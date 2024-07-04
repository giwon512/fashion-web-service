package com.fashionNav.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetConfirmRequest {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String newPassword;

    @NotEmpty
    private String code;
}