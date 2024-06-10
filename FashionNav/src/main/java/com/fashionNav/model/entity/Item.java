package com.fashionNav.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    private int itemId;          // 상품 ID (기본 키)
    private String name;         // 상품 이름
    private String description;  // 상품 설명
    private double price;        // 상품 가격
    private String brand;        // 상품 브랜드
    private String style;        // 스타일 (카테고리와 연관)
}