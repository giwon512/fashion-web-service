package com.fashionNav.controller;


import com.fashionNav.common.api.Api;
import com.fashionNav.common.error.ErrorCode;
import com.fashionNav.common.error.UserErrorCode;
import com.fashionNav.model.dto.request.*;
import com.fashionNav.model.dto.response.UserAuthenticationResponse;
import com.fashionNav.model.dto.response.UserRegistrationResponse;
import com.fashionNav.model.dto.response.UserResponse;
import com.fashionNav.model.dto.response.UserResponseMe;
import com.fashionNav.model.entity.User;
import com.fashionNav.repository.UserMapper;
import com.fashionNav.service.UserService;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * UserController 클래스는 사용자 관련 API를 제공합니다.
 * 이 클래스는 사용자 등록, 인증, 정보 조회 및 수정, 탈퇴 등의 기능을 포함합니다.
 * 사용자 인증 및 권한 부여를 위해 JWT 토큰을 사용하며, 특정 엔드포인트는 인증된 사용자나 관리자만 접근할 수 있습니다.
 */
@Slf4j
@Tag(name = "User API", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/oauth2/google")
    public ResponseEntity<UserAuthenticationResponse> googleLogin(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        UserAuthenticationResponse response = userService.googleLogin(token);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/register")
    public Api<UserResponse> createUser(@Valid @RequestBody UserRegisterRequest request) {
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
        return Api.OK(response);
    }

    @Operation(summary = "회원 탈퇴", description = "사용자 정보를 삭제합니다.")
    @PreAuthorize("authentication.principal.userId == #userId")
    @DeleteMapping("/{userId}")
    public Api<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return Api.OK("회원 탈퇴가 완료되었습니다");
    }

    @Operation(summary = "이메일 찾기", description = "사용자의 이메일을 찾습니다.")
    @PostMapping("/find-email")
    public ResponseEntity<String> findEmail(@RequestBody @Valid UserFindEmailRequest request) {
        Optional<String> email = userService.findEmailByNameAndPhoneNumber(request.getName(), request.getPhoneNumber());
        if (email.isPresent()) {
            return ResponseEntity.ok(email.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    @Operation(summary = "비밀번호 찾기 요청", description = "사용자의 비밀번호 재설정을 요청합니다.")
    @PostMapping("/request-password-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestBody @Valid PasswordResetRequest request) {
        Optional<User> userOptional = userService.findUserByEmailAndName(request.getEmail(), request.getName());
        if (userOptional.isPresent()) {
            userService.sendVerificationCode(request.getEmail());
            return ResponseEntity.ok("비밀번호 재설정 인증 코드가 이메일로 전송되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    @Operation(summary = "비밀번호 재설정", description = "사용자의 비밀번호를 재설정합니다.")
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordResetConfirmRequest request) {
        if (userService.verifyCode(request.getEmail(), request.getCode())) {
            userService.updatePasswordByEmail(request.getEmail(), request.getNewPassword());
            return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 인증 코드입니다.");
        }
    }
}


