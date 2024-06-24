package com.fashionNav.service;

import com.fashionNav.model.entity.File;
import com.fashionNav.repository.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
