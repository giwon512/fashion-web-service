package com.fashionNav.model.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsComment {

    private Long commentId;
    private Long newsId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;




}
