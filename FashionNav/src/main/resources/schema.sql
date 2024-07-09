-- create database fashiondb;

-- CREATE USER 'fashiondbuser'@'localhost' IDENTIFIED BY 'fashiondbuser';
-- CREATE USER 'fashiondbuser'@'%' IDENTIFIED BY 'fashiondbuser';

-- GRANT ALL PRIVILEGES ON fashiondb.* TO 'fashiondbuser'@'localhost';
-- GRANT ALL PRIVILEGES ON fashiondb.* TO 'fashiondbuser'@'%';


create table Banner
(
    banner_id    bigint auto_increment
        primary key,
    title        varchar(255) null,
    url          varchar(255) null,
    image_url    mediumblob null,
    created_date timestamp    null,
    description  varchar(255) null
);

create table Brand
(
    brand_id bigint auto_increment
        primary key,
    name     varchar(255) not null
);

create table ProcessedNews
(
    news_id        bigint auto_increment
        primary key,
    title          varchar(255)                      not null,
    content        text                              not null,
    subtitle       varchar(255)                      null,
    image_url      varchar(255)                      null,
    source         varchar(255)                      null,
    author         varchar(255)                      null,
    published_date timestamp                         null,
    category       varchar(255)                      null,
    gender         enum ('남', '여')                   null,
    age_group      enum ('10대', '20대', '30대', '40대') null,
    style          varchar(255)                      null,
    brand          varchar(255)                      null
);

create table Raw_News
(
    news_id        bigint auto_increment
        primary key,
    title          varchar(255) null,
    subtitle       varchar(255) null,
    content        longtext     null,
    image_url      varchar(255) null,
    source         varchar(255) null,
    author         varchar(255) null,
    published_date timestamp    null,
    category       varchar(255) null
);

create table Style
(
    style_id bigint auto_increment
        primary key,
    name     varchar(255) not null
);

create table USER
(
    user_id     bigint auto_increment
        primary key,
    password    varchar(255)                             not null,
    name        varchar(50)                              not null,
    email       varchar(100)                             not null,
    created_at  datetime     default current_timestamp() null,
    updated_at  datetime                                 null,
    role        varchar(255) default 'ROLE_USER'         not null,
    birthdate   date                                     null,
    gender      enum ('male', 'female')                  null,
    phoneNumber varchar(20)                              null,
    constraint email
        unique (email)
);

create table NewsComments
(
    comment_id bigint auto_increment
        primary key,
    news_id    bigint                                not null,
    user_id    bigint                                not null,
    content    text                                  not null,
    created_at timestamp default current_timestamp() null,
    updated_at timestamp default current_timestamp() null on update current_timestamp(),
    constraint fk_news_comments_news
        foreign key (news_id) references Raw_News (news_id),
    constraint fk_news_comments_user
        foreign key (user_id) references USER (user_id)
);

create table UserSavedPage
(
    saved_page_id bigint auto_increment
        primary key,
    user_id       bigint                                not null,
    news_id       bigint                                not null,
    saved_date    timestamp default current_timestamp() null,
    constraint UserSavedPage_ibfk_1
        foreign key (user_id) references USER (user_id),
    constraint UserSavedPage_ibfk_2
        foreign key (news_id) references Raw_News (news_id)
);

create index news_id
    on UserSavedPage (news_id);

create index user_id
    on UserSavedPage (user_id);

create table UserSurvey
(
    survey_id bigint auto_increment
        primary key,
    user_id   bigint                            not null,
    gender    enum ('남', '여')                   not null,
    age_group enum ('10대', '20대', '30대', '40대') not null,
    constraint UserSurvey_ibfk_1
        foreign key (user_id) references USER (user_id)
);

create index user_id
    on UserSurvey (user_id);

create table UserSurveyBrand
(
    survey_id bigint not null,
    brand_id  bigint not null,
    primary key (survey_id, brand_id),
    constraint UserSurveyBrand_ibfk_1
        foreign key (survey_id) references UserSurvey (survey_id),
    constraint UserSurveyBrand_ibfk_2
        foreign key (brand_id) references Brand (brand_id)
);

create index brand_id
    on UserSurveyBrand (brand_id);

create table UserSurveyStyle
(
    survey_id bigint not null,
    style_id  bigint not null,
    primary key (survey_id, style_id),
    constraint UserSurveyStyle_ibfk_1
        foreign key (survey_id) references UserSurvey (survey_id),
    constraint UserSurveyStyle_ibfk_2
        foreign key (style_id) references Style (style_id)
);

create index style_id
    on UserSurveyStyle (style_id);

create table posts
(
    post_id        int auto_increment
        primary key,
    board_type     varchar(50)                           null,
    user_id        bigint                                null,
    title          varchar(255)                          null,
    content        text                                  null,
    created_at     timestamp default current_timestamp() null,
    updated_at     timestamp default current_timestamp() null on update current_timestamp(),
    parent_post_id int                                   null,
    constraint posts_ibfk_1
        foreign key (user_id) references USER (user_id)
);

create table comments
(
    comment_id        int auto_increment
        primary key,
    post_id           int                                   null,
    user_id           bigint                                null,
    user_name         varchar(25)                           null,
    content           text                                  null,
    created_at        timestamp default current_timestamp() null,
    updated_at        timestamp default current_timestamp() null on update current_timestamp(),
    parent_comment_id int                                   null,
    constraint comments_ibfk_1
        foreign key (post_id) references posts (post_id),
    constraint comments_ibfk_2
        foreign key (user_id) references USER (user_id)
);

create index post_id
    on comments (post_id);

create index user_id
    on comments (user_id);

create table files
(
    file_id     int auto_increment
        primary key,
    post_id     int                                   null,
    file_name   varchar(255)                          null,
    file_path   varchar(255)                          null,
    uploaded_at timestamp default current_timestamp() null,
    constraint files_ibfk_1
        foreign key (post_id) references posts (post_id)
);

create index post_id
    on files (post_id);

create index user_id
    on posts (user_id);

create table images
(
    img_id      bigint auto_increment
        primary key,
    img_content mediumblob null,
    news_id     bigint     null
);

-- 관리자 페이지의 크롤링 버튼 활용해서 Raw_News, images 테이블 알아서 채워짐
-- brand, style 테이블 데이터 들어가 있어야 함.

INSERT INTO brand VALUES(1,'구찌');
INSERT INTO brand VALUES(2,'나이키');
INSERT INTO brand VALUES(3,'자라');
INSERT INTO brand VALUES(4,'폴로');
INSERT INTO brand VALUES(5,'스투시');

INSERT INTO style VALUES(1,'미니멀');
INSERT INTO style VALUES(2,'클래식');
INSERT INTO style VALUES(3,'스트릿');
INSERT INTO style VALUES(4,'아메카지');
INSERT INTO style VALUES(5,'캐쥬얼');
INSERT INTO style VALUES(6,'빈티지');


-- 맨 처음 만들 때 edit 해주기 귀찮은 사람들이 쓰는 쿼리
INSERT INTO ProcessedNews (news_id, title, subtitle, content, published_date, category) 
SELECT news_id, title, subtitle, content, published_date, 'trend'
FROM Raw_News 
WHERE news_id BETWEEN 1 AND 10;

INSERT INTO ProcessedNews (news_id, title, subtitle, content, published_date, category) 
SELECT news_id, title, subtitle, content, published_date, 'brand'
FROM Raw_News 
WHERE news_id BETWEEN 11 AND 20;

INSERT INTO ProcessedNews (news_id, title, subtitle, content, published_date, category) 
SELECT news_id, title, subtitle, content, published_date, 'celeb'
FROM Raw_News 
WHERE news_id BETWEEN 21 AND 30;

-- 자유게시판 게시글 세팅(최초 회원가입 후 사용)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('free', 1, 'example1', 'example', now(), now(), 0)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('free', 1, 'example2', 'example', now(), now(), 0)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('free', 1, 'example3', 'example', now(), now(), 0)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('free', 1, 'example4', 'example', now(), now(), 0)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('free', 1, 'example5', 'example', now(), now(), 0)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('free', 1, 'This is a very long title for a post', 'example', now(), now(), 0)

-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('event', 1, 'example1', 'example', now(), now(), 0)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('event', 1, 'example2', 'example', now(), now(), 0)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('event', 1, 'example3', 'example', now(), now(), 0)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('event', 1, 'example4', 'example', now(), now(), 0)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('event', 1, 'example5', 'example', now(), now(), 0)
-- INSERT posts(board_type, user_id, title, content, created_at, updated_at, parent_post_id)
-- VALUES('event', 1, 'This is a very long title for a post', 'example', now(), now(), 0)
