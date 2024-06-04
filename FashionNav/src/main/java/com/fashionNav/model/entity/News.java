package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private int newsId;          // 뉴스 ID (기본 키)
    private String title;        // 뉴스 제목
    private String content;      // 뉴스 내용
    private String type;         // 뉴스 유형
    private String source;       // 뉴스 출처
    private String author;       // 뉴스 작성자
    private LocalDateTime publishedDate;  // 뉴스 발행일
    private LocalDateTime modifiedDate;   // 뉴스 수정일
    private int visitCount;      // 뉴스 조회수
    private int likeCount;       // 뉴스 좋아요 수
    private String style;        // 뉴스 관련 스타일 (카테고리와 연관)
}