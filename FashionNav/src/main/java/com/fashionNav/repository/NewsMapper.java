package com.fashionNav.repository;

import com.fashionNav.model.entity.Banner;
import com.fashionNav.model.entity.RawNews;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * NewsMapper
 *
 * 이 인터페이스는 MyBatis를 사용하여 데이터베이스와 상호작용하는 News 관련 쿼리 메서드를 정의합니다.
 * 뉴스 및 배너 관련 데이터베이스 작업을 수행합니다.
 */
public interface NewsMapper {




    @Select("SELECT * FROM Raw_News ORDER BY published_date DESC LIMIT #{pageNum}, #{pageSize}")
    List<RawNews> findRawNewsByPagination(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    @Select("SELECT * FROM Raw_News WHERE category = #{category} ORDER BY published_date DESC LIMIT #{pageNum},#{pageSize}")
    List<RawNews> findRawNewsByCategory(@Param("category") String category,int pageNum,int pageSize);



    @Select("SELECT COUNT(*) FROM Raw_News WHERE category = #{category}")
    int countRawNewsByCategory(@Param("category") String category);


    @Select("SELECT COUNT(*) FROM Raw_News")
    long countRawNewsAll();

    @Select("SELECT * FROM Raw_News WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%') LIMIT #{size} OFFSET #{offset}")
    List<RawNews> searchRawNews(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM Raw_News WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%')")
    int countSearchRawNews(@Param("keyword") String keyword);



    @Select("SELECT * from Raw_News where news_id = #{newsId}")
    RawNews findByDetailNews(@Param("newsId") Long newsId);


    @Select("SELECT * FROM Raw_News WHERE category = #{category} " +
            "ORDER BY published_date DESC LIMIT 3")
    List<RawNews> findTop3NewsByCategory(String category);


    @Select("SELECT * FROM Raw_News where category = #{category}")
    List<RawNews> findByCategoryLists(String category);

    @Select("SELECT * FROM Banner ORDER BY created_date DESC LIMIT 5")
    List<Banner> findTop5Banners();

    @Select("SELECT * FROM Raw_News ORDER BY published_date DESC")
    List<RawNews> findAllRawNews();

    @Update("UPDATE Raw_News SET title = #{rawNews.title}, content = #{rawNews.content}, image_url = #{rawNews.imageUrl}, source = #{rawNews.source}, author = #{rawNews.author}, published_date = #{rawNews.publishedDate}, category = #{rawNews.category} WHERE news_id = #{newsId}")
    void updateRawNews(@Param("newsId") Long newsId, @Param("rawNews") RawNews rawNews);

    @Update("UPDATE Banner SET title = #{banner.title}, image_url = #{banner.imageUrl}, url = #{banner.url}, description = #{banner.description} WHERE banner_id = #{bannerId}")
    void updateBanner(@Param("bannerId") Long bannerId, @Param("banner") Banner banner);

    @Delete("DELETE FROM Raw_News WHERE news_id = #{newsId}")
    void deleteRawNews(@Param("newsId") Long newsId);


    @Select("SELECT * FROM Raw_News")
    List<RawNews> getAllContents3();



}
