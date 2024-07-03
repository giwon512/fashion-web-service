package com.fashionNav.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fashionNav.common.api.Api;
import com.fashionNav.model.entity.Post;
import com.fashionNav.model.entity.RawNews;
import com.fashionNav.model.entity.User;
import com.fashionNav.service.UserCommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Usercomment API", description = "유저 댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/comment")
public class UserCommentController {

  private final UserCommentService userCommentService;

  // 작성한 댓글 목록 보는 기능
  @GetMapping
  public Api<Map<String, Object>> getUserCommentList(
      @RequestParam(name = "sizeNum", defaultValue = "1") int sizeNum,
      @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
      Authentication authentication) {

    Long userId = ((User) authentication.getPrincipal()).getUserId();

    Map<String, Object> UserComment = userCommentService.getUserCommentList(sizeNum, pageSize, userId);

    return Api.OK(UserComment);
  }

  // 댓글 보러가는 기능 - 게시물
  @Operation(summary = "게시물 조회", description = "특정 ID를 가진 게시물을 조회합니다.")
  @GetMapping("/post/{postId}")
  public ResponseEntity<Post> getPostById(@PathVariable("postId") int postId) {
    Post post = userCommentService.getPostById(postId);
    return ResponseEntity.ok(post);
  }

  // 댓글 보러가는 기능 - 뉴스
  @Operation(summary = "뉴스 상세 조회", description = "특정 뉴스의 상세 정보를 조회합니다.")
  @GetMapping("/news/{newsId}")
  public ResponseEntity<RawNews> getDetailNews(@PathVariable("newsId") Long newsId) {
    return ResponseEntity.ok(userCommentService.getDetailNews(newsId));
  }

}