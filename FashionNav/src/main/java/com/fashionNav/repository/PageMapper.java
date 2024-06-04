package com.fashionNav.repository;


import com.fashionNav.model.entity.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PageMapper {

    @Select("SELECT * FROM PAGE")
    List<Page> findAll();

    @Select("SELECT * FROM PAGE WHERE page_id = #{pageId}")
    Page findById(int pageId);

    @Insert("INSERT INTO PAGE (url, title, description, created_at, content_type) " +
            "VALUES (#{url}, #{title}, #{description}, #{createdAt}, #{contentType})")
    @Options(useGeneratedKeys = true, keyProperty = "pageId")
    void insert(Page page);

    @Update("UPDATE PAGE SET url = #{url}, title = #{title}, description = #{description}, content_type = #{contentType} " +
            "WHERE page_id = #{pageId}")
    void update(Page page);

    @Delete("DELETE FROM PAGE WHERE page_id = #{pageId}")
    void delete(int pageId);
}