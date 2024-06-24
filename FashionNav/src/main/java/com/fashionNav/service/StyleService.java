package com.fashionNav.service;

import com.fashionNav.model.entity.Style;
import com.fashionNav.repository.StyleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StyleService {
    private final StyleMapper styleMapper;

    public List<Style> findAllStyles() {
        return styleMapper.findAllStyles();
    }
}
