package com.cnu.spg.security.filter;

import com.cnu.spg.security.dto.request.LoginRequestDto;
import com.cnu.spg.security.token.TokenProvider;
import com.cnu.spg.security.exception.LoginRequestParamterNotValidException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class AuthorizationProcessFilter extends AbstractAuthenticationProcessingFilter {
    private static final String LOGIN_HTTP_METHOD = "POST";

    private final TokenProvider tokenProvider;


    public AuthorizationProcessFilter(TokenProvider tokenProvider, AuthenticationManager authenticationManager, String defaultLoginUrl) {
        super(new AntPathRequestMatcher(defaultLoginUrl, LOGIN_HTTP_METHOD));
        setAuthenticationManager(authenticationManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto loginRequestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getUsername(),
                    loginRequestDto.getPassword(),
                    new ArrayList<>());
            setDetails(request, loginToken);

            return getAuthenticationManager().authenticate(loginToken);
        } catch (IOException e) {
            throw new LoginRequestParamterNotValidException();
        }
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken token) {
        token.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails permittedUserDetails = (UserDetails) authResult.getPrincipal();
        log.info("login: {}", permittedUserDetails.getUsername());

        String token = tokenProvider.createToken(permittedUserDetails);

        response.addHeader(tokenProvider.getTokenHeaderName(), token);
    }
}
