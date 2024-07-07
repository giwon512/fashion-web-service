package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RawNews {
    private Long newsId;
    private String title;
    private String subtitle;
    private String content;
    private String imageUrl;
    private String source;
    private String author;
    private LocalDateTime publishedDate;
    private String category;
}
