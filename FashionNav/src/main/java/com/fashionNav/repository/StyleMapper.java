package com.fashionNav.repository;

import com.fashionNav.model.entity.Style;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * StyleMapper 인터페이스는 스타일 데이터를 관리하는 MyBatis 매퍼 인터페이스입니다.
 * 이 인터페이스는 스타일 데이터를 조회, 삽입, 업데이트 및 삭제하는 메서드를 제공합니다.
 */

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