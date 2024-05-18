package com.java.http.filter;

import com.java.http.dto.UserDto;
import com.java.http.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.Set;

import static com.java.http.util.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    public static final Set<String> PUBLIC_PATHS = Set.of(
            LOGIN,
            REGISTRATION);

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain
    ) throws IOException, ServletException {

        /*
        * Получаем информацию на какой сервлет
        * хочет попасть пользователь.
        * */
        String uri = ((HttpServletRequest) servletRequest).getRequestURI();

        /*
        * если путь относится к открытым ресурсам,
        * то позволяем его выполнение. Если же нет,
        * то тогда идет обращение к приватному
        * ресурсу и мы должны позволить обращение
        * к нему только в случае, если пользователь
        * зарегистрирован в системе. Также мы можем
        * сделать проверку роли пользователя.
        * */
        if (isPublicPath(uri) || isUserLoggedIn(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            /*
            * Заголовок, который говорит с какой страницы сюда пришел
            * пользователь. Она может быть null, так как пользователь
            * мог просто вставить ссылку в адресную строку браузера.
            * */
            String prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");

            ((HttpServletResponse) servletResponse).sendRedirect(prevPage != null ? prevPage : LOGIN);
            
        }

    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession()
                .getAttribute("user");

        return user != null;
    }

    /*
    * Пропишем public ресурсы в качестве констант.
    * */
    private boolean isPublicPath(String uri) {
        return PUBLIC_PATHS.stream().anyMatch(uri::startsWith);
    }

}
