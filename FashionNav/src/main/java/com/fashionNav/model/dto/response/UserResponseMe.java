package com.fashionNav.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * UserResponseMe 클래스는 마이페이지에 필요한 사용자 정보를 포함하는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 사용자 ID, 이름, 이메일, 성별, 전화번호, 생년월일, 역할, 구글 사용자 여부를 포함합니다.
 *
 * 필드:
 * - userId: 사용자 ID
 * - name: 사용자 이름
 * - email: 사용자 이메일
 * - gender: 사용자 성별
 * - phoneNumber: 사용자 전화번호
 * - birthdate: 사용자 생년월일
 * - role: 사용자 역할
 * - googleUser: 구글 사용자 여부
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseMe {

    private Long userId;
    private String name;
    private String email;
    private String gender;
    private String phoneNumber;
    private LocalDate birthdate;
    private String role;
    private boolean googleUser; // 구글 사용자 여부 추가

}