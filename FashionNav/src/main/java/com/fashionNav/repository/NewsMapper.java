package com.fashionNav.repository;


import com.fashionNav.model.entity.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {

    @Select("SELECT * FROM NEWS")
    List<News> findAll();

    @Select("SELECT * FROM NEWS WHERE news_id = #{newsId}")
    News findById(int newsId);

    @Insert("INSERT INTO NEWS (title, content, type, source, author, published_date, modified_date, visit_count, like_count, style) " +
            "VALUES (#{title}, #{content}, #{type}, #{source}, #{author}, #{publishedDate}, #{modifiedDate}, #{visitCount}, #{likeCount}, #{style})")
    @Options(useGeneratedKeys = true, keyProperty = "newsId")
    void insert(News news);

    @Update("UPDATE NEWS SET title = #{title}, content = #{content}, type = #{type}, source = #{source}, author = #{author}, published_date = #{publishedDate}, modified_date = #{modifiedDate}, visit_count = #{visitCount}, like_count = #{likeCount}, style = #{style} " +
            "WHERE news_id = #{newsId}")
    void update(News news);

    @Delete("DELETE FROM NEWS WHERE news_id = #{newsId}")
    void delete(int newsId);
}