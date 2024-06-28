package com.fashionNav.exception;


import com.fashionNav.common.api.Api;
import com.fashionNav.common.api.Result;
import com.fashionNav.common.error.ErrorCode;
import com.fashionNav.common.error.UserErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * GlobalExceptionHandler 클래스는 전역 예외 처리를 담당하는 클래스입니다.
 * 이 클래스는 다양한 예외 상황에 대해 적절한 응답을 생성하여 클라이언트에게 반환합니다.
 * 이를 통해 애플리케이션의 안정성을 높이고, 일관된 에러 메시지를 제공할 수 있습니다.
 *
 * - Exception.class: 모든 예외를 처리하며, 500 서버 에러로 응답합니다.
 * - MethodArgumentNotValidException.class: 요청 파라미터 유효성 검증 실패 시 처리하며, 400 상태 코드와 상세한 에러 메시지를 반환합니다.
 * - HttpMessageNotReadableException.class: 요청 본문이 읽을 수 없을 때 처리하며, 400 상태 코드와 에러 메시지를 반환합니다.
 * - AccessDeniedException.class: 접근이 거부된 경우 처리하며, 403 상태 코드와 에러 메시지를 반환합니다.
 */
@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE) //가장 마지막에 실행 적용
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(
            Exception exception
    ){
        log.error("",exception);

        return ResponseEntity
                .status(500)
                .body(
                        Api.Error(Result.ERROR(ErrorCode.SERVER_ERROR))
                );

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Api<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errorMessage = e.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList()
                .toString();
        log.error("Validation error: {}", errorMessage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Api.Error(Result.ERROR(ErrorCode.BAD_REQUEST, errorMessage)));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Api<Object>> handleMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Api.Error(Result.ERROR(ErrorCode.BAD_REQUEST, "Required request body is missing.")));
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Api<Object>> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access Denied: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN) // 403 Forbidden 상태 코드 사용
                .body(Api.Error(Result.ERROR(UserErrorCode.USER_NOT_ALLOWED)));
    }


}
