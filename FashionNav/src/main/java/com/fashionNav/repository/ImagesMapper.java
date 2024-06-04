package com.fashionNav.repository;


import com.fashionNav.model.entity.Images;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImagesMapper {

    @Select("SELECT * FROM IMAGES")
    List<Images> findAll();

    @Select("SELECT * FROM IMAGES WHERE image_id = #{imageId}")
    Images findById(int imageId);

    @Insert("INSERT INTO IMAGES (url, alt_text, caption) " +
            "VALUES (#{url}, #{altText}, #{caption})")
    @Options(useGeneratedKeys = true, keyProperty = "imageId")
    void insert(Images images);

    @Update("UPDATE IMAGES SET url = #{url}, alt_text = #{altText}, caption = #{caption} " +
            "WHERE image_id = #{imageId}")
    void update(Images images);

    @Delete("DELETE FROM IMAGES WHERE image_id = #{imageId}")
    void delete(int imageId);
}