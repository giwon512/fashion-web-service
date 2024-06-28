package com.fashionNav.controller;

import com.fashionNav.model.entity.Banner;
import com.fashionNav.model.entity.RawNews;
import com.fashionNav.service.NewsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "News API", description = "뉴스 관련 API")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // 단건 상세 조회
    @GetMapping("/news/detail/{newsId}")
    public ResponseEntity<RawNews> getDetailNews(@PathVariable("newsId") Long newsId) {
        return ResponseEntity.ok(newsService.getDetailNews(newsId));
    }

    // 카테고리별 전체 조회
    @GetMapping("/news/category/all/{category}")
    public ResponseEntity<List<RawNews>> getCategoryList(@PathVariable("category") String category) {
        return ResponseEntity.ok(newsService.getCategoryList(category));
    }

    // 전체 뉴스 조회 (페이징)
    @GetMapping("/raw-news")
    public ResponseEntity<Map<String, Object>> getAllRawNews(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        log.info("{},{}", pageNum, pageSize);
        return ResponseEntity.ok(newsService.getAllRawNews(pageNum, pageSize));
    }

    // 카테고리별 뉴스 조회 (페이징)
    @GetMapping("/news/category/{category}")
    public ResponseEntity<Map<String, Object>> getNewsByCategoryAndPage(
            @PathVariable("category") String category,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        log.info(category);
        return ResponseEntity.ok(newsService.getRawNewsByCategoryAndPage(category, pageNum, pageSize));
    }

    // 카테고리별 상위 3개 뉴스 조회
    @GetMapping("/top3")
    public ResponseEntity<Map<String, List<RawNews>>> getTop3NewsByCategories() {
        return ResponseEntity.ok(newsService.getTop3NewsByCategories());
    }

    // 상위 5개 배너 조회
    @GetMapping("/banners")
    public ResponseEntity<List<Banner>> getTop5Banners() {
        return ResponseEntity.ok(newsService.getTop5Banners());
    }

    // 뉴스 업데이트 (관리자 권한 필요)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/raw-news/{newsId}")
    public ResponseEntity<Void> updateRawNews(@PathVariable("newsId") Long newsId, @RequestBody RawNews rawNews) {
        newsService.updateRawNews(newsId, rawNews);
        return ResponseEntity.ok().build();
    }

    // 배너 업데이트 (관리자 권한 필요)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/banners/{bannerId}")
    public ResponseEntity<Void> updateBanner(@PathVariable("bannerId") Long bannerId, @RequestBody Banner banner) {
        newsService.updateBanner(bannerId, banner);
        return ResponseEntity.ok().build();
    }

    // 뉴스 삭제 (관리자 권한 필요)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/raw-news/{newsId}")
    public ResponseEntity<Void> deleteRawNews(@PathVariable("newsId") Long newsId) {
        newsService.deleteRawNews(newsId);
        return ResponseEntity.ok().build();
    }

    // 검색 기능
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchRawNews(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Map<String, Object> response = newsService.searchRawNews(keyword, page, size);
        return ResponseEntity.ok(response);
    }
}