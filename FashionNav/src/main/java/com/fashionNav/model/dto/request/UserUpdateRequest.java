package com.fashionNav.model.dto.request;

import com.fashionNav.validation.ConditionalNotBlank;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * UserUpdateRequest 클래스는 사용자 정보 업데이트 요청을 표현하는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 사용자가 업데이트할 수 있는 여러 필드를 포함하고 있으며,
 * Google 사용자 여부에 따라 비밀번호 필드의 유효성을 검증하는 조건부 검증을 포함합니다.
 *
 * 필드:
 * - currentPassword: 현재 비밀번호 (Google 사용자가 아닌 경우 필수 입력 값)
 * - newPassword: 새 비밀번호 (Google 사용자가 아닌 경우 필수 입력 값)
 * - name: 사용자 이름 (필수 입력 값, 최대 50자)
 * - email: 사용자 이메일 (필수 입력 값, 유효한 이메일 형식)
 * - gender: 사용자 성별 (필수 입력 값)
 * - phoneNumber: 사용자 전화번호 (필수 입력 값, 올바른 전화번호 형식)
 * - birthdate: 사용자 생년월일 (필수 입력 값, 올바른 생년월일 형식)
 * - googleUser: Google 사용자 여부를 나타내는 boolean 값
 *
 * ConditionalNotBlank 어노테이션을 사용하여 Google 사용자가 아닌 경우 비밀번호 필드를 필수 입력 값으로 검증합니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @ConditionalNotBlank(message = "기존 비밀번호는 필수 입력 값입니다.", conditionField = "googleUser", conditionValue = "false", field = "currentPassword")
    private String currentPassword;

    @ConditionalNotBlank(message = "새 비밀번호는 필수 입력 값입니다.", conditionField = "googleUser", conditionValue = "false", field = "newPassword")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 최소 8자 이상이어야 하며, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.")
    private String newPassword;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 50, message = "이름은 최대 50자까지 허용됩니다.")
    private String name;

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "성별은 필수 입력 값입니다.")
    private String gender;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다. (010-1234-5678)")
    private String phoneNumber;

    @NotBlank(message = "생년월일은 필수 입력 값입니다.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "올바른 생년월일 형식이 아닙니다. (yyyy-MM-dd)")
    private String birthdate;

    private boolean googleUser;
}