package com.fashionNav.common.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;



/*
 * User의 경우 1400번대 에러 사용.
 * UserErrorCode 열거형은 사용자와 관련된 에러 코드를 정의합니다.
 * 각 에러 코드는 HTTP 상태 코드, 고유 에러 코드 및 에러 설명을 포함합니다.
 *
 * 주요 에러 코드:
 * - USER_NOT_FOUND: 사용자를 찾을 수 없을 때의 에러 코드
 * - USER_ALREADY_EXISTS: 이미 존재하는 사용자에 대한 에러 코드
 * - USER_NOT_ALLOWED: 허용되지 않은 사용자에 대한 에러 코드
 * - INVALID_PASSWORD: 패스워드가 올바르지 않을 때의 에러 코드
 */
@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs {

    USER_NOT_FOUND(400,1404,"사용자를 찾을 수 없음."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT.value(),1405,"이미 존재하는 유저."),
    USER_NOT_ALLOWED(HttpStatus.FORBIDDEN.value(), 1406,"허용되지 않은 유저."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED.value(), 1407,"패스워드가 올바르지 않습니다.");


    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;


}
