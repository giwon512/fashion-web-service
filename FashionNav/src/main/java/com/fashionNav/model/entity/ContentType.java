package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentType {
    private String contentType;  // 콘텐츠 유형 (기본 키)
    private String description;  // 콘텐츠 유형 설명
}