package com.fashionNav.service;

import com.fashionNav.model.entity.NewsComment;
import com.fashionNav.repository.NewsCommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * NewsCommentService 클래스는 뉴스 댓글(NewsComment)과 관련된 비즈니스 로직을 처리합니다.
 * 이 클래스는 뉴스 댓글의 조회, 추가, 업데이트 및 삭제 기능을 제공합니다.
 * 뉴스 ID로 댓글을 조회하고, 특정 댓글을 ID로 조회하며, 댓글의 추가, 업데이트, 삭제를 수행합니다.
 * 댓글을 업데이트하거나 삭제할 때는 사용자의 권한을 확인합니다.
 **/
@Service
@RequiredArgsConstructor
public class NewsCommentService {

    private final NewsCommentMapper newsCommentMapper;

    public List<NewsComment> getCommentsByNewsId(Long newsId) {
        return newsCommentMapper.findByNewsId(newsId);
    }

    public NewsComment getCommentById(Long commentId) {
        return newsCommentMapper.findById(commentId);
    }

    public void addComment(NewsComment newsComment) {
        newsCommentMapper.insert(newsComment);
    }

    public void updateComment(NewsComment newsComment, Long userId) {
        NewsComment existingComment = newsCommentMapper.findById(newsComment.getCommentId());
        if (existingComment != null && existingComment.getUserId().equals(userId)) {
            newsCommentMapper.update(newsComment);
        } else {
            throw new IllegalArgumentException("You do not have permission to update this comment.");
        }
    }

    public void deleteComment(Long commentId, Long userId) {
        NewsComment existingComment = newsCommentMapper.findById(commentId);
        if (existingComment != null && existingComment.getUserId().equals(userId)) {
            newsCommentMapper.delete(commentId);
        } else {
            throw new IllegalArgumentException("You do not have permission to delete this comment.");
        }
    }
}