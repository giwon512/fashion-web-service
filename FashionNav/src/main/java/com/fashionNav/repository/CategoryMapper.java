package com.fashionNav.repository;


import com.fashionNav.model.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CategoryMapper {

    @Select("SELECT * FROM CATEGORY")
    List<Category> findAll();

    @Select("SELECT * FROM CATEGORY WHERE style = #{style}")
    Category findById(String style);

    @Insert("INSERT INTO CATEGORY (style) VALUES (#{style})")
    void insert(Category category);

    @Update("UPDATE CATEGORY SET style = #{style} WHERE style = #{style}")
    void update(Category category);

    @Delete("DELETE FROM CATEGORY WHERE style = #{style}")
    void delete(String style);
}
