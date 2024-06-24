package com.fashionNav.controller;

import com.fashionNav.model.entity.Comment;
import com.fashionNav.model.entity.File;
import com.fashionNav.model.entity.Post;
import com.fashionNav.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPostsByBoardType(
            @RequestParam String boardType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching posts for board type: {} with page: {} and size: {}", boardType, page, size);
        Map<String, Object> response = postService.getPostsByBoardTypeWithPagination(boardType, page, size);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable int postId){
        return postService.getPostById(postId);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> createPost(
            @RequestPart("post") Post post,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Authentication authentication) {
        postService.createPost(post, file, authentication);
        return ResponseEntity.ok("Post created successfully");
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable int postId, @RequestBody Post post, Authentication authentication) {
        post.setPostId(postId);
        postService.updatePost(post, authentication);
        return ResponseEntity.ok("Post updated successfully");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId, Authentication authentication) {
        postService.deletePost(postId, authentication);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/{postId}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable int postId) {
        return postService.getCommentsByPostId(postId);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> createComment(@PathVariable int postId, @RequestBody Comment comment, Authentication authentication) {
        comment.setPostId(postId);
        postService.createComment(comment, authentication);
        return ResponseEntity.ok("Comment created successfully");
    }

    @PostMapping("/{postId}/comments/{parentCommentId}/replies")
    public ResponseEntity<String> createReply(@PathVariable int postId, @PathVariable int parentCommentId, @RequestBody Comment comment, Authentication authentication) {
        comment.setPostId(postId);
        comment.setParentCommentId(parentCommentId);
        postService.createComment(comment, authentication);
        return ResponseEntity.ok("Reply created successfully");
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable int postId, @PathVariable int commentId, @RequestBody Comment comment, Authentication authentication) {
        comment.setCommentId(commentId);
        postService.updateComment(comment, authentication);
        return ResponseEntity.ok("Comment updated successfully");
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable int postId, @PathVariable int commentId, Authentication authentication) {
        postService.deleteComment(commentId, authentication);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    @GetMapping("/{postId}/files")
    public List<File> getFilesByPostId(@PathVariable int postId) {
        return postService.getFilesByPostId(postId);
    }

    @PostMapping("/{postId}/files")
    public ResponseEntity<String> uploadFile(@PathVariable int postId, @RequestParam("file") MultipartFile file) {
        postService.createFile(postId, file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @PutMapping("/{postId}/files/{fileId}")
    public ResponseEntity<String> updateFile(@PathVariable int postId, @PathVariable int fileId, @RequestParam("file") MultipartFile file) {
        postService.updateFile(fileId, file);
        return ResponseEntity.ok("File updated successfully");
    }

    @DeleteMapping("/{postId}/files/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable int postId, @PathVariable int fileId) {
        postService.deleteFile(fileId);
        return ResponseEntity.ok("File deleted successfully");
    }
}