package com.fashionNav.repository;


import com.fashionNav.model.dto.response.NewsImageDetail;
import com.fashionNav.model.entity.NewsImage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsImageMapper {

    /**
     * 뉴스와 이미지를 연결하여 저장합니다.
     *
     * @param newsId  뉴스 ID
     * @param imageId 이미지 ID
     * @param isMain  메인 이미지 여부
     */
    @Insert("INSERT INTO NEWS_IMAGE (news_id, image_id, is_main) VALUES (#{newsId}, #{imageId}, #{isMain})")
    void insertNewsImage(@Param("newsId") int newsId, @Param("imageId") int imageId, @Param("isMain") boolean isMain);

    /**
     * 뉴스와 이미지의 상세 정보를 조회합니다.
     *
     * @return 뉴스 이미지 상세 정보 목록
     */
    @Select("SELECT n.news_id, n.title AS newsTitle, n.content AS newsContent, n.type AS newsType, n.source AS newsSource, n.author AS newsAuthor, " +
            "n.published_date AS newsPublishedDate, n.modified_date AS newsModifiedDate, n.visit_count AS newsVisitCount, n.like_count AS newsLikeCount, n.style AS newsStyle, " +
            "img.image_id, img.url AS imageUrl, img.alt_text AS imageAltText, img.caption AS imageCaption, ni.is_main AS isMain " +
            "FROM NEWS_IMAGE ni " +
            "JOIN NEWS n ON ni.news_id = n.news_id " +
            "JOIN IMAGES img ON ni.image_id = img.image_id")
    List<NewsImageDetail> getAllNewsImageDetails();

    /**
     * 특정 뉴스 ID에 대한 모든 이미지 정보를 조회합니다.
     *
     * @param newsId 뉴스 ID
     * @return 뉴스 이미지 목록
     */
    @Select("SELECT * FROM news_image WHERE news_id = #{newsId}")
    List<NewsImage> findImagesByNewsId(int newsId);

    /**
     * 뉴스와 이미지를 연결하는 정보를 삭제합니다.
     *
     * @param newsId  뉴스 ID
     * @param imageId 이미지 ID
     */
    @Delete("DELETE FROM NEWS_IMAGE WHERE news_id=#{newsId} AND image_id=#{imageId}")
    void deleteNewsImage(@Param("newsId") int newsId, @Param("imageId") int imageId);
}
