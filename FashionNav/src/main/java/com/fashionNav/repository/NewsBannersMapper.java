package com.fashionNav.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fashionNav.model.dto.request.NewsBannerRequest;
import com.fashionNav.model.dto.response.NewsBannersResponse;

@Mapper
public interface NewsBannersMapper {

    @Select("SELECT title, created_date FROM Banner ORDER BY created_date DESC")
    List<NewsBannersResponse> newsbannerslist();

    @Update("UPDATE Banner SET title = #{dto.title}, url = #{dto.url}, image_url = #{dto.imageUrl}, description = #{dto.description} WHERE banner_id = #{bannerId}")
    void updatedBanner(@Param("bannerId") Long bannerId, @Param("dto") NewsBannerRequest dto);

    @Delete("DELETE FROM Banner WHERE banner_id = #{bannerId}")
    void deleteBanner(@Param("bannerId") Long bannerId);

    @Select("SELECT url FROM Banner WHERE url = #{url}")
    String selectBannerUrl(String url);

    @Insert("INSERT INTO Banner ( title, image_url, url, description, created_date ) VALUES ( #{title}, #{imageUrl}, #{url}, #{description}, #{createdDate} )")
    void insertBanner(NewsBannerRequest dto);

    
}
