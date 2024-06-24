package com.fashionNav.repository;

import com.fashionNav.model.entity.Brand;
import org.apache.ibatis.annotations.*;

import java.util.List;

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