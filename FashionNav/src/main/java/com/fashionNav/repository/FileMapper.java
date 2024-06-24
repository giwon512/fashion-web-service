package com.fashionNav.repository;

import com.fashionNav.model.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM files WHERE post_id = #{postId}")
    List<File> findFilesByPostId(int postId);

    @Insert("INSERT INTO files (post_id, file_name, file_path) VALUES (#{postId}, #{fileName}, #{filePath})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insertFile(File file);

    @Delete("DELETE FROM files WHERE file_id = #{fileId}")
    void deleteFile(int fileId);
}
