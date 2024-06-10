package com.fashionNav.repository;


import com.fashionNav.model.entity.Images;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImagesMapper {

    /**
     * 특정 이미지 ID로 이미지를 조회합니다.
     *
     * @param imageId 이미지 ID
     * @return 이미지 정보
     */
    @Select("SELECT * FROM IMAGES WHERE image_id = #{imageId}")
    Images findImageById(int imageId);

    /**
     * 이미지를 데이터베이스에 삽입합니다.
     *
     * @param image 삽입할 이미지 정보
     */
    @Insert("INSERT INTO IMAGES(url, alt_text, caption) VALUES(#{url}, #{altText}, #{caption})")
    @Options(useGeneratedKeys = true, keyProperty = "imageId")
    void insertImage(Images image);

    /**
     * 모든 이미지를 조회합니다.
     *
     * @return 이미지 목록
     */
    @Select("SELECT * FROM IMAGES")
    List<Images> findAllImages();

    /**
     * 특정 이미지 ID를 가진 이미지를 삭제합니다.
     *
     * @param imageId 이미지 ID
     */
    @Delete("DELETE FROM images WHERE image_id=#{imageId}")
    void deleteImage(int imageId);
}
