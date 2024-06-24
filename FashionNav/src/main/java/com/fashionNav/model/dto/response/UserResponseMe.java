package com.fashionNav.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
** 마이페이지에 필요한 정보 불러오는 dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseMe {

    private int userId;
    private String name;
    private String email;
    private String role;

}
