package com.fashionNav.repository;

import com.fashionNav.model.entity.File;
import com.fashionNav.model.entity.Post;
import com.fashionNav.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("SELECT * FROM posts WHERE board_type = #{boardType} LIMIT #{size} OFFSET #{offset}")
    List<Post> findPostsByBoardTypeWithPagination(@Param("boardType") String boardType, @Param("size") int size, @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM posts WHERE board_type = #{boardType}")
    int countPostsByBoardType(String boardType);

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

    @Select("SELECT * FROM files WHERE post_id = #{postId}")
    List<File> findFilesByPostId(int postId);

    @Select("SELECT * FROM files WHERE file_id = #{fileId}")
    File findFileById(int fileId);

    @Insert("INSERT INTO files (post_id, file_name, file_path) VALUES (#{postId}, #{fileName}, #{filePath})")
    void insertFile(File file);

    @Update("UPDATE files SET file_name = #{fileName}, file_path = #{filePath} WHERE file_id = #{fileId}")
    void updateFile(@Param("fileId") int fileId, @Param("fileName") String fileName, @Param("filePath") String filePath);

    @Delete("DELETE FROM files WHERE file_id = #{fileId}")
    void deleteFile(int fileId);

    @Delete("DELETE FROM files WHERE post_id = #{postId}")
    void deleteFilesByPostId(int postId);
}