package com.fashionNav.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainPageNews {

    private String newsType;
    private String title;
    private String imageUrl;
    private int newsId; // newsId를 추가하여 상세페이지 이동 시 사용

}
