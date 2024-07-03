package com.fashionNav.model.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCommentResponse {
	
	private String commentType;
    private int commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String postTitle;
    private String newsTitle;
    private int postId; // 추가
    private Long newsId; // 추가
}
