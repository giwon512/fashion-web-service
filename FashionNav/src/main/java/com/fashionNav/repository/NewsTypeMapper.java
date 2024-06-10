package com.fashionNav.repository;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NewsTypeMapper {

    @Select("SELECT COUNT(*) FROM NEWS_TYPE WHERE type = #{type}")
    int countByType(String type);

    @Insert("INSERT INTO NEWS_TYPE (type) VALUES (#{type})")
    void insertType(String type);
}
