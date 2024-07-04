package com.fashionNav.repository;

import com.fashionNav.model.entity.Comment;
import com.fashionNav.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * CommentMapper 인터페이스는 댓글 데이터를 관리하는 MyBatis 매퍼 인터페이스입니다.
 * 이 인터페이스는 댓글 데이터를 조회, 삽입, 업데이트 및 삭제하는 메서드를 제공합니다.
 */
@Mapper
public interface CommentMapper {

    @Select("SELECT * FROM comments WHERE post_id = #{postId} AND parent_comment_id IS NULL")
    List<Comment> findCommentsByPostId(@Param("postId") int postId);

    @Select("SELECT * FROM comments WHERE parent_comment_id = #{commentId}")
    List<Comment> findRepliesByCommentId(@Param("commentId") int commentId);

    @Select("SELECT * FROM comments WHERE comment_id = #{commentId}")
    Comment findCommentById(@Param("commentId") int commentId);

    @Select("SELECT * FROM USER WHERE user_id = #{userId}")
    User findUserById(@Param("userId") Long userId);

    @Insert("INSERT INTO comments (post_id, user_id, content, created_at, updated_at, parent_comment_id) VALUES (#{postId}, #{userId}, #{content}, now(), now(), #{parentCommentId})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    void insertComment(Comment comment);

    @Update("UPDATE comments SET content = #{content}, updated_at = #{updatedAt} WHERE comment_id = #{commentId}")
    void updateComment(Comment comment);

    @Delete("DELETE FROM comments WHERE comment_id = #{commentId}")
    void deleteComment(@Param("commentId") int commentId);

    @Delete("DELETE FROM comments WHERE parent_comment_id = #{commentId}")
    void deleteRepliesByCommentId(@Param("commentId") int commentId);

    @Delete("DELETE FROM comments WHERE post_id = #{postId}")
    void deleteCommentsByPostId(@Param("postId") int postId);
}
