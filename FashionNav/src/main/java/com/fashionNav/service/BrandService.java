package com.fashionNav.service;

import com.fashionNav.model.entity.Brand;
import com.fashionNav.repository.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CommentService 클래스는 게시물에 대한 댓글을 관리하는 서비스 클래스입니다.
 * 이 클래스는 댓글을 생성, 조회, 업데이트 및 삭제하는 메서드를 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandMapper brandMapper;

    public List<Brand> findAllBrands() {
        return brandMapper.findAllBrands();
    }
}
