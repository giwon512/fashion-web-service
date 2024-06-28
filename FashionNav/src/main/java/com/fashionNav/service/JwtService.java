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

/**
 * JwtService 클래스는 JWT 토큰의 생성, 검증, 정보 추출 등의 기능을 제공하는 서비스 클래스입니다.
 * 이 클래스는 액세스 토큰과 리프레시 토큰을 생성하고, 토큰의 유효성을 검증하며, 토큰에서 사용자 이름(주제)을 추출하는 기능을 수행합니다.
 * JWT 비밀 키와 토큰의 유효 기간은 application.properties 파일에서 설정된 값을 사용하여 초기화됩니다.
 */
@Slf4j
@Service
public class JwtService {

    private final SecretKey key;
    private final long accessTokenExpiry;
    private final long refreshTokenExpiry;

    // 생성자: JWT 비밀 키와 토큰 유효 기간을 주입받습니다.
    public JwtService(
            @Value("${jwt.secret-key}") String key,
            @Value("${jwt.access-token-expiry}") long accessTokenExpiry,
            @Value("${jwt.refresh-token-expiry}") long refreshTokenExpiry) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    // 액세스 토큰을 생성합니다. 유효 기간은 application.properties에서 설정한 값을 사용합니다.
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername(), accessTokenExpiry);
    }

    // 리프레시 토큰을 생성합니다. 유효 기간은 application.properties에서 설정한 값을 사용합니다.
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername(), refreshTokenExpiry);
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