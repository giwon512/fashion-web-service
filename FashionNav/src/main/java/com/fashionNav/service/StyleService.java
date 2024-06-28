package com.fashionNav.service;

import com.fashionNav.model.entity.Style;
import com.fashionNav.repository.StyleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * StyleService 클래스는 스타일(Style)과 관련된 비즈니스 로직을 처리합니다.
 * 이 클래스는 스타일 목록을 조회하는 기능을 제공합니다.
 * 스타일 데이터를 데이터베이스에서 조회하기 위해 StyleMapper를 사용합니다.
 */
@Service
@RequiredArgsConstructor
public class StyleService {
    private final StyleMapper styleMapper;

    public List<Style> findAllStyles() {
        return styleMapper.findAllStyles();
    }
}
