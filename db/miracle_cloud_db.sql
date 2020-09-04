create table login_log
(
    id         bigint auto_increment
        primary key,
    user_name  varchar(38) null,
    ip         varchar(38) null,
    login_time datetime    null,
    city       varchar(38) null,
    msg        varchar(80) null,
    status     char        null
)
    comment '登录日志';

create table menu
(
    id        bigint auto_increment
        primary key,
    menu_name varchar(38)                null,
    parent_id bigint                     null,
    order_num bigint                     null,
    path      varchar(48)                null,
    component varchar(48) default 'Home' not null,
    icon      varchar(48)                null,
    visible   int         default 0      not null
)
    comment '菜单表';

create table role
(
    role_id varchar(38)      not null
        primary key,
    name    varchar(18)      null,
    locked  char default '0' not null
)
    comment '角色表';

create table role_menu
(
    id      bigint(38) auto_increment
        primary key,
    menu_id bigint      null,
    role_id varchar(38) null
);

create table user
(
    user_id   varchar(38) not null
        primary key,
    user_name varchar(38) null,
    password  varchar(38) null,
    mail      varchar(38) null,
    locked    char        null
)
    comment '用户表';

create table user_role
(
    user_id varchar(38) not null
        primary key,
    role_id varchar(38) not null
);


