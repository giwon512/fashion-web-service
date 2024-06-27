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

    // 생성자: JWT 비밀 키를 주입받아 SecretKey 객체로 변환합니다.
    public JwtService(@Value("${jwt.secret-key}") String key) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    // 액세스 토큰을 생성합니다. 유효 기간은 15분입니다.
    public String generateAccessToken(UserDetails userDetails){
        return generateToken(userDetails.getUsername(), 1000 * 60 * 15); // 15분
    }

    // 리프레시 토큰을 생성합니다. 유효 기간은 7일입니다.
    public String generateRefreshToken(UserDetails userDetails){
        return generateToken(userDetails.getUsername(), 1000 * 60 * 60 * 24 * 7); // 7일
    }

    // 토큰에서 사용자 이름(주제)을 추출합니다.
    public String getUsername(String token) {
        return getSubject(token);
    }

    // 토큰이 유효한지 확인합니다.
    public boolean isTokenValid(String token) {
        try {
            getSubject(token);
            return true;
        } catch (JwtException e) {
            log.error("Invalid JWT token", e);
            return false;
        }
    }

    // 주어진 주제와 만료 시간으로 JWT를 생성합니다.
    private String generateToken(String subject, long expirationMillis) {
        var now = new Date();
        var exp = new Date(now.getTime() + expirationMillis);
        return Jwts.builder()
                .subject(subject).signWith(key)
                .issuedAt(now)
                .expiration(exp)
                .compact();
    }

    // JWT에서 주제를 추출합니다. 유효하지 않은 토큰인 경우 예외를 던집니다.
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
