create table question
(
    id int auto_increment,
    title varchar(50) null,
    description TEXT null,
    gmt_create BIGINT null,
    gmt_modified BIGINT null,
    creator int null,
    comment_count int null,
    view_count int null,
    like_count int null,
    tags varchar(255) null,
    constraint question_pk
        primary key (id)
);

