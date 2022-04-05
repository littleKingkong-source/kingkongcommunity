create table comment
(
    id BIGINT auto_increment,
    content varchar(1024) not null,
    parent_id BIGINT not null,
    type int not null,
    commentator BIGINT null,
    gmt_create BIGINT not null,
    gmt_modified BIGINT not null,
    like_count BIGINT default 0 null,
    constraint comment_pk
        primary key (id)
);

