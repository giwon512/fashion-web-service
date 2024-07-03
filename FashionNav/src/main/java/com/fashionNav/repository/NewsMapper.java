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
public interface NewsMapper {



    //가공되지 않는 뉴스에서 가공된 뉴스로 삽입할 sql
    @Insert("INSERT INTO ProcessedNews (news_id,title, content, image_url, source, author, published_date, category) VALUES (#{newsId},#{title}, #{content}, #{imageUrl}, #{source}, #{author}, #{publishedDate}, #{category})")
    void saveProcessedNews(RawNews rawNews);

    //가공되지 않는 뉴스 전체조회 admin 메뉴에서 필요함
    @Select("SELECT * FROM Raw_News ORDER BY published_date DESC LIMIT #{pageNum}, #{pageSize}")
    List<RawNews> findRawNewsByPagination(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    //가공된 뉴스 전체 조회
    @Select("SELECT * FROM ProcessedNews ORDER BY published_date DESC LIMIT #{pageNum}, #{pageSize}")
    List<ProcessedNews> findProcessedNewsByPagination(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    //가공된 뉴스 카테고리별로 조회
    @Select("SELECT * FROM ProcessedNews WHERE category = #{category} ORDER BY published_date DESC LIMIT #{pageNum},#{pageSize}")
    List<ProcessedNews> findProcessedNewsByCategory(@Param("category") String category,int pageNum,int pageSize);

    //가공된 뉴스 카테고리별로 행 갯수 조회
    @Select("SELECT COUNT(*) FROM ProcessedNews WHERE category = #{category}")
    int countProcessedNewsByCategory(@Param("category") String category);

    //가공전 게시글 전체 조회
    @Select("SELECT COUNT(*) FROM Raw_News")
    long countRawNewsAll();

    //가공후 게시글 전체 조회
    @Select("SELECT COUNT(*) FROM ProcessedNews")
    long countProcessedNewsAll();

    //가공전 게시글 검색
    @Select("SELECT * FROM Raw_News WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%') LIMIT #{size} OFFSET #{offset}")
    List<RawNews> searchRawNews(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);

    //가공된 게시글 검색
    @Select("SELECT * FROM ProcessedNews WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%') LIMIT #{size} OFFSET #{offset}")
    List<ProcessedNews> searchProcessedNews(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);

    //가공전 게시글 검색 행 갯수
    @Select("SELECT COUNT(*) FROM Raw_News WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%')")
    int countSearchRawNews(@Param("keyword") String keyword);

    //가공된 게시글 검색 행 갯수
    @Select("SELECT COUNT(*) FROM ProcessedNews WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%')")
    int countSearchProcessedNews(@Param("keyword") String keyword);

    // 가공된 게시물 단건 상세 조회
    @Select("SELECT * from ProcessedNews where news_id = #{newsId}")
    ProcessedNews findByDetailNews(@Param("newsId") Long newsId);

    // 가공된 게시물 카테고리별로 3개씩 조회
    @Select("SELECT * FROM ProcessedNews WHERE category = #{category} " +
            "ORDER BY published_date DESC LIMIT 3")
    List<ProcessedNews> findTop3NewsByCategory(String category);


    @Select("SELECT img_content FROM test_image WHERE news_id = #{newsId}")
    String getImageByNewsId(@Param("newsId") Long newsId);

    @Select("SELECT * FROM ProcessedNews where category = #{category}")
    List<ProcessedNews> findByCategoryLists(String category);

    @Select("SELECT * FROM Banner ORDER BY created_date DESC LIMIT 5")
    List<Banner> findTop5Banners();

    //가공된 뉴스 업데이트
    @Update("UPDATE ProcessedNews SET title = #{ProcessedNews.title}, content = #{ProcessedNews.content}, image_url = #{ProcessedNews.imageUrl}, source = #{ProcessedNews.source}, author = #{ProcessedNews.author}, published_date = #{ProcessedNews.publishedDate}, category = #{ProcessedNews.category} WHERE news_id = #{newsId}")
    void updateProcessedNews(@Param("newsId") Long newsId, @Param("ProcessedNews") ProcessedNews processedNews);

    @Update("UPDATE Banner SET title = #{banner.title}, image_url = #{banner.imageUrl}, url = #{banner.url}, description = #{banner.description} WHERE banner_id = #{bannerId}")
    void updateBanner(@Param("bannerId") Long bannerId, @Param("banner") Banner banner);

    //가공전 뉴스 삭제
    @Delete("DELETE FROM Raw_News WHERE news_id = #{newsId}")
    void deleteRawNews(@Param("newsId") Long newsId);

    //가공된 뉴스 삭제
    @Delete("DELETE FROM ProcessedNews WHERE news_id = #{newsId}")
    void deleteProcessedNews(@Param("newsId") Long newsId);



}
