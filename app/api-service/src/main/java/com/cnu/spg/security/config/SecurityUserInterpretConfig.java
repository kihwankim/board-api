package com.cnu.spg.security.config;

import com.cnu.spg.security.token.JWTProvider;
import com.cnu.spg.security.token.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityUserInterpretConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public TokenProvider tokenProvider() {
        return new JWTProvider();
    }
}
