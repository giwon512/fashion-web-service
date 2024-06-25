package com.fashionNav.repository;

import com.fashionNav.model.entity.Post;
import com.fashionNav.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("SELECT * FROM posts WHERE board_type = #{boardType} ORDER BY created_at DESC LIMIT #{size} OFFSET #{offset}")
    List<Post> findAllPostsByBoardTypeWithPagination(@Param("boardType") String boardType, @Param("size") int size, @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM posts WHERE board_type = #{boardType}")
    int countPostsByBoardType(String boardType);

    @Select("SELECT * FROM posts WHERE post_id = #{postId}")
    Post findPostById(int postId);

    @Insert("INSERT INTO posts (board_type, user_id, title, content, created_at, updated_at, parent_post_id) VALUES (#{boardType}, #{userId}, #{title}, #{content}, now(), now(), #{parentPostId})")
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    void insertPost(Post post);

    @Update("UPDATE posts SET board_type = #{boardType}, user_id = #{userId}, title = #{title}, content = #{content}, updated_at = now() WHERE post_id = #{postId}")
    void updatePost(Post post);

    @Delete("DELETE FROM posts WHERE post_id = #{postId}")
    void deletePost(int postId);

    @Select("SELECT * FROM USER WHERE user_id = #{userId}")
    User findUserById(int userId);

    @Select("SELECT * FROM posts WHERE parent_post_id = #{postId} ORDER BY created_at ASC")
    List<Post> findRepliesByPostId(int postId);

    @Select("WITH RECURSIVE PostCTE AS ( " +
            "    SELECT *, 0 AS level " +
            "    FROM posts " +
            "    WHERE post_id = #{postId} " +
            "    UNION ALL " +
            "    SELECT p.*, cte.level + 1 AS level " +
            "    FROM posts p " +
            "    INNER JOIN PostCTE cte ON p.parent_post_id = cte.post_id " +
            ") " +
            "SELECT * FROM PostCTE ORDER BY created_at ASC")
    List<Post> findAllRepliesByPostId(int postId);
}