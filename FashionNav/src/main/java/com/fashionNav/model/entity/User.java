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
    private int userId;  // u_id와 매핑
    private String password;  // u_pw와 매핑
    private String name;  // u_name과 매핑
    private String email;  // u_email과 매핑
    private LocalDateTime createdAt;  // u_regdate와 매핑
    private LocalDateTime updatedAt;  // u_moddate와 매핑
}