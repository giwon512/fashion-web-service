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
import com.fashionNav.model.entity.User;
import com.fashionNav.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserRegistrationResponse register(UserRegisterRequest request) {


        userMapper
                .findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new ApiException(UserErrorCode.USER_ALREADY_EXISTS);
                });



        User user = new User();
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 비밀번호 암호화
        user.setName(request.getName());
        user.setRole("ROLE_USER"); // 기본 사용자 역할 설정
        user.setEmail(request.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());


        userMapper.insert(user);
        return UserRegistrationResponse.from(user);
    }


    public UserResponse getUserId(int userId) {
        var userEntity = userMapper.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        return UserResponse.from(userEntity,null,null);
    }




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userMapper.findByEmail(email)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND,email));
    }

    public UserAuthenticationResponse authenticate(UserLoginRequest body) {
        var userEntity = getUserEntityByUserEmail(body.getEmail());

        if(passwordEncoder.matches(body.getPassword(), userEntity.getPassword())){
            var accessToken = jwtService.generateAccessToken(userEntity);
            var refreshToken = jwtService.generateRefreshToken(userEntity);
            return new UserAuthenticationResponse(accessToken, refreshToken);
        } else {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD);
        }

    }

    /**
     * 이메일로 사용자 정보 조회
     * @param email 사용자 이메일
     * @return User 사용자 엔티티
     * @throws ApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    public User getUserEntityByUserEmail(String email) {
        return userMapper.findByEmail(email)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND,"존재하지 않는 유저입니다."));
    }


    public UserResponse updateUser(User currentUser, UserUpdateRequest request) {

        var userEntity =
                userMapper.findById(currentUser.getUserId()).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));


        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(request.getCurrentPassword(), userEntity.getPassword())) {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD);
        }

        // 이메일이 변경되었는지 확인
        boolean emailChanged = !userEntity.getEmail().equals(request.getEmail());

        // 사용자 정보 업데이트
        userEntity.setName(request.getName());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userEntity.setUpdatedAt(LocalDateTime.now());

        //사용자 정보 업데이트
        userMapper.update(userEntity);

        // 이메일이 변경되었다면 새로운 토큰 생성
        String newToken = null;
        String newRefreshToken = null;
        if (emailChanged) {
            newToken = jwtService.generateAccessToken(userEntity);
            newRefreshToken = jwtService.generateRefreshToken(userEntity);
        }

        return UserResponse.from(userEntity, newToken, newRefreshToken);
    }

    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
     * @param refreshToken 리프레시 토큰
     * @return UserAuthenticationResponse 새로운 액세스 토큰과 리프레시 토큰
     * @throws ApiException 유효하지 않은 리프레시 토큰인 경우 예외 발생
     */
    public UserAuthenticationResponse refreshToken(String refreshToken) {
        if (jwtService.isTokenValid(refreshToken)) {
            var username = jwtService.getUsername(refreshToken);
            var userEntity = getUserEntityByUserEmail(username);
            var newAccessToken = jwtService.generateAccessToken(userEntity);
            return new UserAuthenticationResponse(newAccessToken, refreshToken);
        } else {
            throw new ApiException(TokenErrorCode.INVALID_TOKEN, "유효하지 않은 리프레시 토큰입니다.");
        }
    }

    public void deleteUser(int userId) {
        User user = userMapper.findById(userId).get();
        if (user == null) {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND);
        }
        userMapper.deleteUser(userId);
    }


    /**
     * UserDetailsService 인터페이스 메서드 구현: 이메일로 사용자 정보 조회
     * @param email 사용자 이메일
     * @return UserDetails 사용자 세부 정보
     * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userMapper.findByEmail(email)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, email));
    }
}