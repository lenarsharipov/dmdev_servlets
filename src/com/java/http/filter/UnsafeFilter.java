package com.java.http.filter;

import com.java.http.dto.UserDto;
import com.java.http.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
* Скажем, у нас есть ресурс только для админов,
* на который никто кроме них не может зайти.
* Этот фильтр будет проверять входящий запрос
* на то был ли он отправлен админом или нет.
*  */
@WebFilter("/admin")
public class UnsafeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain
    ) throws IOException, ServletException {
        /*
        * Проверим залогинен ли пользователь и
        * проверим его роль. Для этого у нашего
        * request из сессии мы должны получить
        * пользователя. Но у ServletRequest нет
        * метода для вызова сессии. Для этого
        * этот объект надо скастить до HttpServletRequest.
        * Получим пользователя с помощью атрибута.
        * Помним, что нам возвратиться Object.
        * Его явно приводим к UserDto.
        *  */
        UserDto userDto = (UserDto) ((HttpServletRequest) servletRequest)
                .getSession()
                .getAttribute("user");

        /*
        * Проверяем авторизован ли пользователь
        * Если не null - авторизован, тогда продолжаем
        * движение по цепочки фильтров к сервлету.
        * В противном случае делаем переадресацию
        * на другую страницу - registration.jsp. Для этого
        * приводим ServletResponse к HttpServletResponse
        * */
        if (userDto != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse)
                    .sendRedirect("/registration");
        }
    }
}
