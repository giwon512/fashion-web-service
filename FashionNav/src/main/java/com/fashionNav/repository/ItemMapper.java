package com.fashionNav.repository;


import com.fashionNav.model.entity.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemMapper {

    @Select("SELECT * FROM ITEM")
    List<Item> findAll();

    @Select("SELECT * FROM ITEM WHERE item_id = #{itemId}")
    Item findById(int itemId);

    @Insert("INSERT INTO ITEM (name, description, price, brand, style) " +
            "VALUES (#{name}, #{description}, #{price}, #{brand}, #{style})")
    @Options(useGeneratedKeys = true, keyProperty = "itemId")
    void insert(Item item);

    @Update("UPDATE ITEM SET name = #{name}, description = #{description}, price = #{price}, brand = #{brand}, style = #{style} " +
            "WHERE item_id = #{itemId}")
    void update(Item item);

    @Delete("DELETE FROM ITEM WHERE item_id = #{itemId}")
    void delete(int itemId);
}