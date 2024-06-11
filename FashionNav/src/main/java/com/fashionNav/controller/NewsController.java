package com.fashionNav.controller;

import com.fashionNav.common.api.Api;
import com.fashionNav.model.dto.request.SaveNewsRequest;
import com.fashionNav.model.dto.response.MainPageNewsDetail;
import com.fashionNav.model.dto.response.NewsDetailResponse;
import com.fashionNav.model.dto.response.NewsImageDetail;
import com.fashionNav.model.entity.Images;
import com.fashionNav.model.entity.News;
import com.fashionNav.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "News API", description = "뉴스 관련 API")
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "메인에서 그림을 클릭했을 때 상세화면 페이지 ", description = "상세화면에 표시될 뉴스 정보를 조회합니다.")
    @GetMapping("/details/{newsId}")
    public Api<NewsDetailResponse> getNewsDetail(@PathVariable int newsId) {
        var response = newsService.getNewsDetail(newsId);
        return Api.OK(response);
    }

    @Operation(summary = "메인에 표시될 뉴스 요약 정보 조회 ", description = "메인에 표시될 뉴스 정보를 조회합니다.")
    @GetMapping("/summaries")
    public Api<List<MainPageNewsDetail>> getAllNewsSummaries() {
        var response = newsService.getAllNewsSummaries();
        return Api.OK(response);
    }

    @Operation(summary = "뉴스와 이미지를 함께 저장", description = "뉴스와 이미지를 함께 저장합니다.")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Api<Void> saveNewsWithImage(@RequestBody SaveNewsRequest saveNewsRequest) {
        newsService.saveNewsWithImage(saveNewsRequest);
        return Api.OK(null);
    }

    @Operation(summary = "특정 ID의 뉴스 업데이트", description = "특정 ID의 뉴스를 업데이트합니다.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Api<Void> updateNews(@RequestBody News news, @PathVariable int id) {
        news.setNewsId(id);
        newsService.updateNews(news);
        return Api.OK(null);
    }

    @Operation(summary = "특정 ID의 뉴스 삭제", description = "특정 ID의 뉴스를 삭제합니다.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Api<Void> deleteNews(@PathVariable int id) {
        newsService.deleteNews(id);
        return Api.OK(null);
    }

    @Operation(summary = "모든 뉴스 조회", description = "모든 뉴스를 조회합니다.")
    @GetMapping
    public Api<List<News>> getAllNews() {
        var response = newsService.getAllNews();
        return Api.OK(response);
    }

    @Operation(summary = "특정 ID의 뉴스 조회", description = "특정 ID의 뉴스를 조회합니다. 조회할 때 방문 횟수를 증가시킵니다.")
    @GetMapping("/{id}")
    public Api<News> getNewsById(@PathVariable int id) {
        newsService.increaseVisitCount(id);
        var response = newsService.getNewsById(id);
        return Api.OK(response);
    }

    @Operation(summary = "특정 타입의 뉴스 조회", description = "특정 타입의 뉴스를 조회하고 한 페이지당 10개의 뉴스가 보여집니다")
    @GetMapping("/category/{type}")
    public List<News> getNewsByTypePaged(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return newsService.getNewsByTypePaged(type, page, size);
    }

    @Operation(summary = "뉴스 이미지 상세 정보 조회", description = "뉴스와 관련된 이미지 상세 정보를 모두 조회합니다.")
    @GetMapping("/details")
    public Api<List<NewsImageDetail>> getAllNewsImageDetails() {
        var response = newsService.getAllNewsImageDetails();
        return Api.OK(response);
    }

    @Operation(summary = "특정 ID의 뉴스에 좋아요 추가", description = "특정 ID의 뉴스에 좋아요를 추가합니다.")
    @PostMapping("/{id}/like")
    public Api<Void> likeNews(@PathVariable int id) {
        newsService.increaseLikeCount(id);
        return Api.OK(null);
    }

    @Operation(summary = "모든 이미지 조회", description = "모든 이미지를 조회합니다.")
    @GetMapping("/images")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Api<List<Images>> getAllImages() {
        var response = newsService.getAllImages();
        return Api.OK(response);
    }

    @Operation(summary = "특정 ID의 이미지 조회", description = "특정 ID의 이미지를 조회합니다.")
    @GetMapping("/images/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Api<Images> getImageById(@PathVariable int id) {
        var response = newsService.getImageById(id);
        return Api.OK(response);
    }
}