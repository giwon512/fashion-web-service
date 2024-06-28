package com.fashionNav.exception;


import com.fashionNav.common.error.ErrorCodeIfs;
/**
 * ApiExceptionIfs 인터페이스는 API 예외 처리를 위한 표준 인터페이스입니다.
 * 이 인터페이스는 에러 코드와 에러 설명을 반환하는 메서드를 정의합니다.
 * 이를 구현하는 클래스는 특정 예외 상황에 대한 에러 코드와 설명을 제공할 수 있습니다.
 */
public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();

    String getErrorDescription();
}
