package com.fashionNav.repository;


import com.fashionNav.model.dto.response.ItemImageDetail;
import com.fashionNav.model.entity.ItemImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemImageMapper {

    /**
     * ITEM_IMAGE 테이블에 새로운 아이템-이미지 관계를 삽입합니다.
     *
     * @param itemId 아이템의 ID
     * @param imageId 이미지의 ID
     * @param isMain 해당 이미지가 메인 이미지인지 여부
     */
    @Insert("INSERT INTO ITEM_IMAGE (item_id, image_id, is_main) VALUES (#{itemId}, #{imageId}, #{isMain})")
    void insertItemImage(@Param("itemId") int itemId, @Param("imageId") int imageId, @Param("isMain") boolean isMain);

    /**
     * 특정 아이템의 ID로 ITEM_IMAGE 테이블에서 아이템-이미지 관계를 조회합니다.
     *
     * @param itemId 아이템의 ID
     * @return 아이템-이미지 관계 목록
     */
    @Select("SELECT * FROM ITEM_IMAGE WHERE item_id=#{itemId}")
    List<ItemImage> findItemImagesByItemId(int itemId);

    /**
     * 모든 아이템 이미지 세부 정보를 조회합니다.
     * 조인된 결과를 통해 아이템과 이미지의 상세 정보를 가져옵니다.
     *
     * @return 아이템 이미지 세부 정보 목록
     */
    @Select("SELECT i.item_id, i.name AS itemName, i.description AS itemDescription, i.price AS itemPrice, i.brand AS itemBrand, i.style AS itemStyle, " +
            "img.image_id, img.url AS imageUrl, img.alt_text AS imageAltText, img.caption AS imageCaption, ii.is_main AS isMain " +
            "FROM ITEM_IMAGE ii " +
            "JOIN ITEM i ON ii.item_id = i.item_id " +
            "JOIN IMAGES img ON ii.image_id = img.image_id")
    List<ItemImageDetail> getAllItemImageDetails();
}
