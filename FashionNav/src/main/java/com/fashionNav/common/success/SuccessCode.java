package com.fashionNav.common.success;


import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * SuccessCode 열거형은 성공적인 응답 코드를 정의합니다.
 * 각 성공 코드는 고유한 코드와 설명을 포함합니다.
 *
 * 주요 성공 코드:
 * - OK: 일반적인 성공 응답 코드
 * - PAGE_SAVED: 페이지가 성공적으로 저장되었을 때의 응답 코드
 */
@AllArgsConstructor
@Getter
public enum SuccessCode implements SuccessCodeIfs {

    OK(200,"성공"),
    PAGE_SAVED(201,"페이지가 성공적으로 저장되었습니다.");

    private final Integer successCode;
    private final String description;
}
