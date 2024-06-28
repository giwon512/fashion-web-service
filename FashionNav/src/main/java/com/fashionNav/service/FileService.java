package com.fashionNav.service;

import com.fashionNav.model.entity.File;
import com.fashionNav.repository.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileService 클래스는 파일 관련 기능을 제공하는 서비스 클래스입니다.
 * 이 클래스는 게시물 ID로 파일을 조회하고, 파일을 생성하며, 파일을 삭제하는 기능을 수행합니다.
 * 파일 관련 데이터 처리는 FileMapper 인터페이스를 통해 수행됩니다.
 */
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMapper fileMapper;

    public List<File> getFilesByPostId(int postId) {
        return fileMapper.findFilesByPostId(postId);
    }

    public void createFile(File file) {
        fileMapper.insertFile(file);
    }

    public void deleteFile(int fileId) {
        fileMapper.deleteFile(fileId);
    }
}
