package com.fashionNav.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {
    private int pageId;         // 페이지 ID (기본 키)
    private String url;         // 페이지 URL
    private String title;       // 페이지 제목
    private String description; // 페이지 설명
    private LocalDateTime createdAt;     // 페이지 생성 날짜
    private String contentType; // 콘텐츠 유형 ('news', 'item' 등)
}