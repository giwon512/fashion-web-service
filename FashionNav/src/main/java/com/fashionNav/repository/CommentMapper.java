package com.fashionNav.repository;

import com.fashionNav.model.entity.Comment;
import com.fashionNav.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("SELECT * FROM comments WHERE post_id = #{postId} AND parent_comment_id IS NULL")
    List<Comment> findCommentsByPostId(int postId);

    @Select("SELECT * FROM comments WHERE parent_comment_id = #{commentId}")
    List<Comment> findRepliesByCommentId(int commentId);

    @Select("SELECT * FROM comments WHERE comment_id = #{commentId}")
    Comment findCommentById(int commentId);

    @Select("SELECT * FROM USER WHERE user_id = #{userId}")
    User findUserById(Long userId);

    @Insert("INSERT INTO comments (post_id, user_id, content, created_at, updated_at, parent_comment_id) VALUES (#{postId}, #{userId}, #{content}, now(), now(), #{parentCommentId})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    void insertComment(Comment comment);

    @Update("UPDATE comments SET content = #{content}, updated_at = #{updatedAt} WHERE comment_id = #{commentId}")
    void updateComment(Comment comment);

    @Delete("DELETE FROM comments WHERE comment_id = #{commentId}")
    void deleteComment(int commentId);

    @Delete("DELETE FROM comments WHERE parent_comment_id = #{commentId}")
    void deleteRepliesByCommentId(int commentId);

    @Delete("DELETE FROM comments WHERE post_id = #{postId}")
    void deleteCommentsByPostId(int postId);
}