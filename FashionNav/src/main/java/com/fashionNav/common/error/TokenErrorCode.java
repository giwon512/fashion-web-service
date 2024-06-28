package com.fashionNav.common.error;


import lombok.AllArgsConstructor;
import lombok.Getter;


/*
 * 토큰의 경우 2000번대 에러 사용했음.
 * TokenErrorCode 열거형은 토큰과 관련된 에러 코드를 정의합니다.
 * 각 에러 코드는 HTTP 상태 코드, 고유 에러 코드 및 에러 설명을 포함합니다.
 *
 * 주요 에러 코드:
 * - INVALID_TOKEN: 유효하지 않은 토큰에 대한 에러 코드
 * - EXPIRED_TOKEN: 만료된 토큰에 대한 에러 코드
 * - TOKEN_EXCEPTION: 알 수 없는 토큰 에러에 대한 에러 코드
 * - AUTHORIZATION_TOKEN_NOT_FOUND: 인증 헤더에 토큰이 없는 경우의 에러 코드
 */
@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs {

    INVALID_TOKEN(400,2000,"유효하지 않은 토큰."),
    EXPIRED_TOKEN(400,2001,"만료된 토큰"),
    TOKEN_EXCEPTION(400,2002,"토큰 알 수 없는 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(400,2003,"인증 헤더 토큰 없음"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;


}
