package com.fashionNav.exception;


import com.fashionNav.common.error.ErrorCodeIfs;
import lombok.Getter;
/**
 * ApiException 클래스는 API 호출 중 발생하는 예외를 처리하는 커스텀 예외 클래스입니다.
 * 이 클래스는 특정 오류 코드와 설명을 포함하여 예외를 생성합니다.
 * 다양한 생성자를 통해 예외의 원인과 추가 설명을 설정할 수 있습니다.
 */
@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs {

    private final ErrorCodeIfs errorCodeIfs;
    private final String errorDescription;

    public ApiException(ErrorCodeIfs errorCodeIfs){
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription  = errorCodeIfs.getDescription();
    }
    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription){
        super(errorDescription);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }


    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx){
        super(tx);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription  = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx,String errorDescription){
        super(tx);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription  = errorCodeIfs.getDescription();
    }
}
