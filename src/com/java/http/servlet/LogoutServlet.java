package com.java.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /*
    * Для удаления пользователя из сессии
    * воспользуемся POST операцией.
    * Для этого вызовем у сессии метод
    * invalidate(). Далее перенаправим
    * пользователя на страницу регистрации.
    * */
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp
    ) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect("login");
    }

}
