package com.fashionNav.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsImageDetail {
    private int newsId;
    private String newsTitle;
    private String newsContent;
    private String newsType;
    private String newsSource;
    private String newsAuthor;
    private LocalDateTime newsPublishedDate;
    private LocalDateTime newsModifiedDate;
    private int newsVisitCount;
    private int newsLikeCount;
    private String newsStyle;
    private int imageId;
    private String imageUrl;
    private String imageAltText;
    private String imageCaption;
    private boolean isMain;
}
