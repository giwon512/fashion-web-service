package com.fashionNav.repository;

import com.fashionNav.model.entity.Post;
import com.fashionNav.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("SELECT * FROM posts WHERE board_type = #{boardType}")
    List<Post> findPostsByBoardType(String boardType);

    @Select("SELECT * FROM posts WHERE post_id = #{postId}")
    Post findPostById(int postId);

    @Insert("INSERT INTO posts (board_type, user_id, title, content, created_at, updated_at) VALUES (#{boardType}, #{userId}, #{title}, #{content}, now(), #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    void insertPost(Post post);

    @Update("UPDATE posts SET board_type = #{boardType}, user_id = #{userId}, title = #{title}, content = #{content}, updated_at = #{updatedAt} WHERE post_id = #{postId}")
    void updatePost(Post post);

    @Delete("DELETE FROM posts WHERE post_id = #{postId}")
    void deletePost(int postId);

    @Select("SELECT * FROM USER WHERE user_id = #{userId}")
    User findUserById(int userId);
}
