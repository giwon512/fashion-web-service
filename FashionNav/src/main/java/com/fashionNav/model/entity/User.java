package com.fashionNav.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    private Long userId;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String gender; // 성별 추가
    private String phoneNumber = ""; // 전화번호 추가, 기본값으로 빈 문자열 설정
    private LocalDate birthdate; // 생일 추가
    private String role; // 역할을 문자열로 저장

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(this.role.equals("ROLE_ADMIN")){
            return List.of(
                    new SimpleGrantedAuthority("ROLE_" + "ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            );

        }else{
            return List.of(
                    new SimpleGrantedAuthority("ROLE_" + "ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
