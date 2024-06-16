package com.fashionNav.model.dto.response;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {
    private int newsId;
    private String title;
    private String content;
    private String type;
    private String source;
    private String author;
    private LocalDateTime publishedDate;
    private LocalDateTime modifiedDate;
    private int visitCount;
    private int likeCount;
    private String style;
    private String imageUrl; // 이미지 URL을 추가합니다
}


