package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
    private int surveyId;        // 설문조사 ID (기본 키)
    private String title;        // 설문조사 제목
    private String description;  // 설문조사 설명
    private LocalDateTime createdAt;      // 설문조사 생성 날짜
}