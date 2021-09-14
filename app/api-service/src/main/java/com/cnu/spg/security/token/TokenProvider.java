package com.cnu.spg.security.token;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenProvider {
    String createToken(UserDetails userDetails);

    String getTokenHeaderName();
}
