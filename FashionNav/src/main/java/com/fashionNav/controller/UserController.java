package com.fashionNav.controller;


import com.fashionNav.common.api.Api;
import com.fashionNav.model.dto.request.UserLoginRequest;
import com.fashionNav.model.dto.request.UserRegisterRequest;
import com.fashionNav.model.dto.request.UserUpdateRequest;
import com.fashionNav.model.dto.response.UserAuthenticationResponse;
import com.fashionNav.model.dto.response.UserRegistrationResponse;
import com.fashionNav.model.dto.response.UserResponse;
import com.fashionNav.model.dto.response.UserResponseMe;
import com.fashionNav.model.entity.User;
import com.fashionNav.service.UserService;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@Tag(name = "User API", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/oauth2/google")
    public ResponseEntity<UserAuthenticationResponse> googleLogin(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        UserAuthenticationResponse response = userService.googleLogin(token);

        log.info("성공");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/register")
    public Api<UserResponse> createUser(@RequestBody UserRegisterRequest request) {
        UserRegistrationResponse response = userService.register(request);
        return Api.OK(UserResponse.builder()
                .userId(response.getUserId())
                .name(response.getName())
                .email(response.getEmail())
                .createdAt(response.getCreatedAt())
                .updatedAt(response.getUpdatedAt())
                .build());
    }

    @Operation(summary = "사용자 인증", description = "사용자 로그인 및 JWT 토큰을 발급합니다.")
    @PostMapping("/authenticate")
    public Api<UserAuthenticationResponse> authenticate(@RequestBody UserLoginRequest request) {
        var response = userService.authenticate(request);
        return Api.OK(response);
    }

    @Operation(summary = "리프레시 토큰", description = "리프레시 토큰을 사용해 새로운 액세스 토큰을 발급합니다.")
    @PostMapping("/refresh")
    public UserAuthenticationResponse refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        log.info("리프레쉬 부분");
        log.info("Authorization Header: " + authorizationHeader);
        String refreshToken;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            refreshToken = authorizationHeader.substring(7); // "Bearer " 접두사 제거
            log.info("Extracted Refresh Token: " + refreshToken);
        } else {
            throw new JwtException("Invalid refresh token format");
        }
        return userService.refreshToken(refreshToken);
    }

    @Operation(summary = "사용자 조회", description = "특정 ID를 가진 사용자의 정보를 조회합니다.")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public Api<UserResponse> getUser(@PathVariable Long userId) {
        var response = userService.getUserId(userId);

        return Api.OK(response);
    }

    @Operation(summary = "회원 정보 수정", description = "현재 인증된 사용자의 정보를 수정합니다.",security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/me")
    public Api<UserResponse> updateUser(Authentication authentication, @Valid @RequestBody UserUpdateRequest request) {
        var response = userService.updateUser((User)authentication.getPrincipal(), request);


        return Api.OK(response);
    }

    @Operation(summary = "회원 정보 불러오기", description = "현재 인증된 사용자의 정보를 불러옵니다.",security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/me")
    public Api<UserResponseMe> findByMe(Authentication authentication) {
        var response = userService.getUserMe(authentication);
        log.info("호출");
        return Api.OK(response);
    }

    @Operation(summary = "회원 탈퇴", description = "사용자 정보를 삭제합니다.")
    @PreAuthorize("authentication.principal.userId == #userId")
    @DeleteMapping("/{userId}")
    public Api<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return Api.OK("회원 탈퇴가 완료되었습니다");
    }
}


