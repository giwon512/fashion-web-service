package com.fashionNav.service;

import com.fashionNav.model.entity.Banner;
import com.fashionNav.model.entity.ProcessedNews;
import com.fashionNav.model.entity.RawNews;
import com.fashionNav.repository.NewsCommentMapper;
import com.fashionNav.repository.NewsMapper;
import com.fashionNav.repository.UserSavePageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * NewsService 클래스는 뉴스(RawNews) 및 배너(Banner)와 관련된 비즈니스 로직을 처리합니다.
 * 이 클래스는 뉴스의 조회, 검색, 업데이트, 삭제 및 배너의 조회와 업데이트 기능을 제공합니다.
 * 다양한 카테고리의 뉴스와 배너를 가져오고, 페이징 처리 및 검색 기능을 지원합니다.
 */

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsMapper newsMapper;
    private final NewsCommentMapper newsCommentMapper;
    private final UserSavePageMapper userSavePageMapper;

    public Map<String, List<ProcessedNews>> getTop3NewsByCategories() {
        Map<String, List<ProcessedNews>> newsByCategory = new HashMap<>();
        String[] categories = {"celeb", "brand", "trend"};

        for (String category : categories) {
            List<ProcessedNews> top3News = newsMapper.findTop3NewsByCategory(category);
            newsByCategory.put(category, top3News);
        }

        return newsByCategory;
    }


    //가공된 뉴스 저장
    public void saveProcessedNews(RawNews rawNews) {
        newsMapper.saveProcessedNews(rawNews);
    }

    public String getImageByNewsId(Long newsId) {
    	String imgContent = newsMapper.getImageByNewsId(newsId);
    	return imgContent;
    }
    
    public List<Banner> getTop5Banners() {
        return newsMapper.findTop5Banners();
    }

    //변경점 ProcessedNews를 기준으로 정렬
    public Map<String, Object> getProcessedNewsByCategoryAndPage(String category, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<ProcessedNews> newsList = newsMapper.findProcessedNewsByCategory(category, offset, pageSize);
        long total = newsMapper.countProcessedNewsByCategory(category);
        Map<Long, String> imgContent = new HashMap<>(); 
        for(ProcessedNews news : newsList) {
        	String img = newsMapper.getImageByNewsId(news.getNewsId());
        	imgContent.put(news.getNewsId(), img);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("content", newsList);
        response.put("currentPage", pageNum);
        response.put("totalItems", total);
        response.put("totalPages", (int) Math.ceil((double) total / pageSize));
        response.put("imgContent", imgContent);
        return response;
    }

    //가공된 뉴스 전체 조회 allList에 필요함.
    public Map<String, Object> getAllProcessedNews(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<ProcessedNews> processedNewsList = newsMapper.findProcessedNewsByPagination(offset, pageSize);
        long total = newsMapper.countProcessedNewsAll();
        Map<Long, String> imgContent = new HashMap<>(); 
        for(ProcessedNews news : processedNewsList) {
        	String img = newsMapper.getImageByNewsId(news.getNewsId());
        	imgContent.put(news.getNewsId(), img);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("content", processedNewsList);
        response.put("currentPage", pageNum);
        response.put("totalItems", total);
        response.put("totalPages", (int) Math.ceil((double) total / pageSize));
        response.put("imgContent", imgContent);
        return response;
    }


    //admin 메뉴에 Raw_news 관리할때 필요함
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

    // 가공전 뉴스 검색 기능
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

    // 가공 후 뉴스 검색 기능
    public Map<String, Object> searchProcessedNews(String keyword, int page, int size) {
        int offset = (page - 1) * size;
        List<ProcessedNews> processedNewsList = newsMapper.searchProcessedNews(keyword, offset, size);
        int totalItems = newsMapper.countSearchProcessedNews(keyword);

        int totalPages = (int) Math.ceil((double) totalItems / size);

        Map<String, Object> response = new HashMap<>();
        response.put("content", processedNewsList);
        response.put("currentPage", page);
        response.put("totalItems", totalItems);
        response.put("totalPages", totalPages);

        return response;
    }


    public void updateProcessedNews(Long newsId, ProcessedNews processedNews) {
        newsMapper.updateProcessedNews(newsId, processedNews);
    }

    public void updateBanner(Long bannerId, Banner banner) {
        newsMapper.updateBanner(bannerId, banner);
    }

    //가공전 뉴스 삭제
    @Transactional
    public void deleteRawNews(Long newsId) {

        userSavePageMapper.deleteByNewsId(newsId);
        newsCommentMapper.deleteByNewsId(newsId);
        newsMapper.deleteRawNews(newsId);

    }

    //가공된 뉴스 삭제
    @Transactional
    public void deleteProcessedNews(Long newsId) {
        userSavePageMapper.deleteByNewsId(newsId);
        newsCommentMapper.deleteByNewsId(newsId);
        newsMapper.deleteProcessedNews(newsId);

    }




    public List<ProcessedNews> getCategoryList(String category) {
        return newsMapper.findByCategoryLists(category);
    }

    public ProcessedNews getDetailNews(Long newsId) {
        return newsMapper.findByDetailNews(newsId);
    }



//    public List<RawNews> getAllContents3() {
//        return newsMapper.getAllContents3();
//    }


}
