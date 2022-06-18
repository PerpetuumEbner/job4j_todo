package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest reg = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = reg.getRequestURI();
        if (uri.endsWith("loginPage") || uri.endsWith("login")
                || uri.endsWith("formAddUser") || uri.endsWith("registration")
                || uri.endsWith("success") || uri.endsWith("items")) {
            chain.doFilter(reg, res);
            return;
        }
        if (reg.getSession().getAttribute("user") == null) {
            res.sendRedirect(reg.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(reg, res);
    }
}