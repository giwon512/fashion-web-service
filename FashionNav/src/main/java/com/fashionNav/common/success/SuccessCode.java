package com.fashionNav.common.success;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessCode implements SuccessCodeIfs {

    OK(200,"성공"),
    PAGE_SAVED(201,"페이지가 성공적으로 저장되었습니다.");

    private final Integer successCode;
    private final String description;
}
