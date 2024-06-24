package com.fashionNav.common.api;



import com.fashionNav.common.error.ErrorCode;
import com.fashionNav.common.error.ErrorCodeIfs;
import com.fashionNav.common.success.SuccessCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
