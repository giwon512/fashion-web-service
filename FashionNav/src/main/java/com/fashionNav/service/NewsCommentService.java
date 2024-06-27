package com.fashionNav.service;

import com.fashionNav.model.entity.NewsComment;
import com.fashionNav.repository.NewsCommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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