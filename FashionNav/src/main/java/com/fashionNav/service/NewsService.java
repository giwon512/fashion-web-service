package com.fashionNav.service;

import com.fashionNav.model.entity.Banner;
import com.fashionNav.model.entity.RawNews;
import com.fashionNav.repository.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsMapper newsMapper;

    public Map<String, List<RawNews>> getTop3NewsByCategories() {
        Map<String, List<RawNews>> newsByCategory = new HashMap<>();
        String[] categories = {"celeb", "brand", "trend"};

        for (String category : categories) {
            List<RawNews> top3News = newsMapper.findTop3NewsByCategory(category);
            newsByCategory.put(category, top3News);
        }

        return newsByCategory;
    }

    public List<Banner> getTop5Banners() {
        return newsMapper.findTop5Banners();
    }

    public Map<String, Object> getRawNewsByCategoryAndPage(String category, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<RawNews> newsList = newsMapper.findRawNewsByCategory(category, offset, pageSize);
        long total = newsMapper.countRawNewsByCategory(category);

        Map<String, Object> response = new HashMap<>();
        response.put("content", newsList);
        response.put("currentPage", pageNum);
        response.put("totalItems", total);
        response.put("totalPages", (int) Math.ceil((double) total / pageSize));
        return response;
    }

    public Map<String, Object> getAllRawNews(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<RawNews> rawNewsList = newsMapper.findRawNewsByPagination(offset, pageSize);
        long total = newsMapper.countRawNewsAll();

        Map<String, Object> response = new HashMap<>();
        response.put("content", rawNewsList);
        response.put("currentPage", pageNum);
        response.put("totalItems", total);
        response.put("totalPages", (int) Math.ceil((double) total / pageSize));
        return response;
    }

    // 검색 기능
    public Map<String, Object> searchRawNews(String keyword, int page, int size) {
        int offset = (page - 1) * size;
        List<RawNews> rawNewsList = newsMapper.searchRawNews(keyword, offset, size);
        int totalItems = newsMapper.countSearchRawNews(keyword);

        int totalPages = (int) Math.ceil((double) totalItems / size);

        Map<String, Object> response = new HashMap<>();
        response.put("content", rawNewsList);
        response.put("currentPage", page);
        response.put("totalItems", totalItems);
        response.put("totalPages", totalPages);

        return response;
    }


    public void updateRawNews(Long newsId, RawNews rawNews) {
        newsMapper.updateRawNews(newsId, rawNews);
    }

    public void updateBanner(Long bannerId, Banner banner) {
        newsMapper.updateBanner(bannerId, banner);
    }

    public void deleteRawNews(Long newsId) {
        newsMapper.deleteRawNews(newsId);
    }

    public List<RawNews> getCategoryList(String category) {
        return newsMapper.findByCategoryLists(category);
    }

    public RawNews getDetailNews(Long newsId) {
        return newsMapper.findByDetailNews(newsId);
    }

    public List<RawNews> getAllContents3() {
        return newsMapper.getAllContents3();
    }


}
