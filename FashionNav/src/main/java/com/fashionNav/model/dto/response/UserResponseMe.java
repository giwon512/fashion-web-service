package com.fashionNav.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 ** 마이페이지에 필요한 정보 불러오는 dto
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