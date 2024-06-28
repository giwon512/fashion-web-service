package com.fashionNav.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * UserLoginRequest 클래스는 사용자 로그인 요청을 표현하는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 사용자 이메일과 비밀번호를 포함하며, 입력된 데이터의 유효성을 검증하기 위해 여러 제약 조건을 사용합니다.
 *
 * 필드:
 * - email: 사용자 이메일. 필수 입력 항목이며, 올바른 이메일 형식이어야 합니다.
 * - password: 사용자 비밀번호. 필수 입력 항목이며, 최소 8자 이상이어야 하고, 대문자, 소문자, 숫자, 특수 문자를 각각 최소 하나씩 포함해야 합니다.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
            message = "비밀번호는 최소 하나의 대문자, 하나의 소문자, 하나의 숫자, 하나의 특수 문자를 포함해야 합니다.")
    private String password;

}
