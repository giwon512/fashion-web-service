package com.fashionNav.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSurveyResponse {
    private int userId;       // 사용자 ID (외래 키)
    private int questionId;   // 질문 ID (외래 키)
    private int optionId;     // 옵션 ID (외래 키)
    private LocalDateTime responseDate;  // 응답 날짜
}