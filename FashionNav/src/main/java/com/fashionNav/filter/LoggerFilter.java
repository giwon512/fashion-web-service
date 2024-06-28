package com.fashionNav.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;


import java.io.IOException;
/**
 * LoggerFilter 클래스는 HTTP 요청 및 응답을 로깅하는 필터입니다.
 * 이 클래스는 들어오는 요청과 나가는 응답의 세부 정보를 기록하여 디버깅 및 모니터링에 유용합니다.
 * 요청과 응답의 본문, 헤더, URI, 메서드 등을 포함한 다양한 정보를 로깅합니다.
 */
@Slf4j
@Component
public class LoggerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        var res = new ContentCachingResponseWrapper((HttpServletResponse) response);


        chain.doFilter(req,res);

        //request 정보
        var headerNames = req.getHeaderNames();
        var headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerKey ->{
            var headerValue = req.getHeader(headerKey);


            // authorization-token : ??? , user-agent : ???
            headerValues.append(headerKey).append(" : ").append(headerValue).append(" , ");
        });

        var requestBody = new String(req.getContentAsByteArray());
        var uri = req.getRequestURI();
        var method = req.getMethod();

        log.info(">>>> uri : {} ,header : {}, method : {}, body : {}",uri,method,headerValues,requestBody);

        //response 정보

        var responseHeaderValues = new StringBuilder();

        res.getHeaderNames().forEach(headerKey -> {
            var headerValue = res.getHeader(headerKey);

            responseHeaderValues.
                    append("[")
                    .append(headerKey)
                    .append(" : ").
                    append(headerValue).
                    append(" ]");
        });

        var responseBody = new String(res.getContentAsByteArray());

        log.info("<<<< uri : {}, method : {},header : {}, body : {}",uri,method,responseHeaderValues,responseBody);

        res.copyBodyToResponse();


    }
}
