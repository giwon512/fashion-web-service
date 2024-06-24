package com.fashionNav.repository;

import com.fashionNav.model.entity.Style;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StyleMapper {

    @Insert("INSERT INTO Style (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "styleId")
    void insertStyle(Style style);

    @Select("SELECT * FROM Style WHERE style_id = #{styleId}")
    Style findStyleById(Long styleId);

    @Select("SELECT * FROM Style")
    List<Style> findAllStyles();

    @Update("UPDATE Style SET name = #{name} WHERE style_id = #{styleId}")
    void updateStyle(Style style);

    @Delete("DELETE FROM Style WHERE style_id = #{styleId}")
    void deleteStyle(Long styleId);
}