package com.fashionNav.model.dto.response;

import com.fashionNav.model.entity.Brand;
import com.fashionNav.model.entity.Style;
import com.fashionNav.model.entity.UserSurvey;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * UserSurveyResponse 클래스는 사용자 설문조사 응답 정보를 포함하는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 사용자 설문조사 정보, 스타일 목록, 브랜드 목록을 포함합니다.
 *
 * 필드:
 * - userSurvey: 사용자 설문조사 정보
 * - styles: 사용자 설문조사와 연관된 스타일 목록
 * - brands: 사용자 설문조사와 연관된 브랜드 목록
 *
 */
@Data
@AllArgsConstructor

public class UserSurveyResponse {
    private UserSurvey userSurvey;
    private List<Style> styles;
    private List<Brand> brands;

}
