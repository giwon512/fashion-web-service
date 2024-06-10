package com.fashionNav.service;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    private final SecretKey key;


    public JwtService(@Value("${jwt.secret-key}") String key) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String generateAccessToken(UserDetails userDetails){
        return generatedToken(userDetails.getUsername());
    }

    public String getUsername(String accessToken){
        return getSubject(accessToken);
    }


    private String generatedToken(String subject) {
        var now = new Date();
        var exp = new Date(now.getTime() + (1000 * 60 * 60 * 3)); //3시간 이후 만료시점
        return Jwts.builder().subject(subject).signWith(key)
                .issuedAt(now)
                .expiration(exp)
                .compact();
    }



    private String getSubject(String token) {

        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

        } catch (JwtException exception) {
            log.error("JwtException", exception);
            throw exception;
        }
    }
}
