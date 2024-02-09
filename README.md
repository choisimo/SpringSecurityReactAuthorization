테이블 

create table member(
	id bigint auto_increment primary key,
    member_password varchar(100),
    member_name varchar(15),
    regdate datetime,
    enabled boolean
);


create table auth (
    id bigint auto_increment primary key,
    member_name varchar(15),
    auth varchar(10)
);
