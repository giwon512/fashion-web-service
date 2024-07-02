package com.fashionNav.service;

import com.fashionNav.common.error.TokenErrorCode;
import com.fashionNav.common.error.UserErrorCode;
import com.fashionNav.exception.ApiException;
import com.fashionNav.model.dto.request.UserLoginRequest;
import com.fashionNav.model.dto.request.UserRegisterRequest;
import com.fashionNav.model.dto.request.UserUpdateRequest;
import com.fashionNav.model.dto.response.UserAuthenticationResponse;
import com.fashionNav.model.dto.response.UserRegistrationResponse;
import com.fashionNav.model.dto.response.UserResponse;
import com.fashionNav.model.dto.response.UserResponseMe;
import com.fashionNav.model.entity.User;
import com.fashionNav.repository.UserMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;


/**
 * UserService 클래스는 사용자 등록, 인증, 업데이트, 삭제 등의 기능을 제공하는 서비스입니다.
 * Google OAuth2 로그인을 처리하며, JWT 토큰의 생성 및 검증을 담당합니다.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;


    public UserRegistrationResponse register(UserRegisterRequest request) {

        userMapper.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new ApiException(UserErrorCode.USER_ALREADY_EXISTS);
        });
        User user = new User();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole("ROLE_USER");
        user.setEmail(request.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setGender(request.getGender());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setBirthdate(LocalDate.parse(request.getBirthdate()));

        userMapper.insert(user);
        return UserRegistrationResponse.from(user);
    }

    public UserResponse getUserId(Long userId) {
        var userEntity = userMapper.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        return UserResponse.from(userEntity, null, null);
    }

    public UserAuthenticationResponse authenticate(UserLoginRequest body) {
        var userEntity = getUserEntityByUserEmail(body.getEmail());

        if (passwordEncoder.matches(body.getPassword(), userEntity.getPassword())) {
            var accessToken = jwtService.generateAccessToken(userEntity);
            var refreshToken = jwtService.generateRefreshToken(userEntity);
            return new UserAuthenticationResponse(accessToken, refreshToken,false);
        } else {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD);
        }
    }

    public User getUserEntityByUserEmail(String email) {
        return userMapper.findByEmail(email)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "존재하지 않는 유저입니다."));
    }

    public UserResponse updateUser(User currentUser, UserUpdateRequest request) {

        log.info("login");

        var userEntity = userMapper.findById(currentUser.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        boolean emailChanged = !userEntity.getEmail().equals(request.getEmail());

        // 구글 사용자 여부 확인
        boolean isGoogleUser = userEntity.getPassword() == null || userEntity.getPassword().isEmpty();

        // 구글 사용자가 아니고, 비밀번호 변경을 시도하는 경우에만 비밀번호 관련 처리
        if (!isGoogleUser && (request.getCurrentPassword() != null || request.getNewPassword() != null)) {
            if (request.getCurrentPassword() == null || request.getCurrentPassword().isEmpty()) {
                throw new ApiException(UserErrorCode.INVALID_PASSWORD, "현재 비밀번호는 필수 입력 값입니다.");
            }

            if (!passwordEncoder.matches(request.getCurrentPassword(), userEntity.getPassword())) {
                throw new ApiException(UserErrorCode.INVALID_PASSWORD);
            }

            if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
                userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
            }
        }

        userEntity.setName(request.getName());
        userEntity.setEmail(request.getEmail());
        userEntity.setUpdatedAt(LocalDateTime.now());
        userEntity.setGender(request.getGender());
        userEntity.setPhoneNumber(request.getPhoneNumber());
        userEntity.setBirthdate(LocalDate.parse(request.getBirthdate()));

        userMapper.update(userEntity);

        String newToken = null;
        String newRefreshToken = null;
        if (emailChanged) {
            newToken = jwtService.generateAccessToken(userEntity);
            newRefreshToken = jwtService.generateRefreshToken(userEntity);
        }

        return UserResponse.from(userEntity, newToken, newRefreshToken);
    }

    public UserAuthenticationResponse refreshToken(String refreshToken) {
        if (jwtService.isTokenValid(refreshToken)) {
            var username = jwtService.getUsername(refreshToken);
            var userEntity = getUserEntityByUserEmail(username);
            var newAccessToken = jwtService.generateAccessToken(userEntity);
            return new UserAuthenticationResponse(newAccessToken, refreshToken,false);
        } else {
            throw new ApiException(TokenErrorCode.INVALID_TOKEN, "유효하지 않은 리프레시 토큰입니다.");
        }
    }

    public void deleteUser(Long userId) {
        User user = userMapper.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        userMapper.deleteUser(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userMapper.findByEmail(email).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, email));
    }

    public UserResponseMe getUserMe(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return UserResponseMe.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .birthdate(user.getBirthdate())
                .role(user.getRole())
                .googleUser(user.getPassword() == null || user.getPassword().isEmpty())
                .build();
    }

    public UserAuthenticationResponse googleLogin(String token) {
        GoogleIdToken.Payload payload = verifyGoogleToken(token);
        String email = payload.getEmail();

        Optional<User> optionalUser = userMapper.findByEmail(email);
        User user;
        boolean isNewUser;

        if (optionalUser.isEmpty()) {
            user = new User();
            user.setEmail(email);
            user.setName((String) payload.get("name"));
            user.setPassword("");
            user.setRole("ROLE_USER");
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userMapper.insert(user);
            isNewUser = true; // 신규 가입
            log.info("true");
        } else {
            user = optionalUser.get();
            user.setUpdatedAt(LocalDateTime.now());
            userMapper.update(user);
            isNewUser = false; // 기존 사용자
            log.info("false");
        }

        String jwt = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new UserAuthenticationResponse(jwt, refreshToken, isNewUser);
    }


    private GoogleIdToken.Payload verifyGoogleToken(String token) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections
                        .singletonList(googleClientId))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                return idToken.getPayload();
            } else {
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, "Invalid ID token.");
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new ApiException(TokenErrorCode.INVALID_TOKEN, "Failed to verify token");
        }
    }
}