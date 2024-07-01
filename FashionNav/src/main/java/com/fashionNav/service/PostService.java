package com.fashionNav.service;

import com.fashionNav.model.entity.Comment;
import com.fashionNav.model.entity.File;
import com.fashionNav.model.entity.Post;
import com.fashionNav.model.entity.User;
import com.fashionNav.repository.CommentMapper;
import com.fashionNav.repository.FileMapper;
import com.fashionNav.repository.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * PostService 클래스는 게시물(Post) 및 관련 댓글(Comment), 파일(File)과 관련된 비즈니스 로직을 처리합니다.
 * 게시물의 생성, 조회, 수정, 삭제 기능과 함께 댓글 및 파일 관리 기능을 제공합니다.
 * 사용자 인증(Authentication)을 통해 사용자 권한을 확인하고, 게시물과 댓글의 소유자를 확인합니다.
 */
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final FileMapper fileMapper;

    private final Path rootLocation = Paths.get("upload-dir");

    public Map<String, Object> getPostsByBoardTypeWithPagination(String boardType, int page, int size) {
        int offset = (page - 1) * size;
        List<Post> posts = postMapper.findAllPostsByBoardTypeWithPagination(boardType, size, offset);
        posts.forEach(post -> {
            User user = postMapper.findUserById(post.getUserId());
            post.setUserName(user.getName());
            if (post.getParentPostId() == 0) {
                List<Post> replies = postMapper.findRepliesByPostId(post.getPostId());
                post.setReplies(replies);
            }
        });

        int totalPosts = postMapper.countPostsByBoardType(boardType);
        int totalPages = (int) Math.ceil((double) totalPosts / size);

        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts);
        response.put("totalPages", totalPages);
        return response;
    }

    public File getFileById(int fileId) {
        return fileMapper.findFileById(fileId);
    }

    public Post getPostById(int postId) {
        Post post = postMapper.findPostById(postId);
        User user = postMapper.findUserById(post.getUserId());
        post.setUserName(user.getName());
        List<Post> replies = postMapper.findRepliesByPostId(postId);
        post.setReplies(replies);
        return post;
    }

    public void createPost(Post post, MultipartFile file, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getUserId();  // 현재 로그인된 사용자 ID 설정
        post.setUserId(userId);
        postMapper.insertPost(post);
        if (file != null) {
            saveFile(post.getPostId(), file);
        }
    }

    public void updatePost(Post post, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        // 현재 사용자만 게시물을 수정할 수 있도록 제한
        if (postMapper.findPostById(post.getPostId()).getUserId() != userId) {
            throw new IllegalArgumentException("You do not have permission to edit this post");
        }
        postMapper.updatePost(post);
    }

    public void deletePost(int postId, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        // 현재 사용자만 게시물을 삭제할 수 있도록 제한
        if (postMapper.findPostById(postId).getUserId() != userId) {
            throw new IllegalArgumentException("You do not have permission to delete this post");
        }
        // 게시물 삭제 전에 관련된 댓글과 파일을 먼저 삭제
        commentMapper.deleteCommentsByPostId(postId);
        fileMapper.deleteFilesByPostId(postId);
        postMapper.deletePost(postId);
    }

    public List<Comment> getCommentsByPostId(int postId) {
        List<Comment> comments = commentMapper.findCommentsByPostId(postId);
        comments.forEach(comment -> {
            User user = commentMapper.findUserById(comment.getUserId());
            comment.setUserName(user.getName());
            List<Comment> replies = commentMapper.findRepliesByCommentId(comment.getCommentId());
            comment.setReplies(replies);
        });
        return comments;
    }

    public void createComment(Comment comment, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getUserId();  // 현재 로그인된 사용자 ID 설정
        comment.setUserId(userId);
        commentMapper.insertComment(comment);
    }

    public void updateComment(Comment comment, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        // 현재 사용자만 댓글을 수정할 수 있도록 제한
        if (commentMapper.findCommentById(comment.getCommentId()).getUserId() != userId) {
            throw new IllegalArgumentException("You do not have permission to edit this comment");
        }
        commentMapper.updateComment(comment);
    }
    public void deleteComment(int commentId, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        Comment comment = commentMapper.findCommentById(commentId);
        // 현재 사용자만 댓글을 삭제할 수 있도록 제한
        if (comment.getUserId() != userId) {
            throw new IllegalArgumentException("You do not have permission to delete this comment");
        }
        // 대댓글도 삭제
        commentMapper.deleteRepliesByCommentId(commentId);
        commentMapper.deleteComment(commentId);
    }

    public List<File> getFilesByPostId(int postId) {
        return fileMapper.findFilesByPostId(postId);
    }

    public void createFile(int postId, MultipartFile file, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        Post post = postMapper.findPostById(postId);
        if (post.getUserId() != userId) {
            throw new IllegalArgumentException("You do not have permission to upload files for this post");
        }
        saveFile(postId, file);
    }

    public void updateFile(int fileId, MultipartFile file, Authentication authentication) {
        File existingFile = fileMapper.findFileById(fileId);
        Post post = postMapper.findPostById(existingFile.getPostId());
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        if (post.getUserId() != userId) {
            throw new IllegalArgumentException("You do not have permission to update files for this post");
        }
        saveFile(existingFile.getPostId(), file);
        fileMapper.updateFile(fileId, file.getOriginalFilename(), existingFile.getFilePath());
    }

    public void deleteFile(int fileId, Authentication authentication) {
        File file = fileMapper.findFileById(fileId);
        Post post = postMapper.findPostById(file.getPostId());
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        if (post.getUserId() != userId) {
            throw new IllegalArgumentException("You do not have permission to delete files for this post");
        }
        fileMapper.deleteFile(fileId);
    }

    private void saveFile(int postId, MultipartFile file) {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            String filename = file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(
                            Paths.get(filename))
                    .normalize().toAbsolutePath();
            file.transferTo(destinationFile);

            File newFile = new File();
            newFile.setPostId(postId);
            newFile.setFileName(filename);
            newFile.setFilePath(destinationFile.toString());
            fileMapper.insertFile(newFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public List<Post> getRepliesByPostId(int postId) {
        return postMapper.findRepliesByPostId(postId);
    }
}