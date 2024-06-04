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
public class User {
    private int userId;           // 사용자 ID (기본 키)
    private String password;      // 사용자 비밀번호
    private String name;          // 사용자 이름
    private String email;         // 사용자 이메일 (유일, 필수)
    private LocalDateTime createdAt;       // 사용자 등록 날짜
    private LocalDateTime updatedAt;       // 사용자 수정 날짜
}