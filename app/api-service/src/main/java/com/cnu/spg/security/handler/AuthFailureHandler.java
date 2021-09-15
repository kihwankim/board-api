package com.cnu.spg.security.handler;

import com.cnu.spg.user.exception.LoginRequestParamterNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFailureHandler implements AuthenticationFailureHandler {
    private static final String PASSWORD_NOT_MATCHED_MSG = "Password is not matched";
    private static final String TOKEN_EXPIRED_EXCEPTION = "token was expired plz login again";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String errorMsg = "Forbidden";

        if (exception instanceof BadCredentialsException) {
            errorMsg = PASSWORD_NOT_MATCHED_MSG;
        } else if (exception instanceof CredentialsExpiredException) {
            errorMsg = TOKEN_EXPIRED_EXCEPTION;
        } else if (exception instanceof LoginRequestParamterNotValidException) {
            errorMsg = exception.getMessage();
        }

        response.getWriter().write(errorMsg);
    }
}
