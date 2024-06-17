package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private String adminId;  // 관리자 ID (기본 키)
    private String adminPw;  // 관리자 비밀번호
}