create table fashiondb.CATEGORY
(
    style varchar(50) not null
        primary key
);

create table fashiondb.CONTENT_TYPE
(
    content_type varchar(50)  not null
        primary key,
    description  varchar(200) null
);

create table fashiondb.CRAWLING_SITE
(
    site_address    varchar(200) not null
        primary key,
    site_name       varchar(100) null,
    last_crawled    datetime     null,
    crawl_frequency varchar(20)  null
);

create table fashiondb.IMAGES
(
    image_id int auto_increment
        primary key,
    url      varchar(500) not null,
    alt_text varchar(200) null,
    caption  varchar(500) null
);

create table fashiondb.ITEM
(
    item_id     int auto_increment
        primary key,
    name        varchar(100)   not null,
    description varchar(500)   null,
    price       decimal(10, 2) null,
    brand       varchar(50)    null,
    style       varchar(50)    null,
    constraint ITEM_ibfk_1
        foreign key (style) references fashiondb.CATEGORY (style)
);

create index style
    on fashiondb.ITEM (style);

create table fashiondb.ITEM_CATEGORY
(
    item_id int         not null,
    style   varchar(50) not null,
    primary key (item_id, style),
    constraint ITEM_CATEGORY_ibfk_1
        foreign key (item_id) references fashiondb.ITEM (item_id),
    constraint ITEM_CATEGORY_ibfk_2
        foreign key (style) references fashiondb.CATEGORY (style)
);

create index style
    on fashiondb.ITEM_CATEGORY (style);

create table fashiondb.ITEM_IMAGE
(
    item_id  int                  not null,
    image_id int                  not null,
    is_main  tinyint(1) default 0 null,
    primary key (item_id, image_id),
    constraint ITEM_IMAGE_ibfk_1
        foreign key (item_id) references fashiondb.ITEM (item_id),
    constraint ITEM_IMAGE_ibfk_2
        foreign key (image_id) references fashiondb.IMAGES (image_id)
);

create index image_id
    on fashiondb.ITEM_IMAGE (image_id);

create table fashiondb.NEWS_TYPE
(
    type             varchar(50)  not null
        primary key,
    type_description varchar(200) null
);

create table fashiondb.NEWS
(
    news_id        int auto_increment
        primary key,
    title          varchar(200)  not null,
    content        text          null,
    type           varchar(50)   null,
    source         varchar(100)  null,
    author         varchar(50)   null,
    published_date date          null,
    modified_date  date          null,
    visit_count    int default 0 null,
    like_count     int default 0 null,
    style          varchar(50)   null,
    constraint NEWS_ibfk_1
        foreign key (type) references fashiondb.NEWS_TYPE (type),
    constraint NEWS_ibfk_2
        foreign key (style) references fashiondb.CATEGORY (style)
);

create index style
    on fashiondb.NEWS (style);

create index type
    on fashiondb.NEWS (type);

create table fashiondb.NEWS_IMAGE
(
    news_id  int                  not null,
    image_id int                  not null,
    is_main  tinyint(1) default 0 null,
    primary key (news_id, image_id),
    constraint NEWS_IMAGE_ibfk_1
        foreign key (news_id) references fashiondb.NEWS (news_id),
    constraint NEWS_IMAGE_ibfk_2
        foreign key (image_id) references fashiondb.IMAGES (image_id)
);

create index image_id
    on fashiondb.NEWS_IMAGE (image_id);

create table fashiondb.PAGE
(
    page_id      int auto_increment
        primary key,
    url          varchar(200)                         not null,
    title        varchar(100)                         null,
    description  varchar(500)                         null,
    created_at   datetime default current_timestamp() null,
    content_type varchar(50)                          not null,
    constraint PAGE_ibfk_1
        foreign key (content_type) references fashiondb.CONTENT_TYPE (content_type)
);

create index content_type
    on fashiondb.PAGE (content_type);

create table fashiondb.SURVEY
(
    survey_id   int auto_increment
        primary key,
    title       varchar(100)                         not null,
    description varchar(500)                         null,
    created_at  datetime default current_timestamp() null
);

create table fashiondb.SURVEY_QUESTION
(
    question_id   int auto_increment
        primary key,
    survey_id     int          null,
    question_text varchar(500) not null,
    constraint SURVEY_QUESTION_ibfk_1
        foreign key (survey_id) references fashiondb.SURVEY (survey_id)
);

create table fashiondb.SURVEY_OPTION
(
    option_id   int auto_increment
        primary key,
    question_id int          null,
    option_text varchar(500) not null,
    constraint SURVEY_OPTION_ibfk_1
        foreign key (question_id) references fashiondb.SURVEY_QUESTION (question_id)
);

create index question_id
    on fashiondb.SURVEY_OPTION (question_id);

create index survey_id
    on fashiondb.SURVEY_QUESTION (survey_id);

create table fashiondb.USER
(
    user_id    int auto_increment
        primary key,
    password   varchar(255)                             not null,
    name       varchar(50)                              not null,
    email      varchar(100)                             not null,
    created_at datetime     default current_timestamp() null,
    updated_at datetime                                 null,
    role       varchar(255) default 'ROLE_USER'         not null,
    constraint email
        unique (email)
);

create table fashiondb.USER_PAGE
(
    user_id  int                                  not null,
    page_id  int                                  not null,
    saved_at datetime default current_timestamp() null,
    primary key (user_id, page_id),
    constraint USER_PAGE_ibfk_1
        foreign key (user_id) references fashiondb.USER (user_id),
    constraint USER_PAGE_ibfk_2
        foreign key (page_id) references fashiondb.PAGE (page_id)
);

create index page_id
    on fashiondb.USER_PAGE (page_id);

create table fashiondb.USER_SURVEY_RESPONSE
(
    user_id       int                                  not null,
    question_id   int                                  not null,
    option_id     int                                  not null,
    response_date datetime default current_timestamp() null,
    primary key (user_id, question_id, option_id),
    constraint USER_SURVEY_RESPONSE_ibfk_1
        foreign key (user_id) references fashiondb.USER (user_id),
    constraint USER_SURVEY_RESPONSE_ibfk_2
        foreign key (question_id) references fashiondb.SURVEY_QUESTION (question_id),
    constraint USER_SURVEY_RESPONSE_ibfk_3
        foreign key (option_id) references fashiondb.SURVEY_OPTION (option_id)
);

create index option_id
    on fashiondb.USER_SURVEY_RESPONSE (option_id);

create index question_id
    on fashiondb.USER_SURVEY_RESPONSE (question_id);

