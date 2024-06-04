package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyOption {
    private int optionId;         // 옵션 ID (기본 키)
    private int questionId;       // 질문 ID (외래 키)
    private String optionText;    // 옵션 텍스트
}