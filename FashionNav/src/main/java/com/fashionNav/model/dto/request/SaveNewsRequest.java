package com.fashionNav.model.dto.request;


import com.fashionNav.model.entity.Images;
import com.fashionNav.model.entity.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveNewsRequest {
    private NewsInfo news;
    private ImagesInfo images;
    private boolean isMain;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewsInfo {
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

        public News toEntity() {
            return News.builder()
                    .title(title)
                    .content(content)
                    .type(type)
                    .source(source)
                    .author(author)
                    .publishedDate(publishedDate)
                    .modifiedDate(modifiedDate)
                    .visitCount(visitCount)
                    .likeCount(likeCount)
                    .style(style)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImagesInfo {
        private String url;
        private String altText;
        private String caption;

        public Images toEntity() {
            return Images.builder()
                    .url(url)
                    .altText(altText)
                    .caption(caption)
                    .build();
        }
    }
}
