package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategory {
    private int itemId;    // 상품 ID (외래 키)
    private String style;  // 스타일 (카테고리와 연관)
}