package com.fashionNav.common.error;
/**
 * ErrorCodeIfs 인터페이스는 에러 코드와 관련된 메서드를 정의합니다.
 * 이 인터페이스를 구현하는 클래스는 HTTP 상태 코드, 고유 에러 코드 및 에러 설명을 제공해야 합니다.
 *
 * 주요 메서드:
 * - getHttpStatusCode(): HTTP 상태 코드를 반환합니다.
 * - getErrorCode(): 고유 에러 코드를 반환합니다.
 * - getDescription(): 에러 설명을 반환합니다.
 */
public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getDescription();
}
