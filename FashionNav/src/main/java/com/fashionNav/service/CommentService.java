//package com.fashionNav.service;
//
//import com.fashionNav.model.entity.Comment;
//import com.fashionNav.repository.CommentMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * CommentService 클래스는 게시물에 대한 댓글을 관리하는 서비스 클래스입니다.
// * 이 클래스는 댓글을 생성, 조회, 업데이트 및 삭제하는 메서드를 제공합니다.
// */
//@Service
//@RequiredArgsConstructor
//public class CommentService {
//    private final CommentMapper commentMapper;
//
//    public List<Comment> getCommentsByPostId(int postId) {
//        return commentMapper.findCommentsByPostId(postId);
//    }
//
//    public void createComment(Comment comment) {
//        commentMapper.insertComment(comment);
//    }
//
//    public void updateComment(Comment comment) {
//        commentMapper.updateComment(comment);
//    }
//
//    public void deleteComment(int commentId) {
//        commentMapper.deleteComment(commentId);
//    }
//}
