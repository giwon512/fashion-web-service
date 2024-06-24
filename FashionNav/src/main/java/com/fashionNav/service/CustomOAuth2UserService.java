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

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        if (email != null) {
            Optional<User> optionalUser = userMapper.findByEmail(email);
            if (optionalUser.isEmpty()) {
                // Create a new user
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(oAuth2User.getAttribute("name"));
                newUser.setRole("ROLE_USER");
                newUser.setCreatedAt(LocalDateTime.now());
                newUser.setUpdatedAt(LocalDateTime.now());
                userMapper.insert(newUser);
            } else {
                // Update existing user's updatedAt field
                User existingUser = optionalUser.get();
                existingUser.setUpdatedAt(LocalDateTime.now());
                userMapper.update(existingUser);
            }
        }

        return oAuth2User;
    }
}
