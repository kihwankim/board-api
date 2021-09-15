package com.cnu.spg.security.token;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface TokenProvider {
    String createToken(UserDetails userDetails);

    String getTokenHeaderName();

    String getTokenSubject(String token);

    Map<String, Object> getTokenPayload(String token);
}
