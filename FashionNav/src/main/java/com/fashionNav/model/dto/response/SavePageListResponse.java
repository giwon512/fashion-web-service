package com.fashionNav.model.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SavePageListResponse 클래스는 사용자가 저장한 페이지의 정보를 나타내는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 저장된 뉴스의 ID, 제목, 이미지 URL을 포함합니다.
 *
 * 필드:
 * - newsId: 저장된 뉴스의 ID
 * - title: 저장된 뉴스의 제목
 * - imageUrl: 저장된 뉴스의 이미지 URL
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SavePageListResponse {
	private Long newsId;
    private String title;
    private String imageUrl;
}
