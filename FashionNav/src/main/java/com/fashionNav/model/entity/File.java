package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class File {
    private int fileId;
    private int postId;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;


}
