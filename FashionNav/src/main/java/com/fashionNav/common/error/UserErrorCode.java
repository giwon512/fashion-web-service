package com.fashionNav.common.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


/*
* User의 경우 1000 번대 에러 사용
*
* */

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
