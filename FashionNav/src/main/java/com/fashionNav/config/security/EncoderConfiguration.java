package com.fashionNav.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * EncoderConfiguration
 *
 * 이 클래스는 BCryptPasswordEncoder를 빈으로 등록하여
 * 비밀번호 암호화 및 검증에 사용되도록 설정합니다.
 */
@Configuration
public class EncoderConfiguration {

    // BCryptPasswordEncoder를 빈으로 등록합니다.
    // 이 빈은 비밀번호를 암호화하거나 검증할 때 사용됩니다.
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
