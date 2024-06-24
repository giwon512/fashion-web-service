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
