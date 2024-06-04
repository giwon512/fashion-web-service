package com.fashionNav.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
    북마크 기능에 필요한 entity
*
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPage {
    private int userId;     // 사용자 ID (외래 키)
    private int pageId;     // 페이지 ID (외래 키)
    private LocalDateTime savedAt;   // 페이지 저장 날짜
}