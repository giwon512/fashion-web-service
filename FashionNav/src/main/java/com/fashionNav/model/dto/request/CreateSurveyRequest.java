package com.fashionNav.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSurveyRequest {

    private String title;        // 설문조사 제목
    private String description;  // 설문조사 설명
    private LocalDateTime createdAt; //설문조사 날짜
    private List<QuestionRequest> questions;
}
