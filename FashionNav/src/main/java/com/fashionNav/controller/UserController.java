package com.fashionNav.controller;


import com.fashionNav.common.api.Api;
import com.fashionNav.model.entity.User;
import com.fashionNav.model.user.UserRegisterRequest;
import com.fashionNav.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;



    @PostMapping("/register")
    public Api<User> createUser(@Valid @RequestBody UserRegisterRequest request) {
        User response = userService.register(request);
        return Api.OK(response);
    }

}

