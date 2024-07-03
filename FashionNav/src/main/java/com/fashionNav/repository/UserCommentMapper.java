package com.fashionNav.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fashionNav.model.dto.response.UserCommentResponse;
import com.fashionNav.model.entity.Post;
import com.fashionNav.model.entity.RawNews;
import com.fashionNav.model.entity.User;


@Mapper
public interface UserCommentMapper {

	// 유저 name 찾는 메서드
	@Select("SELECT name FROM user WHERE user_id = #{userId}")
	String findUserName( @Param("userId") Long userId);

	// 댓글 목록 보여주는 메서드
	@Select("SELECT 'Post' AS comment_type, c.comment_id, c.content, c.created_at, c.updated_at, p.title AS post_title, NULL AS news_title, p.post_id AS post_id, NULL AS news_id " +
	        "FROM comments c " +
	        "JOIN posts p ON c.post_id = p.post_id " +
	        "WHERE c.user_id = #{userId} " +
	        "UNION ALL " +
	        "SELECT 'News' AS comment_type, nc.comment_id, nc.content, nc.created_at, nc.updated_at, NULL AS post_title, rn.title AS news_title, NULL AS post_id, rn.news_id AS news_id " +
	        "FROM NewsComments nc " +
	        "JOIN Raw_News rn ON nc.news_id = rn.news_id " +
	        "WHERE nc.user_id = #{userId} " +
	        "ORDER BY created_at DESC " +
	        "LIMIT #{pageSize} OFFSET #{offset}")
	List<UserCommentResponse> findByUserComment(@Param("offset") int offset, @Param("userId") Long userId, @Param("pageSize") int pageSize);

	// 글 합계
	@Select("SELECT " +
	        "(SELECT COUNT(*) FROM comments WHERE user_id = #{userId}) + " +
	        "(SELECT COUNT(*) FROM NewsComments WHERE user_id = #{userId}) " +
	        "AS total_comments")
	int postNewsCountByUserId(@Param("userId") Long userId);

	@Select("SELECT * FROM USER WHERE user_id = #{userId}")
    User findUserById(@Param("userId") Long userId);
	
	
	@Select("SELECT * FROM posts WHERE post_id = #{postId}")
    Post findPostById(@Param("postId") int postId);
	
	
	@Select("SELECT * FROM posts WHERE parent_post_id = #{postId} ORDER BY created_at ASC")
    List<Post> findRepliesByPostId(@Param("postId") int postId);
	
	@Select("SELECT * from Raw_News where news_id = #{newsId}")
    RawNews findByDetailNews(@Param("newsId") Long newsId);
}
