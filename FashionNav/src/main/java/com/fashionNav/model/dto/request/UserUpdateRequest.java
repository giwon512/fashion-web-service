package com.fashionNav.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @NotBlank
    private String currentPassword; // 현재 비밀번호

    @NotBlank
    private String newPassword;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;
}