package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Images {
    private int imageId;      // 이미지 ID (기본 키)
    private String url;       // 이미지 URL
    private String altText;   // 이미지 대체 텍스트
    private String caption;   // 이미지 캡션
}