package com.fashionNav.exception;


import com.fashionNav.common.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * ApiExceptionHandler 클래스는 API 호출 중 발생하는 예외를 처리하는 핸들러 클래스입니다.
 * 이 클래스는 ApiException 예외를 처리하고, 적절한 HTTP 상태 코드와 응답 본문을 반환합니다.
 * 예외 발생 시 로그를 기록하고, Api 클래스의 ERROR 메서드를 사용하여 표준화된 에러 응답을 생성합니다.
 */
@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(
            ApiException apiException
    ){

        log.error("",apiException);

        var errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode,apiException.getErrorDescription())
                );

    }
}
