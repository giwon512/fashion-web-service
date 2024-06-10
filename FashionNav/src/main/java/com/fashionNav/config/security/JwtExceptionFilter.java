package com.fashionNav.config.security;

import com.fashionNav.common.error.ErrorCode;
import com.fashionNav.exception.ApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            // 필터 체인을 통해 요청을 다음 필터로 전달
            filterChain.doFilter(request, response);

        } catch (JwtException exception) {
            // JWT 예외가 발생한 경우 처리

            // 응답의 Content-Type을 JSON으로 설정
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            // 응답 상태 코드를 401 Unauthorized로 설정
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 응답의 인코딩을 UTF-8로 설정
            response.setCharacterEncoding("UTF-8");

            // 예외 메시지를 포함한 ApiException 객체 생성
            var errorResponse = new ApiException(ErrorCode.BAD_REQUEST, exception.getMessage());

            // ApiException 객체를 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String responseJson = objectMapper.writeValueAsString(errorResponse);
            // JSON 응답을 클라이언트에 반환
            response.getWriter().write(responseJson);
        }
    }
}
