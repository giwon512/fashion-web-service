package com.fashionNav.model.dto.response;

import com.fashionNav.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    private String name;
    private String email;
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
                user.getCreatedAt(),
                user.getUpdatedAt(),
                null,
                null,
                user.getPassword() == null || user.getPassword().isEmpty()
        );
    }
}