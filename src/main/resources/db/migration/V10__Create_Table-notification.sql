create table notification
(
    id BIGINT auto_increment,
    notifier BIGINT not null comment '通知的人',
    receiver BIGINT null comment '接受通知的人',
    outerId BIGINT not null,
    type int not null comment '用来确定是评论还是什么',
    gmt_create BIGINT not null,
    status int default 0 not null comment '状态，1已读，0未读',
    constraint notification_pk
        primary key (id)
);

