-- ADMIN 테이블 생성 (관리자 정보)
CREATE TABLE ADMIN (
                       admin_id VARCHAR(50) PRIMARY KEY,  -- 관리자 ID (기본 키)
                       admin_pw VARCHAR(255) NOT NULL     -- 관리자 비밀번호
);

-- USER 테이블 생성 (사용자 정보)
CREATE TABLE USER (
                      user_id INT AUTO_INCREMENT PRIMARY KEY,           -- 사용자 ID (자동 증가, 기본 키)
                      password VARCHAR(50) NOT NULL,                    -- 사용자 비밀번호
                      name VARCHAR(50) NOT NULL,                        -- 사용자 이름
                      email VARCHAR(100) UNIQUE NOT NULL,               -- 사용자 이메일 (유일, 필수)
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,    -- 사용자 등록 날짜 (기본값: 현재 날짜와 시간)
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 사용자 수정 날짜 (기본값: 현재 날짜와 시간, 업데이트 시 자동 변경)
);

-- CATEGORY 테이블 생성 (카테고리 정보)
CREATE TABLE CATEGORY (
                          style VARCHAR(50) PRIMARY KEY                     -- 스타일 이름 (기본 키)
);

-- ITEM 테이블 생성 (상품 정보)
CREATE TABLE ITEM (
                      item_id INT AUTO_INCREMENT PRIMARY KEY,           -- 상품 ID (자동 증가, 기본 키)
                      name VARCHAR(100) NOT NULL,                       -- 상품 이름
                      description VARCHAR(500),                         -- 상품 설명
                      price DECIMAL(10, 2),                             -- 상품 가격
                      brand VARCHAR(50),                                -- 상품 브랜드
                      style VARCHAR(50),                                -- 스타일 (카테고리와 연관)
                      FOREIGN KEY (style) REFERENCES CATEGORY(style)    -- 외래 키 설정
);

-- ITEM_CATEGORY 테이블 생성 (상품-카테고리 관계)
CREATE TABLE ITEM_CATEGORY (
                               item_id INT,                                      -- 상품 ID (외래 키)
                               style VARCHAR(50),                                -- 스타일 (카테고리와 연관)
                               PRIMARY KEY (item_id, style),                     -- 복합 기본 키 (상품 ID, 스타일)
                               FOREIGN KEY (item_id) REFERENCES ITEM(item_id),   -- 외래 키 설정
                               FOREIGN KEY (style) REFERENCES CATEGORY(style)    -- 외래 키 설정
);

-- NEWS 테이블 생성 (뉴스 정보)
CREATE TABLE NEWS (
                      news_id INT AUTO_INCREMENT PRIMARY KEY,           -- 뉴스 ID (자동 증가, 기본 키)
                      title VARCHAR(200) NOT NULL,                      -- 뉴스 제목
                      content TEXT,                                     -- 뉴스 내용
                      type VARCHAR(50),                                 -- 뉴스 유형
                      source VARCHAR(100),                              -- 뉴스 출처
                      author VARCHAR(50),                               -- 뉴스 작성자
                      published_date DATE,                              -- 뉴스 발행일
                      modified_date DATE,                               -- 뉴스 수정일
                      visit_count INT DEFAULT 0,                        -- 뉴스 조회수 (기본값: 0)
                      like_count INT DEFAULT 0,                         -- 뉴스 좋아요 수 (기본값: 0)
                      style VARCHAR(50),                                -- 뉴스 관련 스타일 (카테고리와 연관)
                      FOREIGN KEY (type) REFERENCES NEWS_TYPE(type),    -- 외래 키 설정
                      FOREIGN KEY (style) REFERENCES CATEGORY(style)    -- 외래 키 설정
);

-- NEWS_TYPE 테이블 생성 (뉴스 유형 정보)
CREATE TABLE NEWS_TYPE (
                           type VARCHAR(50) PRIMARY KEY,                     -- 뉴스 유형 (기본 키)
                           type_description VARCHAR(200)                     -- 뉴스 유형 설명
);

-- IMAGES 테이블 생성 (이미지 정보)
CREATE TABLE IMAGES (
                        image_id INT AUTO_INCREMENT PRIMARY KEY,          -- 이미지 ID (자동 증가, 기본 키)
                        url VARCHAR(500) NOT NULL,                        -- 이미지 URL
                        alt_text VARCHAR(200),                            -- 이미지 대체 텍스트
                        caption VARCHAR(500)                              -- 이미지 캡션
);

-- ITEM_IMAGE 테이블 생성 (상품-이미지 관계)
CREATE TABLE ITEM_IMAGE (
                            item_id INT,                                      -- 상품 ID (외래 키)
                            image_id INT,                                     -- 이미지 ID (외래 키)
                            is_main BOOLEAN DEFAULT FALSE,                    -- 메인 이미지 여부 (기본값: FALSE)
                            PRIMARY KEY (item_id, image_id),                  -- 복합 기본 키 (상품 ID, 이미지 ID)
                            FOREIGN KEY (item_id) REFERENCES ITEM(item_id),   -- 외래 키 설정
                            FOREIGN KEY (image_id) REFERENCES IMAGES(image_id)-- 외래 키 설정
);

-- NEWS_IMAGE 테이블 생성 (뉴스-이미지 관계)
CREATE TABLE NEWS_IMAGE (
                            news_id INT,                                      -- 뉴스 ID (외래 키)
                            image_id INT,                                     -- 이미지 ID (외래 키)
                            is_main BOOLEAN DEFAULT FALSE,                    -- 메인 이미지 여부 (기본값: FALSE)
                            PRIMARY KEY (news_id, image_id),                  -- 복합 기본 키 (뉴스 ID, 이미지 ID)
                            FOREIGN KEY (news_id) REFERENCES NEWS(news_id),   -- 외래 키 설정
                            FOREIGN KEY (image_id) REFERENCES IMAGES(image_id)-- 외래 키 설정
);

-- CONTENT_TYPE 테이블 생성 (콘텐츠 유형 정보)
CREATE TABLE CONTENT_TYPE (
                              content_type VARCHAR(50) PRIMARY KEY,             -- 콘텐츠 유형 (기본 키, 예: 'news', 'item')
                              description VARCHAR(200)                          -- 콘텐츠 유형 설명
);

-- PAGE 테이블 생성 (페이지 정보)
CREATE TABLE PAGE (
                      page_id INT AUTO_INCREMENT PRIMARY KEY,           -- 페이지 ID (자동 증가, 기본 키)
                      url VARCHAR(200) NOT NULL,                        -- 페이지 URL
                      title VARCHAR(100),                               -- 페이지 제목
                      description VARCHAR(500),                         -- 페이지 설명
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,    -- 페이지 생성 날짜 (기본값: 현재 날짜와 시간)
                      content_type VARCHAR(50) NOT NULL,                -- 콘텐츠 유형 ('news', 'item' 등)
                      FOREIGN KEY (content_type) REFERENCES CONTENT_TYPE(content_type) -- 콘텐츠 유형 외래 키 설정
);

-- USER_PAGE 테이블 생성 (사용자가 저장한 페이지)
CREATE TABLE USER_PAGE (
                           user_id INT,                                      -- 사용자 ID (외래 키)
                           page_id INT,                                      -- 페이지 ID (외래 키)
                           saved_at DATETIME DEFAULT CURRENT_TIMESTAMP,      -- 페이지 저장 날짜 (기본값: 현재 날짜와 시간)
                           PRIMARY KEY (user_id, page_id),                   -- 복합 기본 키 (사용자 ID, 페이지 ID)
                           FOREIGN KEY (user_id) REFERENCES USER(user_id),   -- 사용자 ID 외래 키 설정
                           FOREIGN KEY (page_id) REFERENCES PAGE(page_id)    -- 페이지 ID 외래 키 설정
);

-- CRAWLING_SITE 테이블 생성 (크롤링 사이트 정보)
CREATE TABLE CRAWLING_SITE (
                               site_address VARCHAR(200) PRIMARY KEY,            -- 사이트 주소 (기본 키)
                               site_name VARCHAR(100),                           -- 사이트 이름
                               last_crawled DATETIME,                            -- 마지막 크롤링 시간
                               crawl_frequency VARCHAR(20)                       -- 크롤링 빈도
);

-- SURVEY 테이블 생성 (설문조사 정보)
CREATE TABLE SURVEY (
                        survey_id INT AUTO_INCREMENT PRIMARY KEY,         -- 설문조사 ID (자동 증가, 기본 키)
                        title VARCHAR(100) NOT NULL,                      -- 설문조사 제목
                        description VARCHAR(500),                         -- 설문조사 설명
                        created_at DATETIME DEFAULT CURRENT_TIMESTAMP     -- 설문조사 생성 날짜 (기본값: 현재 날짜와 시간)
);

-- SURVEY_QUESTION 테이블 생성 (설문조사 질문)
CREATE TABLE SURVEY_QUESTION (
                                 question_id INT AUTO_INCREMENT PRIMARY KEY,       -- 질문 ID (자동 증가, 기본 키)
                                 survey_id INT,                                    -- 설문조사 ID (외래 키)
                                 question_text VARCHAR(500) NOT NULL,              -- 질문 텍스트
                                 FOREIGN KEY (survey_id) REFERENCES SURVEY(survey_id) -- 외래 키 설정
);

-- SURVEY_OPTION 테이블 생성 (설문조사 옵션)
CREATE TABLE SURVEY_OPTION (
                               option_id INT AUTO_INCREMENT PRIMARY KEY,         -- 옵션 ID (자동 증가, 기본 키)
                               question_id INT,                                  -- 질문 ID (외래 키)
                               option_text VARCHAR(500) NOT NULL,                -- 옵션 텍스트
                               FOREIGN KEY (question_id) REFERENCES SURVEY_QUESTION(question_id) -- 외래 키 설정
);

-- USER_SURVEY_RESPONSE 테이블 생성 (사용자 설문조사 응답)
CREATE TABLE USER_SURVEY_RESPONSE (
                                      user_id INT,                                      -- 사용자 ID (외래 키)
                                      question_id INT,                                  -- 질문 ID (외래 키)
                                      option_id INT,                                    -- 옵션 ID (외래 키)
                                      response_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- 응답 날짜 (기본값: 현재 날짜와 시간)
                                      PRIMARY KEY (user_id, question_id, option_id),    -- 복합 기본 키 (사용자 ID, 질문 ID, 옵션 ID)
                                      FOREIGN KEY (user_id) REFERENCES USER(user_id),   -- 사용자 ID 외래 키 설정
                                      FOREIGN KEY (question_id) REFERENCES SURVEY_QUESTION(question_id), -- 외래 키 설정
                                      FOREIGN KEY (option_id) REFERENCES SURVEY_OPTION(option_id) -- 외래 키 설정
);
