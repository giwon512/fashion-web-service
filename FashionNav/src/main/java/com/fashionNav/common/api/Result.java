package com.fashionNav.common.api;



import com.fashionNav.common.error.ErrorCode;
import com.fashionNav.common.error.ErrorCodeIfs;
import com.fashionNav.common.success.SuccessCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Result 클래스는 API 응답의 결과 정보를 포함하는 데이터 구조를 정의합니다.
 * 이 클래스는 요청의 성공 또는 실패 상태를 나타내는 코드와 메시지를 포함합니다.
 *
 * 주요 필드:
 * - resultCode: 요청의 결과 상태를 나타내는 코드.
 * - resultMessage: 요청의 결과 메시지.
 * - resultDescription: 요청의 결과 설명.
 *
 * 주요 메서드:
 * - OK(): 기본 성공 응답을 생성합니다.
 * - OK(SuccessCodeIfs successCodeIfs): 지정된 성공 코드를 포함한 성공 응답을 생성합니다.
 * - ERROR(ErrorCodeIfs errorCodeIfs): 지정된 에러 코드를 포함한 에러 응답을 생성합니다.
 * - ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx): 지정된 에러 코드와 예외를 포함한 에러 응답을 생성합니다.
 * - ERROR(ErrorCodeIfs errorCodeIfs, String description): 지정된 에러 코드와 설명을 포함한 에러 응답을 생성합니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result OK(){
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("성공")
                .build();

    }
    public static Result OK(SuccessCodeIfs successCodeIfs) {
        return Result.builder()
                .resultCode(successCodeIfs.getSuccessCode())
                .resultMessage(successCodeIfs.getDescription())
                .resultDescription("성공")
                .build();
    }


    public static Result ERROR(ErrorCodeIfs errorCodeIfs){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription("에러발생")
                .build();
    }


    public static Result ERROR(ErrorCodeIfs errorCodeIfs,Throwable tx){

        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(tx.getLocalizedMessage())
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, String description){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(description)
                .build();
    }
}
