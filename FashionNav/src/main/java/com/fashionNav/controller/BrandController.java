package com.fashionNav.controller;

import com.fashionNav.common.api.Api;
import com.fashionNav.common.success.SuccessCode;
import com.fashionNav.model.entity.Brand;
import com.fashionNav.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * BrandController 클래스는 브랜드 관련 API를 제공합니다.
 * 이 클래스는 모든 브랜드 정보를 조회하는 엔드포인트를 정의합니다.
 */
@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public Api<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.findAllBrands();
        return Api.OK(brands, SuccessCode.OK);
    }
}
