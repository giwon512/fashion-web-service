package com.fashionNav.model.dto.response;

import com.fashionNav.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * UserResponse 클래스는 사용자 정보를 포함하는 응답 데이터를 나타내는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 사용자 ID, 이름, 이메일, 생성일, 수정일, 새로운 액세스 토큰, 새로운 리프레시 토큰, 구글 사용자 여부를 포함합니다.
 *
 * 필드:
 * - userId: 사용자 ID
 * - name: 사용자 이름
 * - email: 사용자 이메일
 * - createdAt: 사용자 생성일
 * - updatedAt: 사용자 수정일
 * - newAccessToken: 새로운 액세스 토큰
 * - newRefreshToken: 새로운 리프레시 토큰
 * - googleUser: 구글 사용자 여부
 *
 * from 메서드:
 * - 주어진 User 객체와 새로운 액세스 토큰, 리프레시 토큰으로부터 UserResponse 객체를 생성합니다.
 * - 주어진 User 객체로부터 UserResponse 객체를 생성하며, 새로운 액세스 토큰과 리프레시 토큰은 null로 설정합니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    private String name;
    private String email;
    private String gender;
    private LocalDate birthdate;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String newAccessToken; // 새로운 액세스 토큰
    private String newRefreshToken; // 새로운 리프레시 토큰
    private boolean googleUser; // 구글 사용자 여부 추가

    public static UserResponse from(User user, String newAccessToken, String newRefreshToken){
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getGender(),
                user.getBirthdate(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                newAccessToken,
                newRefreshToken,
                user.getPassword() == null || user.getPassword().isEmpty()
        );
    }

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getGender(),
                user.getBirthdate(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                null,
                null,
                user.getPassword() == null || user.getPassword().isEmpty()
        );
    }
}