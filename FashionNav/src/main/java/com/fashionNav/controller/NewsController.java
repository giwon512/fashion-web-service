package com.fashionNav.controller;

import com.fashionNav.model.entity.Banner;
import com.fashionNav.model.entity.ProcessedNews;
import com.fashionNav.model.entity.RawNews;
import com.fashionNav.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * NewsController 클래스는 뉴스와 관련된 API를 제공합니다.
 * 이 클래스는 뉴스의 조회, 업데이트, 삭제 및 검색 기능을 수행하는 엔드포인트를 정의합니다.
 * 관리자는 특정 뉴스와 배너를 업데이트하거나 삭제할 수 있습니다.
 */
@Tag(name = "News API", description = "뉴스 관련 API")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "ProcessedNews 저장", description = "가공전 뉴스를 edit후 save 클릭시 가공된 뉴스 테이블에 저장됨")
    @PostMapping("/processed-news")
    public ResponseEntity<Void> saveProcessedNews(@RequestBody RawNews rawNews) {
        newsService.saveProcessedNews(rawNews);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "뉴스 상세 조회", description = "특정 뉴스의 상세 정보를 조회합니다.")
    @GetMapping("/news/detail/{newsId}")
    public ResponseEntity<ProcessedNews> getDetailNews(@PathVariable("newsId") Long newsId) {
        return ResponseEntity.ok(newsService.getDetailNews(newsId));
    }

    @Operation(summary = "카테고리별 뉴스 조회", description = "특정 카테고리의 모든 뉴스를 조회합니다.")
    @GetMapping("/news/category/all/{category}")
    public ResponseEntity<List<ProcessedNews>> getCategoryList(@PathVariable("category") String category) {
        return ResponseEntity.ok(newsService.getCategoryList(category));
    }

    @Operation(summary = "가공전 전체 뉴스 조회 (페이징)", description = "페이징을 통해 전체 뉴스를 조회합니다 (admin 뉴스관리에 필요함)")
    @GetMapping("/raw-news")
    public ResponseEntity<Map<String, Object>> getAllRawNews(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        log.info("{},{}", pageNum, pageSize);
        return ResponseEntity.ok(newsService.getAllRawNews(pageNum, pageSize));
    }

    @Operation(summary = "가공 후 전체 뉴스 조회 (페이징)", description = "페이징을 통해 전체 뉴스를 조회합니다.")
    @GetMapping("/processed-news")
    public ResponseEntity<Map<String, Object>> getAllProcessedNews(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        log.info("{},{}", pageNum, pageSize);
        return ResponseEntity.ok(newsService.getAllProcessedNews(pageNum, pageSize));
    }

    @Operation(summary = "카테고리별 뉴스 조회 (페이징)", description = "특정 카테고리의 뉴스를 페이징을 통해 조회합니다.")
    @GetMapping("/news/category/{category}")
    public ResponseEntity<Map<String, Object>> getNewsByCategoryAndPage(
            @PathVariable("category") String category,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    	if("best".equals(category)) {
    		return ResponseEntity.ok(newsService.getAllProcessedNews(1, 10));
    	}
        return ResponseEntity.ok(newsService.getProcessedNewsByCategoryAndPage(category, pageNum, pageSize));
    }

    @Operation(summary = "카테고리별 상위 3개 뉴스 조회", description = "각 카테고리의 상위 3개 뉴스를 조회합니다.")
    @GetMapping("/top3")
    public ResponseEntity<Map<String, List<ProcessedNews>>> getTop3NewsByCategories() {
        return ResponseEntity.ok(newsService.getTop3NewsByCategories());
    }

    @Operation(summary = "뉴스 아이디에 해당하는 이미지 조회", description = "뉴스 아이디에 해당하는 이미지의 base64URL을 조회합니다.")
    @GetMapping("/top3/{newsId}")
    public ResponseEntity<String> getImgByNewsId(@PathVariable("newsId") Long newsId){
        return ResponseEntity.ok(newsService.getImageByNewsId(newsId));
    }

    @Operation(summary = "상위 5개 배너 조회", description = "상위 5개의 배너를 조회합니다.")
    @GetMapping("/banners")
    public ResponseEntity<List<Banner>> getTop5Banners() {
        return ResponseEntity.ok(newsService.getTop5Banners());
    }

    @Operation(summary = "가공된 뉴스 업데이트", description = "가공된 뉴스를 업데이트합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/processed-news/{newsId}")
    public ResponseEntity<Void> updateRawNews(@PathVariable("newsId") Long newsId, @RequestBody ProcessedNews processedNews) {
        newsService.updateProcessedNews(newsId, processedNews);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "배너 업데이트", description = "특정 배너를 업데이트합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/banners/{bannerId}")
    public ResponseEntity<Void> updateBanner(@PathVariable("bannerId") Long bannerId, @RequestBody Banner banner) {
        newsService.updateBanner(bannerId, banner);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "가공전 뉴스 삭제", description = "가공전 뉴스를 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/raw-news/{newsId}")
    public ResponseEntity<Void> deleteRawNews(@PathVariable("newsId") Long newsId) {
        newsService.deleteRawNews(newsId);
        log.info("가공전 뉴스 삭제");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "가공된 뉴스 삭제", description = "가공된 뉴스를 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/processed-news/{newsId}")
    public ResponseEntity<Void> deleteProcessedNews(@PathVariable("newsId") Long newsId) {
        newsService.deleteProcessedNews(newsId);
        log.info("가공된 뉴스 삭제");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "가공된 뉴스 검색", description = "키워드를 통해 가공된 뉴스를 검색합니다.")
    @GetMapping("/processed-news/search")
    public ResponseEntity<Map<String, Object>> searchProcessedNews(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Map<String, Object> response = newsService.searchProcessedNews(keyword, page, size);
        return ResponseEntity.ok(response);
    }
}
