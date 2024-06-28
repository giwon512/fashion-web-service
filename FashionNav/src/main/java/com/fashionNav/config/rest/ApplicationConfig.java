package com.fashionNav.config.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 * ApplicationConfig 클래스는 애플리케이션 전반에서 사용할 설정을 정의하는 클래스입니다.
 * 이 클래스는 RestTemplate 빈을 생성하여 애플리케이션 내에서 HTTP 요청을 간편하게 처리할 수 있도록 합니다.
 *
 * 주요 메서드:
 * - restTemplate(): RestTemplate 객체를 빈으로 등록하여 애플리케이션에서 HTTP 요청을 처리하는 데 사용됩니다.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}