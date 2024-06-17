package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsType {
    private String type;             // 뉴스 유형 (기본 키)
    private String typeDescription;  // 뉴스 유형 설명
}