package com.java.http.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/*
Мы не можем создавать каких-либо конструкторов в сервлете,
потому что через reflection api вызывается конструктор без параметров.
 */
@WebServlet("/first") // маппинг с url /first
public class FirstServlet extends HttpServlet {

    /*
    * ServletConfig - конфигурационный класс, в котором есть некоторые настройки сервлета.
    * getServletName() - имя сервлета
    * getInitParameterNames() - параметры
    * getServletContext() - что-то вроде глобальной переменной на все наше приложение, то есть
    * у всех сервлетов будет один и тот же экземпляр класса ServletContext. Он служит хранилищем
    * для глобальных переменных - attributes, фильтров, слушатель/listener
    *
    */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /*
    * Основной код, основная логика находится здесь.
    * В реализации HttpServlet метод service представляет из себя множество if-else на каждый http-method:
    * GET, POST, PUT, PATCH, etc.
    * Соответственно, надо предопределить не весь метод service, а вызываемые для каждого метода методы типа
    * doMETHODNAME -doGet, doPost, doPut, doDelete and etc.
    */
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);
//    }

    /*
    Переопределим метод doGet()
    Вернем браузеру страницу
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param = req.getParameter("param");
        Map<String, String[]> parameterMap = req.getParameterMap();
        System.out.println(parameterMap);

        resp.setContentType("text/html; charset=UTF-8");
        resp.setHeader("token", "12345ABcD");
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("Hello from First Servlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Map<String, String[]> parameterMap = req.getParameterMap();
//        System.out.println(parameterMap);

//        req.getReader() - считывает данные в виде строчных символов
//        req.getInputStream(); // для бинарных файлов
        // так как мы передавали текстовый файл, возьмем reader
        try (BufferedReader reader = req.getReader();
            var lines = reader.lines()) {
            lines.forEach(System.out::println);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}



















