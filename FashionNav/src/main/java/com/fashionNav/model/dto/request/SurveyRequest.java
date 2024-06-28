package com.fashionNav.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * SurveyRequest 클래스는 사용자 설문조사 요청을 표현하는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 사용자 ID, 성별, 연령대, 스타일 ID 목록, 브랜드 ID 목록을 포함한 설문조사 데이터를 캡슐화합니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyRequest {
    private Long userId;
    private String gender;
    private String ageGroup;
    private List<Long> styleIds;
    private List<Long> brandIds;
}