package com.fashionNav.repository;


import com.fashionNav.model.dto.response.MainPageItemDetail;
import com.fashionNav.model.entity.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemMapper {

    @Select("SELECT * FROM ITEM WHERE item_id = #{itemId}")
    Item findItemById(int itemId);

    @Insert("INSERT INTO ITEM(name, description, price, brand, style) VALUES(#{name}, #{description}, #{price}, #{brand}, #{style})")
    @Options(useGeneratedKeys = true, keyProperty = "itemId")
    void insertItem(Item item);

    @Update("UPDATE ITEM SET name=#{name}, description=#{description}, price=#{price}, brand=#{brand}, style=#{style} WHERE item_id=#{itemId}")
    void updateItem(Item item);

    @Delete("DELETE FROM ITEM WHERE item_id=#{itemId}")
    void deleteItem(int itemId);

    @Select("SELECT * FROM ITEM")
    List<Item> findAllItems();

    @Select("SELECT * FROM ITEM WHERE style = #{style}")
    List<Item> findItemsByStyle(String style);

    @Select("SELECT * FROM ITEM WHERE brand = #{brand}")
    List<Item> findItemsByBrand(String brand);

    //메인페이지 아이템 이름, 스타일, imageUrl을 list로 나타내기
    @Select("SELECT i.name AS itemName, i.style AS itemStyle, img.url AS imageUrl " +
            "FROM ITEM_IMAGE ii " +
            "JOIN ITEM i ON ii.item_id = i.item_id " +
            "JOIN IMAGES img ON ii.image_id = img.image_id")
    List<MainPageItemDetail> getAllMainPageItemDetails();
}
