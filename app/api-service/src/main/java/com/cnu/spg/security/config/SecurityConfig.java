package com.cnu.spg.security.config;

import com.cnu.spg.security.filter.AuthorizationProcessFilter;
import com.cnu.spg.security.handler.AuthFailureHandler;
import com.cnu.spg.security.provider.AuthProvider;
import com.cnu.spg.security.token.JWTProvider;
import com.cnu.spg.security.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_URL_PATH = "/v1/login";

    private final UserDetailsService loginDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public TokenProvider tokenProvider() {
        return new JWTProvider();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthorizationProcessFilter authorizationProcessFilter() throws Exception {
        AuthorizationProcessFilter authorizationProcessFilter = new AuthorizationProcessFilter(tokenProvider(), authenticationManagerBean(), LOGIN_URL_PATH);
        authorizationProcessFilter.setAuthenticationFailureHandler(new AuthFailureHandler());

        return authorizationProcessFilter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthProvider(loginDetailService, passwordEncoder());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/swagger-ui.html/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(LOGIN_URL_PATH).permitAll()
                .anyRequest().authenticated();

        http
                .csrf().disable();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // session 사용하지 않음

        http
                .addFilterBefore(authorizationProcessFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
