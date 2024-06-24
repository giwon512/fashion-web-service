package com.fashionNav.service;

import com.fashionNav.model.entity.Comment;
import com.fashionNav.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    public List<Comment> getCommentsByPostId(int postId) {
        return commentMapper.findCommentsByPostId(postId);
    }

    public void createComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    public void updateComment(Comment comment) {
        commentMapper.updateComment(comment);
    }

    public void deleteComment(int commentId) {
        commentMapper.deleteComment(commentId);
    }
}
