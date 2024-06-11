package com.fashionNav.controller;

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

    @Operation(summary = "메인에서 그림을 클릭했을 때 상세화면 페이지 ", description = "상세화면에  표시될 뉴스 정보를 조회합니다.")
    @GetMapping("/details/{newsId}")
    public NewsDetailResponse getNewsDetail(@PathVariable int newsId) {
        return newsService.getNewsDetail(newsId);
    }

    @Operation(summary = "메인에 표시될 뉴스 요약 정보 조회 ", description = "메인에 표시될 상품 정보를 조회합니다.")
    @GetMapping("/summaries")
    public List<MainPageNewsDetail> getAllNewsSummaries() {
        return newsService.getAllNewsSummaries();
    }

    @Operation(summary = "뉴스와 이미지를 함께 저장", description = "뉴스와 이미지를 함께 저장합니다.")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveNewsWithImage(@RequestBody SaveNewsRequest saveNewsRequest) {
        newsService.saveNewsWithImage(saveNewsRequest);
    }

    @Operation(summary = "특정 ID의 뉴스 업데이트", description = "특정 ID의 뉴스를 업데이트합니다.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateNews(@RequestBody News news, @PathVariable int id) {
        news.setNewsId(id);
        newsService.updateNews(news);
    }

    @Operation(summary = "특정 ID의 뉴스 삭제", description = "특정 ID의 뉴스를 삭제합니다.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteNews(@PathVariable int id) {
        newsService.deleteNews(id);
    }

    @Operation(summary = "모든 뉴스 조회", description = "모든 뉴스를 조회합니다.")
    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @Operation(summary = "특정 ID의 뉴스 조회", description = "특정 ID의 뉴스를 조회합니다. 조회할 때 방문 횟수를 증가시킵니다.")
    @GetMapping("/{id}")
    public News getNewsById(@PathVariable int id) {
        newsService.increaseVisitCount(id);
        return newsService.getNewsById(id);
    }

    @Operation(summary = "특정 타입의 뉴스 조회", description = "특정 타입의 뉴스를 조회합니다.")
    @GetMapping("/category/{type}")
    public List<News> getNewsByType(@PathVariable String type) {
        return newsService.getNewsByType(type);
    }

    @Operation(summary = "뉴스 이미지 상세 정보 조회", description = "뉴스와 관련된 이미지 상세 정보를 모두 조회합니다.")
    @GetMapping("/details")
    public List<NewsImageDetail> getAllNewsImageDetails() {
        return newsService.getAllNewsImageDetails();
    }

    @Operation(summary = "특정 ID의 뉴스에 좋아요 추가", description = "특정 ID의 뉴스에 좋아요를 추가합니다.")
    @PostMapping("/{id}/like")
    public void likeNews(@PathVariable int id) {
        newsService.increaseLikeCount(id);
    }

    @Operation(summary = "모든 이미지 조회", description = "모든 이미지를 조회합니다.")
    @GetMapping("/images")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Images> getAllImages() {
        return newsService.getAllImages();
    }

    @Operation(summary = "특정 ID의 이미지 조회", description = "특정 ID의 이미지를 조회합니다.")
    @GetMapping("/images/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Images getImageById(@PathVariable int id) {
        return newsService.getImageById(id);
    }
}
