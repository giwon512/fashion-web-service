package com.fashionNav.common.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
/**
 * ErrorCode 열거형은 API 응답의 에러 코드를 정의합니다.
 * 각 에러 코드는 HTTP 상태 코드, 고유 에러 코드, 설명을 포함합니다.
 *
 * 주요 필드:
 * - httpStatusCode: HTTP 상태 코드.
 * - errorCode: 고유 에러 코드.
 * - description: 에러 설명.
 *
 * 주요 에러 코드:
 * - OK: 성공을 나타내는 코드 (HTTP 200).
 * - BAD_REQUEST: 잘못된 요청을 나타내는 코드 (HTTP 400).
 * - SERVER_ERROR: 서버 에러를 나타내는 코드 (HTTP 500).
 * - NULL_POINT: 널 포인터 예외를 나타내는 코드 (HTTP 512).
 */
@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs {

    OK(200,200,"성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(),400,"잘못된 요청"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512,"Null point")

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;


}
