package com.fashionNav.service;

import com.fashionNav.model.entity.User;
import com.fashionNav.model.user.UserRegisterRequest;
import com.fashionNav.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserMapper userMapper;

    public User register(UserRegisterRequest request) {



        User user = new User();
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.insert(user);
        return user;
    }





}
