package com.fashionNav.service;


import com.fashionNav.model.dto.request.SaveNewsRequest;
import com.fashionNav.model.dto.response.MainPageNews;
import com.fashionNav.model.dto.response.NewsDetailResponse;
import com.fashionNav.model.dto.response.NewsImageDetail;
import com.fashionNav.model.entity.Images;
import com.fashionNav.model.entity.News;
import com.fashionNav.model.entity.NewsImage;
import com.fashionNav.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper newsMapper;
    private final ImagesMapper imagesMapper;
    private final NewsImageMapper newsImageMapper;
    private final NewsTypeMapper newsTypeMapper;
    private final CategoryMapper categoryMapper;


    //메인 페이지 뉴스
    public List<MainPageNews> getAllNewsSummaries() {
        return newsMapper.getAllNewsSummaries();
    }



    @Transactional
    public void saveNewsWithImage(SaveNewsRequest saveNewsRequest) {

        // NewsType 존재 여부 확인 및 삽입
        String newsType = saveNewsRequest.getNews().getType();
        if (newsTypeMapper.countByType(newsType) == 0) {
            newsTypeMapper.insertType(newsType);
        }


        String style = saveNewsRequest.getNews().getStyle();
        if (categoryMapper.countByStyle(style) == 0) {
            categoryMapper.insertStyle(style);
        }
        // News 저장
        News news = saveNewsRequest.getNews().toEntity();
        news.setPublishedDate(LocalDateTime.now());
        //news.setModifiedDate(LocalDateTime.now());
        newsMapper.insertNews(news);

        // Images 저장
        Images image = saveNewsRequest.getImages().toEntity();
        imagesMapper.insertImage(image);

        // NewsImage 저장
        NewsImage newsImage = new NewsImage(news.getNewsId(), image.getImageId(), saveNewsRequest.isMain());
        newsImageMapper.insertNewsImage(newsImage.getNewsId(), newsImage.getImageId(), newsImage.isMain());
    }

    public List<News> getAllNews() {
        return newsMapper.findAllNews();
    }

    public News getNewsById(int id) {
        return newsMapper.findNewsById(id);
    }

    public void updateNews(News news) {
        news.setModifiedDate(LocalDateTime.now());
        newsMapper.updateNews(news);
    }

    public void deleteNews(int id) {
        // 연관된 이미지를 먼저 삭제
        List<NewsImage> newsImages = newsImageMapper.findImagesByNewsId(id);
        for (NewsImage newsImage : newsImages) {
            newsImageMapper.deleteNewsImage(newsImage.getNewsId(), newsImage.getImageId());
        }
        // 뉴스 삭제
        newsMapper.deleteNews(id);
    }

    public List<News> getNewsByTypePaged(String type, int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return newsMapper.getNewsByTypePaged(type, pageSize, offset);
    }

    public List<NewsImageDetail> getAllNewsImageDetails() {
        return newsImageMapper.getAllNewsImageDetails();
    }

    public List<Images> getAllImages() {
        return imagesMapper.findAllImages();
    }

    public Images getImageById(int id) {
        return imagesMapper.findImageById(id);
    }

    public void increaseVisitCount(int id) { newsMapper.increaseVisitCount(id); }

    public void increaseLikeCount(int id) { newsMapper.increaseLikeCount(id); }


    //뉴스 상세화면 뉴스 id값 받아서
    public NewsDetailResponse getNewsDetail(int newsId) {
        return newsMapper.getNewsDetail(newsId);
    }
}