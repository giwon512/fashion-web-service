package com.fashionNav.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainPageNewsDetail {

    private String newsType;
    private String title;
    private String imageUrl;
}
