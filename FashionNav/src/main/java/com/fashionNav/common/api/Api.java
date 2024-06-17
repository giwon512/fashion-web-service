package com.fashionNav.common.api;


import com.fashionNav.common.error.ErrorCodeIfs;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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