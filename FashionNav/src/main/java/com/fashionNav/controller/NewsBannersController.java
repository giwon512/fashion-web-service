package com.fashionNav.controller;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fashionNav.common.api.Api;
import com.fashionNav.model.dto.request.NewsBannerRequest;
import com.fashionNav.model.dto.response.NewsBannersResponse;
import com.fashionNav.service.NewsBannersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Banners API", description = "배너 API")
@Slf4j
@RestController
@RequestMapping("/api/processedbanner")
@RequiredArgsConstructor
public class NewsBannersController {

    private final NewsBannersService newsBannersService;

    // 가공된 뉴스를 배너에 넣는 기능
    @Operation(summary = "Banner 저장", description = "뉴스를 가공해 배너에 넣는 기능")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Api<String> insertProcessedBanner(@RequestBody NewsBannerRequest dto) {
        newsBannersService.insertProcessedBanner(dto);

        return Api.OK("배너로 저장 성공");
    }

    // 배너 뉴스 목록
    @Operation(summary = "Banner 목록", description = "배너 목록 보여주는 기능")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Api<List<NewsBannersResponse>> NewsBannersList() {
        List<NewsBannersResponse> banners = newsBannersService.NewsBannersList();

        return Api.OK(banners);
    }

    // 배너 업데이트
    @Operation(summary = "Banner 수정", description = "배너 수정하는 기능")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{bannerId}")
    public Api<String> updatedBanner(@PathVariable("bannerId") Long bannerId, @RequestBody NewsBannerRequest dto) {
        newsBannersService.updatedBanner(bannerId, dto);

        return Api.OK("배너 업데이트 완료");
    }

    // 배너 삭제
    @Operation(summary = "Banner 삭제", description = "배너 삭제하는 기능")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{bannerId}/delete")
    public Api<String> deleteBanner(@PathVariable("bannerId") Long bannerId) {
        newsBannersService.deleteBanner(bannerId);
        return Api.OK("배너 삭제 완료");
    }

    
}
