package com.fashionNav.controller;

import com.fashionNav.model.entity.Comment;
import com.fashionNav.model.entity.File;
import com.fashionNav.model.entity.Post;
import com.fashionNav.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * PostController 클래스는 게시물과 관련된 API를 제공합니다.
 * 이 클래스는 게시물의 생성, 조회, 수정, 삭제 및 파일, 댓글과 관련된 엔드포인트를 정의합니다.
 * 인증된 사용자는 게시물을 생성, 수정 및 삭제할 수 있습니다.
 * 특정 게시물에 대한 파일과 댓글도 관리할 수 있습니다.
 */
@Slf4j
@Tag(name = "Post API", description = "게시물 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시물 목록 조회", description = "게시판 유형에 따라 게시물 목록을 페이지네이션하여 조회합니다.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPostsByBoardType(
            @RequestParam("boardType") String boardType,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Map<String, Object> response = postService.getPostsByBoardTypeWithPagination(boardType, page, size);
        log.info("페이징 호출");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시물 조회", description = "특정 ID를 가진 게시물을 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable("postId") int postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "게시물 댓글 조회", description = "특정 게시물에 대한 댓글 목록을 조회합니다.")
    @GetMapping("/{postId}/replies")
    public ResponseEntity<List<Post>> getRepliesByPostId(@PathVariable("postId") int postId) {
        List<Post> replies = postService.getRepliesByPostId(postId);
        return ResponseEntity.ok(replies);
    }

    @Operation(summary = "게시물 생성", description = "새로운 게시물을 생성합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> createPost(
            @RequestPart("post") Post post,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Authentication authentication) {
        postService.createPost(post, file, authentication);
        return ResponseEntity.ok("Post created successfully");
    }


    @Operation(summary = "댓글 생성", description = "특정 게시물에 대한 댓글을 생성합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/{postId}/replies")
    public ResponseEntity<String> createReply(
            @PathVariable("postId") int postId,
            @RequestBody Post post,
            Authentication authentication) {
        post.setParentPostId(postId);
        postService.createPost(post, null, authentication);
        return ResponseEntity.ok("Reply created successfully");
    }

    @Operation(summary = "게시물 수정", description = "특정 게시물을 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(
            @PathVariable("postId") int postId,
            @RequestBody Post post,
            Authentication authentication) {
        post.setPostId(postId);
        postService.updatePost(post, authentication);
        return ResponseEntity.ok("Post updated successfully");
    }

    @Operation(summary = "게시물 삭제", description = "특정 게시물을 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") int postId, Authentication authentication) {
        postService.deletePost(postId, authentication);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @Operation(summary = "댓글 목록 조회", description = "특정 게시물에 대한 댓글 목록을 조회합니다.")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable("postId") int postId) {
        List<Comment> comments = postService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "댓글 생성", description = "특정 게시물에 댓글을 생성합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> createComment(@PathVariable("postId") int postId, @RequestBody Comment comment, Authentication authentication) {
        comment.setPostId(postId);
        postService.createComment(comment, authentication);
        return ResponseEntity.ok("Comment created successfully");
    }

    @Operation(summary = "댓글 수정", description = "특정 게시물에 대한 댓글을 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId, @RequestBody Comment comment, Authentication authentication) {
        comment.setCommentId(commentId);
        postService.updateComment(comment, authentication);
        return ResponseEntity.ok("Comment updated successfully");
    }

    @Operation(summary = "댓글 삭제", description = "특정 게시물에 대한 댓글을 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId, Authentication authentication) {
        postService.deleteComment(commentId, authentication);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    @Operation(summary = "파일 목록 조회", description = "특정 게시물에 대한 파일 목록을 조회합니다.")
    @GetMapping("/{postId}/files")
    public ResponseEntity<List<File>> getFilesByPostId(@PathVariable("postId") int postId) {
        List<File> files = postService.getFilesByPostId(postId);
        return ResponseEntity.ok(files);
    }

    @Operation(summary = "파일 업로드", description = "특정 게시물에 파일을 업로드합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/{postId}/files")
    public ResponseEntity<String> uploadFile(@PathVariable("postId") int postId, @RequestParam("file") MultipartFile file, Authentication authentication) {
        postService.createFile(postId, file, authentication);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @Operation(summary = "파일 수정", description = "특정 게시물에 대한 파일을 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{postId}/files/{fileId}")
    public ResponseEntity<String> updateFile(@PathVariable("postId") int postId, @PathVariable("fileId") int fileId, @RequestParam("file") MultipartFile file, Authentication authentication) {
        postService.updateFile(fileId, file, authentication);
        return ResponseEntity.ok("File updated successfully");
    }

    @Operation(summary = "파일 삭제", description = "특정 게시물에 대한 파일을 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{postId}/files/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable("postId") int postId, @PathVariable("fileId") int fileId, Authentication authentication) {
        postService.deleteFile(fileId, authentication);
        return ResponseEntity.ok("File deleted successfully");
    }

    @Operation(summary = "파일 다운로드", description = "특정 파일을 다운로드합니다.")
    @GetMapping("/files/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") int fileId) {
        File file = postService.getFileById(fileId);
        Path path = Paths.get(file.getFilePath());
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + file.getFilePath());
            }
        } catch (Exception e) {
            throw new RuntimeException("File not found: " + file.getFilePath(), e);
        }

        String encodedFileName = UriUtils.encode(file.getFileName(), "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .body(resource);
    }
}
