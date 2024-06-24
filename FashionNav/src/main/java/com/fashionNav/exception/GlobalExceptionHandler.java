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
