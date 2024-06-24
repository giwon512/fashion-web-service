package com.fashionNav.repository;

import com.fashionNav.model.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM files WHERE post_id = #{postId}")
    List<File> findFilesByPostId(int postId);

    @Select("SELECT * FROM files WHERE file_id = #{fileId}")
    File findFileById(int fileId);

    @Insert("INSERT INTO files (post_id, file_name, file_path) VALUES (#{postId}, #{fileName}, #{filePath})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insertFile(File file);

    @Update("UPDATE files SET file_name = #{fileName}, file_path = #{filePath} WHERE file_id = #{fileId}")
    void updateFile(@Param("fileId") int fileId, @Param("fileName") String fileName, @Param("filePath") String filePath);

    @Delete("DELETE FROM files WHERE file_id = #{fileId}")
    void deleteFile(int fileId);

    @Delete("DELETE FROM files WHERE post_id = #{postId}")
    void deleteFilesByPostId(int postId);
}