package com.fashionNav.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebConfiguration {


    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

     private final JwtExceptionFilter jwtExceptionFilter;



    //프론트 엔드와 통신할땨의 CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000","http://127.0.0.1:3000"));
        configuration.setAllowedMethods(List.of("GET","POST","PATCH","DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;



    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers(HttpMethod.POST,"/api/users/*","/api/users/authenticate")
                                .permitAll()
                                .requestMatchers(
                                        SWAGGER.toArray(new String[0])
                                ).permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/news/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/news/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/items/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/items/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/surveys/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/surveys/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement(
                        (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(CsrfConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter,jwtAuthenticationFilter.getClass())
                .httpBasic(HttpBasicConfigurer::disable);

        return http.build();
    }
}
