package com.fashionNav.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDetailResponse {
    private String title;
    private String imageUrl;
    private LocalDateTime publishedAt;
    private String newsType;  // 추가된 필드
}