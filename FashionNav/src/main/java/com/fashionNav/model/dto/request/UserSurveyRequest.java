package com.fashionNav.model.dto.request;

import com.fashionNav.model.entity.UserSurvey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * UserSurveyRequest 클래스는 사용자 설문조사 요청을 표현하는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 사용자가 설문조사에서 선택한 스타일 및 브랜드 정보를 포함합니다.
 *
 * 필드:
 * - userSurvey: UserSurvey 엔티티 객체로, 사용자의 설문조사 정보를 포함합니다.
 * - styleIds: 사용자가 선택한 스타일의 ID 목록입니다.
 * - brandIds: 사용자가 선택한 브랜드의 ID 목록입니다.
 *
 * Lombok 라이브러리를 사용하여 자동으로 getter, setter, 생성자 등을 생성합니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSurveyRequest {
    private UserSurvey userSurvey;
    private List<Long> styleIds;
    private List<Long> brandIds;
}
