package com.fashionNav.controller;

import com.fashionNav.model.entity.NewsComment;
import com.fashionNav.model.entity.User;
import com.fashionNav.service.NewsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news-comments")
@RequiredArgsConstructor
public class NewsCommentController {

    private final NewsCommentService newsCommentService;

    @GetMapping("/news/{newsId}")
    public ResponseEntity<List<NewsComment>> getCommentsByNewsId(@PathVariable("newsId") Long newsId) {
        return ResponseEntity.ok(newsCommentService.getCommentsByNewsId(newsId));
    }

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody NewsComment newsComment, Authentication authentication) {
        User user = (User) authentication.getPrincipal();  // 현재 인증된 사용자 정보 가져오기
        newsComment.setUserId(user.getUserId());  // user_id 설정
        newsCommentService.addComment(newsComment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable("commentId") Long commentId, @RequestBody NewsComment newsComment, Authentication authentication) {
        User user = (User) authentication.getPrincipal();  // 현재 인증된 사용자 정보 가져오기
        newsComment.setCommentId(commentId);
        newsCommentService.updateComment(newsComment, user.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();  // 현재 인증된 사용자 정보 가져오기
        newsCommentService.deleteComment(commentId, user.getUserId());
        return ResponseEntity.ok().build();
    }
}