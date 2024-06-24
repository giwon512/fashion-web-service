package com.fashionNav.service;

import com.fashionNav.model.entity.Brand;
import com.fashionNav.repository.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandMapper brandMapper;

    public List<Brand> findAllBrands() {
        return brandMapper.findAllBrands();
    }
}
