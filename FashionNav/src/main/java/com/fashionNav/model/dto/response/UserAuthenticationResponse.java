package com.fashionNav.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserAuthenticationResponse 클래스는 사용자 인증 요청에 대한 응답 데이터를 나타내는 DTO(Data Transfer Object)입니다.
 * 이 클래스는 인증된 사용자의 액세스 토큰과 리프레시 토큰을 포함합니다.
 *
 * 필드:
 * - accessToken: 사용자에게 발급된 액세스 토큰
 * - refreshToken: 사용자에게 발급된 리프레시 토큰
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
