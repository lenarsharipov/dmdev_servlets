package com.java.http.servlet;

import com.java.http.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/sessions")
public class SessionServlet extends HttpServlet {

    private static final String USER = "user";

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp
    ) throws ServletException, IOException {

        HttpSession session = req.getSession();

        // проверяем есть ли в атрибутах пользователь
        UserDto user = (UserDto) session.getAttribute(USER);
        // если его нет, то устанавливаем.
        // обычно пользователь устанавливается через логи и пароль
        if (user == null) {
            user = UserDto.builder()
                    .id(25)
                    .email("test@gmail.com")
                    .build();
            session.setAttribute(USER, user);
        }

        System.out.println(req.getAttributeNames());
    }
}
