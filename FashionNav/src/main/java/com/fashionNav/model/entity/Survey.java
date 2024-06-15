package com.fashionNav.model.entity;

import com.fashionNav.model.dto.request.CreateSurveyRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Survey {
    private int surveyId;        // 설문조사 ID (기본 키)
    private String title;        // 설문조사 제목
    private String description;  // 설문조사 설명
    private LocalDateTime createdAt;// 설문조사 생성 날짜

    public static Survey toEntity(CreateSurveyRequest createSurveyRequest){
        return Survey.builder()
                .title(createSurveyRequest.getTitle())
                .description(createSurveyRequest.getDescription())
                .build();
    }
}