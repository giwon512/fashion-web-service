package com.fashionNav.controller;

import com.fashionNav.common.api.Api;
import com.fashionNav.common.success.SuccessCode;
import com.fashionNav.model.entity.Style;
import com.fashionNav.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * StyleController 클래스는 스타일과 관련된 API를 제공합니다.
 * 이 클래스는 모든 스타일 목록을 조회하는 엔드포인트를 정의합니다.
 * 스타일 목록은 데이터베이스에서 조회됩니다.
 */
@RestController
@RequestMapping("/api/styles")
@RequiredArgsConstructor
public class StyleController {
    private final StyleService styleService;

    @GetMapping
    public Api<List<Style>> getAllStyles() {
        List<Style> styles = styleService.findAllStyles();
        return Api.OK(styles, SuccessCode.OK);
    }
}
