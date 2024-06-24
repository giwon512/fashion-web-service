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
