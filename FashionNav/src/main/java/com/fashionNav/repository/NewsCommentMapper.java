package com.fashionNav.repository;

import com.fashionNav.model.entity.NewsComment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * NewsCommentMapper 인터페이스는 뉴스 댓글 데이터를 관리하는 MyBatis 매퍼 인터페이스입니다.
 * 이 인터페이스는 뉴스 댓글 데이터를 조회, 삽입, 업데이트 및 삭제하는 메서드를 제공합니다.
 */
public interface NewsCommentMapper {

    @Select("SELECT * FROM NewsComments WHERE news_id = #{newsId} ORDER BY created_at DESC")
    List<NewsComment> findByNewsId(@Param("newsId") Long newsId);

    @Select("SELECT * FROM NewsComments WHERE comment_id = #{commentId}")
    NewsComment findById(@Param("commentId") Long commentId);

    @Insert("INSERT INTO NewsComments (news_id, user_id, content, created_at, updated_at) VALUES (#{newsId}, #{userId}, #{content}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    void insert(NewsComment newsComment);

    @Update("UPDATE NewsComments SET content = #{content}, updated_at = NOW() WHERE comment_id = #{commentId}")
    void update(NewsComment newsComment);

    @Delete("DELETE FROM NewsComments WHERE comment_id = #{commentId}")
    void delete(@Param("commentId") Long commentId);
}