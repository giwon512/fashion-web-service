package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestion {
    private int questionId;        // 질문 ID (기본 키)
    private int surveyId;          // 설문조사 ID (외래 키)
    private String questionText;   // 질문 텍스트
}