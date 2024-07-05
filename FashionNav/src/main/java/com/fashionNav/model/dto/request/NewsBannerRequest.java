package com.fashionNav.model.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsBannerRequest {
	private String title;
	private String imageUrl;
	private String url; // 추가된 필드
	private String description; // 추가된 필드
	private LocalDateTime createdDate;

	
}
