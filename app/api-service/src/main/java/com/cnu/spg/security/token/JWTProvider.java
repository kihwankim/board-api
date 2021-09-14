package com.cnu.spg.security.token;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@Slf4j
public class JWTProvider implements TokenProvider {
    @Value("${jwt.token.secret.key}")
    private String tokenSecretKey;

    @Value("${jwt.token.expiration.seconds}")
    private int expireTime;

    @Value("${jwt.http.request.header}")
    private String tokenHeaderName;

    @Override
    public String createToken(UserDetails userDetails) {
        Date now = new Date();
        Date expireDate = createExpireTime(now);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, tokenSecretKey)
                .compact();
    }

    @Override
    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    private Date createExpireTime(Date start) {
        return new Date(start.getTime() + expireTime);
    }
}
