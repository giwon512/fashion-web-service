package com.fashionNav.repository;

import com.fashionNav.model.entity.Comment;
import com.fashionNav.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("SELECT * FROM comments WHERE post_id = #{postId}")
    List<Comment> findCommentsByPostId(@Param("postId") int postId);

    @Select("SELECT * FROM comments WHERE comment_id = #{commentId}")
    Comment findCommentById(@Param("commentId") int commentId);

    @Insert("INSERT INTO comments (post_id, user_id, content) VALUES (#{postId}, #{userId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    void insertComment(Comment comment);

    @Update("UPDATE comments SET content = #{content} WHERE comment_id = #{commentId}")
    void updateComment(Comment comment);

    @Delete("DELETE FROM comments WHERE comment_id = #{commentId}")
    void deleteComment(@Param("commentId") int commentId);

    @Delete("DELETE FROM comments WHERE post_id = #{postId}")
    void deleteCommentsByPostId(@Param("postId") int postId);

    @Select("SELECT * FROM USER WHERE user_id = #{userId}")
    User findUserById(int userId);
}
