package com.java.http.servlet;

import com.java.http.dto.CreateUserDto;
import com.java.http.entity.Gender;
import com.java.http.entity.Role;
import com.java.http.exception.ValidationException;
import com.java.http.service.UserService;
import com.java.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
* MultipartConfig - позволяет работать с Part
* location - место где будут сохраняться эти файлы
* maxFileSize - макс размер файла
* maxRequestSize - макс размер всего http request
* fileSizeThreshold - позволяет не сохранять на диск,
* а держать в памяти. Например 1MB = 1024*1024,
* то есть файлы больше 1МБ будут сохранены на диске.
*/
@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet(value = "/registration", name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp
    ) throws ServletException, IOException {
//
//        if (true) {
//            throw new RuntimeException();
//        }

        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.getPath("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp
    ) throws ServletException, IOException {
        /*
        * Сервлеты не могут работать с Part. Для этого
        * добавляем аннотацию над классом @MultipartConfig
        */
//        Part image = req.getPart("image");

        /*
            Нам нужен отдельный dto для картинки
            и отдельный сервис + dao, который будет сохранять
            эту картинку.
         */

        /*
        Извлекаем все параметры вручную
         */

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name(req.getParameter("name"))
                .birthday(req.getParameter("birthday"))
                .image(req.getPart("image"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();

        try {
            userService.create(createUserDto);
            resp.sendRedirect("/login");
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }

}