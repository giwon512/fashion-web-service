package com.fashionNav.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrawlingSite {
    private String siteAddress;  // 사이트 주소 (기본 키)
    private String siteName;     // 사이트 이름
    private LocalDateTime lastCrawled;    // 마지막 크롤링 시간
    private String crawlFrequency; // 크롤링 빈도
}