package com.fashionNav.config.security;

import com.fashionNav.service.JwtService;
import com.fashionNav.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // JWT 인증 로직

        // 토큰의 접두사 정의
        String BEARER_PREFIX = "Bearer ";

        // 요청 헤더에서 Authorization 헤더를 가져옴
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 현재 SecurityContext를 가져옴
        var securityContext = SecurityContextHolder.getContext();

        // Authorization 헤더가 비어있지 않고, Bearer 접두사로 시작하며, SecurityContext에 인증 정보가 없는 경우
        if (!ObjectUtils.isEmpty(authorization) &&
                authorization.startsWith(BEARER_PREFIX) &&
                securityContext.getAuthentication() == null) {

            // Bearer 접두사를 제거하고 토큰만 추출
            var accessToken = authorization.substring(BEARER_PREFIX.length());
            // JWT 서비스에서 토큰을 파싱하여 사용자명(username)을 가져옴
            var username = jwtService.getUsername(accessToken);
            // 사용자명으로 사용자 정보를 로드
            var userDetails = userService.loadUserByUsername(username);

            // 사용자 정보를 기반으로 UsernamePasswordAuthenticationToken 생성
            var authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // 요청에 대한 세부 정보를 설정
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // SecurityContext에 인증 정보를 설정
            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);
        }

        // 필터 체인을 통해 요청을 다음 필터로 전달
        filterChain.doFilter(request, response);
    }
}
