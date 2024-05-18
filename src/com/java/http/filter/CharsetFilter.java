package com.java.http.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
* 1. Класс должен реализовывать jakarta.servlet.Filter
* Необходимо реализовать метод doFilter(),
* другие методы - inti(FilterConfig filterConfig),
* destroy() - дефолтные методы, из необязательно
* переопределять.
*
* 2. Надо поставить url-pattern, по которому этот
* фильтр будет срабатывать. Добавим @WebFilter.
* В этой аннотации много параметров, но главный
* - urlPattern. Есть еще urlPatterns - массив urlов.
*
*
* */
@WebFilter(value = "/*",
//        servletNames = {"RegistrationServlet"},
        initParams = {
            @WebInitParam(name = "param1", value = "param1Value")
        },
        dispatcherTypes = DispatcherType.REQUEST
) /* срабатывает для всех запросов */
public class CharsetFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /*
    * ServletRequest - это базовый интерфейс, который
    * реализует уже HttpServletRequest
    */
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain
    ) throws IOException, ServletException {
        /*
        * Установим кодировку для всех request, response.
        * Для этого используем StandardCharsets.UTF_8.name()
        * */
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());

        /*
        * Далее, после того как мы добавили новый функционал,
        * мы должны продолжить выполнять нашу цепочку фильтров.
        * То есть у нас есть цепочка фильтров, после которой мы
        * должны добраться до сервлета. Сервлет обрабатывает наш
        * запрос. Такая цепочка фильтров построена по паттерну
        * - Chain of responsibility.
        * Например, мы можем полностью обработать запрос в первом
        * фильтре и отправить ответ клиенту и не продолжать цепочку
        * фильтров. Если же нам нужно продолжить выполнение далее
        * фильтров, то мы передаем процесс следующему фильтру в
        * цепочке. Далее следующий фильтр определяет, нужно ли ему
        * передавать обязанность следующему фильтру или стоит
        * прекратить. Для этого нам и нужен объект FilterChain.
        * Он знает про всю цепочку вызовов. У него есть один
        * метод doFilter(), которому мы должны передать
        * ServletRequest, ServletResponse.
        *  */
        filterChain.doFilter(servletRequest, servletResponse);

        /*
        * doFilter() пойдет дальше по цепочку фильтров до самого сервлета,
        * затем по цепочке вернется обратно сюда и далее мы сможем выполнять
        * другие действия.
        * Обычно на практике конечно так не делают.
        *  */
        System.out.println();
    }
}
