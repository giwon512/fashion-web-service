package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    private Long bannerId;
    private String title;
    private String imageUrl;
    private String url; // 추가된 필드
    private String description; // 추가된 필드
    private LocalDateTime createdDate;


}