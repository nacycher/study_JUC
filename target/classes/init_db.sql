drop database if exists study_juc;
drop table if exists user_info;

create database if not exists study_juc;
create table user_info if not exists user_info
(
    id          int auto_increment
        primary key,
    name        varchar(100)      not null,
    age         int               not null,
    email       varchar(100)      null,
    address     varchar(100)      null,
    delete_flag tinyint default 0 not null,
    version     int     default 0 not null
);

