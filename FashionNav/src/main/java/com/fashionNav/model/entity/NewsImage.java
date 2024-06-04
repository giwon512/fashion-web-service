package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsImage {
    private int newsId;   // 뉴스 ID (외래 키)
    private int imageId;  // 이미지 ID (외래 키)
    private boolean isMain;  // 메인 이미지 여부
}