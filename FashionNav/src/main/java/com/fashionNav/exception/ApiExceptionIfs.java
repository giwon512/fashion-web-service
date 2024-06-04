package com.fashionNav.exception;


import com.fashionNav.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();

    String getErrorDescription();
}
