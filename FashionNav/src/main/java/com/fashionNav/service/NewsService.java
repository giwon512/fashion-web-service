package com.fashionNav.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionNav.controller.NewsController;
import com.fashionNav.model.entity.Banner;
import com.fashionNav.model.entity.Brand;
import com.fashionNav.model.entity.ProcessedNews;
import com.fashionNav.model.entity.RawNews;
import com.fashionNav.model.entity.Style;
import com.fashionNav.model.entity.User;
import com.fashionNav.model.entity.UserSurvey;
import com.fashionNav.repository.NewsCommentMapper;
import com.fashionNav.repository.NewsMapper;
import com.fashionNav.repository.UserSavePageMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * NewsService 클래스는 뉴스(RawNews) 및 배너(Banner)와 관련된 비즈니스 로직을 처리합니다.
 * 이 클래스는 뉴스의 조회, 검색, 업데이트, 삭제 및 배너의 조회와 업데이트 기능을 제공합니다.
 * 다양한 카테고리의 뉴스와 배너를 가져오고, 페이징 처리 및 검색 기능을 지원합니다.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsMapper newsMapper;
    private final NewsCommentMapper newsCommentMapper;
    private final UserSavePageMapper userSavePageMapper;
    private final UserSurveyService userSurveyService;

    public Map<String, List<ProcessedNews>> getTop3NewsByCategories() {
        Map<String, List<ProcessedNews>> newsByCategory = new HashMap<>();
        String[] categories = {"celeb", "brand", "trend"};

        for (String category : categories) {
            List<ProcessedNews> top3News = newsMapper.findTop3NewsByCategory(category);
            newsByCategory.put(category, top3News);
        }

        return newsByCategory;
    }

    public List<ProcessedNews> getTop3NewsByCategoriesAndPreference(String category, User currentUser) {
    	Long userId = currentUser.getUserId();
    	//해당 카테고리의 뉴스 1000개 가져옴
    	List<ProcessedNews> newsList = newsMapper.findProcessedNewsByCategory(category, 0, 1000);
    	if(newsList.size() == 0) {
    		return null;
    	}
    	
    	//해당 유저의 설문조사 결과 가져오기
    	List<UserSurvey> userSurveys = userSurveyService.getUserSurveysByUserId(userId);
    	
    	//설문조사가 없을 때 예외처리
    	if(userSurveys.size() == 0) {
    		if(newsList.size() >= 3)
    			return newsList.subList(0, 3);
    		else
    			return newsList.subList(0, newsList.size());
    	}
    	List<Style> styleList = userSurveyService.findStylesBySurveyId(userSurveys.get(0).getSurveyId());
    	List<Brand> brandList = userSurveyService.findBrandsBySurveyId(userSurveys.get(0).getSurveyId());
    	
    	//관심있는 스타일, 브랜드의 키워드를 가지고 있는 뉴스 필터링
    	List<ProcessedNews> filteredList = newsList.stream().filter(news -> {
    		for(Style style : styleList) {
    			if(news.getTitle().contains(style.getName()) || news.getContent().contains(style.getName())) {
    				return true;
    			}
    		}
    		for(Brand brand : brandList) {
    			if(news.getTitle().contains(brand.getName()) || news.getContent().contains(brand.getName())) {
    				return true;
    			}
    		}
    		return false;
    	}).collect(Collectors.toList());
    	
    	//리턴할 리스트의 크기 3개로 맞춰서 리턴해주기
    	if(filteredList.size() >= 3) {
    		return filteredList.subList(0, 3);
    	}
    	else {
    		int add = 3 - filteredList.size();
    		for(int i = 0; i < add; i++) {
    			//전체 리스트에서도 게시글이 3개 이하인 경우 예외처리
    			if(i == newsList.size())
    				break;
    			
    			filteredList.add(newsList.get(i));
    		}
    		return filteredList;
    	}
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
