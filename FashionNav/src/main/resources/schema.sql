create table Banner
(
    banner_id    bigint auto_increment
        primary key,
    title        varchar(255) null,
    url          varchar(255) null,
    image_url    varchar(255) null,
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