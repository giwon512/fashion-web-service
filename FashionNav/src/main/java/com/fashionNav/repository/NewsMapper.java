package com.fashionNav.repository;


import com.fashionNav.model.dto.response.MainPageNews;
import com.fashionNav.model.dto.response.NewsDetailResponse;
import com.fashionNav.model.entity.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {
    @Select("SELECT * FROM NEWS WHERE news_id = #{newsId}")
    News findNewsById(int newsId);

    @Insert("INSERT INTO NEWS(title, content, type, source, author, published_date, visit_count, like_count, style) VALUES(#{title}, #{content}, #{type}, #{source}, #{author}, #{publishedDate}, #{visitCount}, #{likeCount}, #{style})")
    @Options(useGeneratedKeys = true, keyProperty = "newsId")
    void insertNews(News news);

    @Update("UPDATE NEWS SET title=#{title}, content=#{content}, type=#{type}, source=#{source}, author=#{author}, published_date=#{publishedDate}, modified_date=#{modifiedDate}, visit_count=#{visitCount}, like_count=#{likeCount}, style=#{style} WHERE news_id=#{newsId}")
    void updateNews(News news);

    @Delete("DELETE FROM NEWS WHERE news_id=#{newsId}")
    void deleteNews(int newsId);

    @Select("SELECT * FROM NEWS")
    List<News> findAllNews();

    @Update("UPDATE NEWS SET visit_count = visit_count + 1 WHERE news_id = #{newsId}")
    void increaseVisitCount(int newsId);

    @Update("UPDATE NEWS SET like_count = like_count + 1 WHERE news_id = #{newsId}")
    void increaseLikeCount(int newsId);

    @Select("SELECT * FROM NEWS WHERE type = #{type} ORDER BY published_date ASC LIMIT #{pageSize} OFFSET #{offset}")
    List<News> getNewsByTypePaged(@Param("type") String type, @Param("pageSize") int pageSize, @Param("offset") int offset);

    //메인화면 뉴스 list로 보여주기
    @Select("SELECT n.type AS newsType, n.title AS title, img.url AS imageUrl " +
            "FROM NEWS n " +
            "JOIN NEWS_IMAGE ni ON n.news_id = ni.news_id " +
            "JOIN IMAGES img ON ni.image_id = img.image_id")
    List<MainPageNews> getAllNewsSummaries();


    @Select("SELECT n.title, i.url AS imageUrl, n.published_at AS publishedAt, n.type AS newsType " +
            "FROM NEWS n " +
            "JOIN IMAGES i ON n.image_id = i.image_id " +
            "WHERE n.news_id = #{newsId}")
    NewsDetailResponse getNewsDetail(@Param("newsId") int newsId);
}