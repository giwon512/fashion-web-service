package com.fashionNav.model.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * UserRegisterRequest 클래스는 사용자 등록 요청을 표현하는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 사용자 비밀번호, 이름, 이메일, 성별, 전화번호, 생년월일을 포함하며,
 * 입력된 데이터의 유효성을 검증하기 위해 여러 제약 조건을 사용합니다.
 *
 * 필드:
 * - password: 사용자 비밀번호. 필수 입력 값이며, 최소 8자 이상, 대문자, 소문자, 숫자, 특수 문자를 각각 포함해야 합니다.
 * - name: 사용자 이름. 필수 입력 값이며, 최대 50자까지 허용됩니다.
 * - email: 사용자 이메일. 필수 입력 값이며, 유효한 이메일 주소 형식이어야 합니다.
 * - gender: 사용자 성별. 필수 입력 값입니다.
 * - phoneNumber: 사용자 전화번호. 필수 입력 값이며, '010-1234-5678' 형식이어야 합니다.
 * - birthdate: 사용자 생년월일. 필수 입력 값이며, 'yyyy-MM-dd' 형식이어야 합니다.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 최소 8자 이상이어야 하며, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 50, message = "이름은 최대 10자까지 허용됩니다.")
    private String name;

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "성별은 필수 입력 값입니다.")
    private String gender;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNumber; // 전화번호 추가

    @NotBlank(message = "생년월일은 필수 입력 값입니다.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "올바른 생년월일 형식이 아닙니다. (yyyy-MM-dd)")
    private String birthdate;
}