package com.fashionNav.service;

import com.fashionNav.model.entity.User;
import com.fashionNav.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * CustomOAuth2UserService 클래스는 OAuth2 인증을 통해 사용자 정보를 처리하는 서비스 클래스입니다.
 * 이 클래스는 OAuth2 인증 요청을 처리하고, 사용자 정보를 로드하여 사용자 정보를 데이터베이스에 저장하거나 업데이트합니다.
 * 사용자가 처음 로그인하는 경우 새로운 사용자를 생성하고, 이미 존재하는 사용자인 경우 사용자의 정보를 업데이트합니다.
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2UserRequest를 통해 사용자 정보를 로드합니다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 사용자 정보에서 이메일을 추출합니다.
        String email = oAuth2User.getAttribute("email");

        if (email != null) {
            // 이메일로 사용자를 조회합니다.
            Optional<User> optionalUser = userMapper.findByEmail(email);
            if (optionalUser.isEmpty()) {
                // 사용자가 존재하지 않으면 새로운 사용자를 생성합니다.
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(oAuth2User.getAttribute("name"));
                newUser.setRole("ROLE_USER");
                newUser.setCreatedAt(LocalDateTime.now());
                newUser.setUpdatedAt(LocalDateTime.now());
                userMapper.insert(newUser);
            } else {
                // 사용자가 존재하면 업데이트 시간을 갱신합니다.
                User existingUser = optionalUser.get();
                existingUser.setUpdatedAt(LocalDateTime.now());
                userMapper.update(existingUser);
            }
        }

        // OAuth2User를 반환합니다.
        return oAuth2User;
    }
}