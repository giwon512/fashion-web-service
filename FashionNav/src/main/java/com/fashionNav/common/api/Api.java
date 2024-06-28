package com.fashionNav.common.api;


import com.fashionNav.common.error.ErrorCodeIfs;
import com.fashionNav.common.success.SuccessCodeIfs;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Api 클래스는 API 응답을 표준화하여 처리하기 위한 데이터 구조를 정의합니다.
 * 이 클래스는 요청에 대한 결과와 데이터(또는 에러 메시지)를 포함합니다.
 *
 * 주요 필드:
 * - result: 요청의 성공 또는 실패 결과를 나타내는 Result 객체.
 * - body: 요청의 결과로 반환되는 데이터.
 *
 * 주요 메서드:
 * - OK(T data): 요청이 성공했을 때, 데이터와 함께 성공 응답을 반환합니다.
 * - OK(T data, SuccessCodeIfs successCodeIfs): 요청이 성공했을 때, 데이터와 성공 코드를 포함한 응답을 반환합니다.
 * - OK(SuccessCodeIfs successCodeIfs): 요청이 성공했을 때, 성공 코드를 포함한 응답을 반환합니다.
 * - Error(Result result): 요청이 실패했을 때, Result 객체를 포함한 에러 응답을 반환합니다.
 * - ERROR(ErrorCodeIfs errorCodeIfs): 요청이 실패했을 때, 에러 코드를 포함한 에러 응답을 반환합니다.
 * - ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx): 요청이 실패했을 때, 에러 코드와 예외를 포함한 에러 응답을 반환합니다.
 * - ERROR(ErrorCodeIfs errorCodeIfs, String description): 요청이 실패했을 때, 에러 코드와 설명을 포함한 에러 응답을 반환합니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;


    @Valid
    private T body;

    public static <T> Api<T> OK(T data) {
        var api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;

    }

    public static <T> Api<T> OK(T data, SuccessCodeIfs successCodeIfs) {
        var api = new Api<T>();
        api.result = Result.OK(successCodeIfs);
        api.body = data;
        return api;
    }


    public static Api<Void> OK(SuccessCodeIfs successCodeIfs) {
        var api = new Api<Void>();
        api.result = Result.OK(successCodeIfs);
        api.body = null;
        return api;
    }




    public static Api<Object> Error(Result result) {
        var api = new Api<Object>();
        api.result = result;
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs) {
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx) {
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs, tx);
        return api;

    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs,String description){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs,description);
        return api;
    }


}