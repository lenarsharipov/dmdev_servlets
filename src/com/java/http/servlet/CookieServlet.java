package com.java.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/cookies")
public class CookieServlet extends HttpServlet {

    private static final String UNIQUE_ID = "userId";
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp
    ) throws ServletException, IOException {
        /*
        req.getHeader("cookie") == Получим куки в виде строки
        то есть надо вручную распарсить строку. Что не удобно.
        Воспользуемся методом getCookies(), который вернет
        массив кук. Он может быть null, если не пришло ни
        одного значения куки.
         */
        Cookie[] cookies = req.getCookies();

        /*
        Проверяем на null, если не null, то проверяем
        есть ли кука с таким именем. Если ее нет, то
        этот пользователь у нас впервые, мы должны
        создать куку, инкрементировать счетчик уникальных
        пользователей и отправить клиенту.
         */
        if (cookies == null || Arrays.stream(cookies)
                .filter(cookie -> UNIQUE_ID.equals(cookie.getName()))
                .findFirst()
                .isEmpty()) {
            Cookie cookie = new Cookie(UNIQUE_ID, "1");
            cookie.setPath("/cookies"); // таким образом куки будут идти только при вызове сервлета /cookies
            cookie.setMaxAge(15 * 60); /* Задается в сек.
            По умолчанию == -1 - это значит что кука будет доступна пока браузер не будет закрыт */

            resp.addCookie(cookie);
            counter.incrementAndGet();
        }

        resp.setContentType("text/html");
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(counter.get());
        }
    }
}
