package com.fashionNav.common.api;


import com.fashionNav.common.error.ErrorCodeIfs;
import com.fashionNav.common.success.SuccessCodeIfs;
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