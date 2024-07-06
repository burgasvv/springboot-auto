package com.burgas.springbootauto.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        if (exception.getLocalizedMessage().equals("Пользователь отключен")) {
            response.sendRedirect(request.getContextPath() + "/baned");
        }
        else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
