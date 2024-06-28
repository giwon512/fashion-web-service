package com.fashionNav.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
/**
 * AuthorizationInterceptor 클래스는 요청의 권한을 검증하는 인터셉터입니다.
 * 이 클래스는 HTTP 요청이 처리되기 전에 특정 조건을 만족하는지 확인하여, 조건에 따라 요청을 계속 처리하거나 차단합니다.
 * 예를 들어, OPTIONS 메서드 요청이나 리소스 파일 요청의 경우 검증을 통과시키고, 그 외의 경우는 추가 검증 로직을 수행합니다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {


    //사전에 검증
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url : {} " ,request.getRequestURI());

        //WEB, chrome 의 경우 GET, POST OPTIONS = pass
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        // js. html. png resource 를 요청하는 경우 =pass
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }


        //TODO header 검증
        return true;
    }
}
