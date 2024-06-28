package com.fashionNav.model.dto.response;

import com.fashionNav.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserRegistrationResponse 클래스는 회원가입 요청에 대한 응답 데이터를 나타내는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 새로 가입한 사용자의 ID, 이름, 이메일, 생성일, 수정일을 포함합니다.
 *
 * 필드:
 * - userId: 사용자 ID
 * - name: 사용자 이름
 * - email: 사용자 이메일
 * - createdAt: 사용자 생성일
 * - updatedAt: 사용자 수정일
 * from 메서드:
 * - 주어진 User 객체로부터 UserRegistrationResponse 객체를 생성합니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationResponse {

    private Long userId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserRegistrationResponse from(User user){
        return new UserRegistrationResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
