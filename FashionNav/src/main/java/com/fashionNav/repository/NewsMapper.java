package com.fashionNav.repository;

import com.fashionNav.model.entity.Banner;
import com.fashionNav.model.entity.ProcessedNews;
import com.fashionNav.model.entity.RawNews;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * NewsMapper
 *
 * 이 인터페이스는 MyBatis를 사용하여 데이터베이스와 상호작용하는 News 관련 쿼리 메서드를 정의합니다.
 * 뉴스 및 배너 관련 데이터베이스 작업을 수행합니다.
 */
@Mapper
public interface NewsMapper {

    @Insert("INSERT INTO ProcessedNews (news_id, title, subtitle, content, image_url, source, author, published_date, category) VALUES (#{newsId}, #{title}, #{subtitle}, #{content}, #{imageUrl}, #{source}, #{author}, #{publishedDate}, #{category})")
    void saveProcessedNews(RawNews rawNews);

    @Select("SELECT * FROM Raw_News ORDER BY published_date DESC LIMIT #{pageNum}, #{pageSize}")
    List<RawNews> findRawNewsByPagination(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    @Select("SELECT * FROM ProcessedNews ORDER BY published_date DESC LIMIT #{pageNum}, #{pageSize}")
    List<ProcessedNews> findProcessedNewsByPagination(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    @Select("SELECT * FROM ProcessedNews WHERE category = #{category} ORDER BY published_date DESC LIMIT #{pageNum}, #{pageSize}")
    List<ProcessedNews> findProcessedNewsByCategory(@Param("category") String category, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(*) FROM ProcessedNews WHERE category = #{category}")
    int countProcessedNewsByCategory(@Param("category") String category);

    @Select("SELECT COUNT(*) FROM Raw_News")
    long countRawNewsAll();

    @Select("SELECT COUNT(*) FROM ProcessedNews")
    long countProcessedNewsAll();

    @Select("SELECT * FROM Raw_News WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%') LIMIT #{size} OFFSET #{offset}")
    List<RawNews> searchRawNews(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);

    @Select("SELECT * FROM ProcessedNews WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%') LIMIT #{size} OFFSET #{offset}")
    List<ProcessedNews> searchProcessedNews(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM Raw_News WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%')")
    int countSearchRawNews(@Param("keyword") String keyword);

    @Select("SELECT COUNT(*) FROM ProcessedNews WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%')")
    int countSearchProcessedNews(@Param("keyword") String keyword);

    @Select("SELECT * FROM ProcessedNews WHERE news_id = #{newsId}")
    ProcessedNews findByDetailNews(@Param("newsId") Long newsId);

    @Select("SELECT * FROM ProcessedNews WHERE category = #{category} ORDER BY published_date DESC LIMIT 3")
    List<ProcessedNews> findTop3NewsByCategory(@Param("category") String category);

    @Select("SELECT img_content FROM images WHERE img_id = #{newsId}")
    String getImageByNewsId(@Param("newsId") Long newsId);

    @Select("SELECT * FROM ProcessedNews WHERE category = #{category}")
    List<ProcessedNews> findByCategoryLists(@Param("category") String category);

    @Select("SELECT * FROM Banner ORDER BY created_date DESC LIMIT 5")
    List<Banner> findTop5Banners();

    @Update("UPDATE ProcessedNews SET title = #{processedNews.title}, subtitle = #{processedNews.subtitle}, content = #{processedNews.content}, image_url = #{processedNews.imageUrl}, source = #{processedNews.source}, author = #{processedNews.author}, published_date = #{processedNews.publishedDate}, category = #{processedNews.category} WHERE news_id = #{newsId}")
    void updateProcessedNews(@Param("newsId") Long newsId, @Param("processedNews") ProcessedNews processedNews);

    @Update("UPDATE Banner SET title = #{banner.title}, image_url = #{banner.imageUrl}, url = #{banner.url}, description = #{banner.description} WHERE banner_id = #{bannerId}")
    void updateBanner(@Param("bannerId") Long bannerId, @Param("banner") Banner banner);

    @Delete("DELETE FROM Raw_News WHERE news_id = #{newsId}")
    void deleteRawNews(@Param("newsId") Long newsId);

    @Delete("DELETE FROM ProcessedNews WHERE news_id = #{newsId}")
    void deleteProcessedNews(@Param("newsId") Long newsId);
}
