package com.fashionNav.repository;

import com.fashionNav.model.entity.Brand;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * BrandMapper 인터페이스는 브랜드 데이터를 관리하는 MyBatis 매퍼 인터페이스입니다.
 * 이 인터페이스는 브랜드 데이터를 조회, 삽입, 업데이트 및 삭제하는 메서드를 제공합니다.
 */
@Mapper
public interface BrandMapper {

    @Insert("INSERT INTO Brand (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "brandId")
    void insertBrand(Brand brand);

    @Select("SELECT * FROM Brand WHERE brand_id = #{brandId}")
    Brand findBrandById(Long brandId);

    @Select("SELECT * FROM Brand")
    List<Brand> findAllBrands();

    @Update("UPDATE Brand SET name = #{name} WHERE brand_id = #{brandId}")
    void updateBrand(Brand brand);

    @Delete("DELETE FROM Brand WHERE brand_id = #{brandId}")
    void deleteBrand(Long brandId);
}