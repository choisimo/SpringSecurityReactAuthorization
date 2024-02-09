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



![](C:\Users\csimo\Downloads\springbootsecurity1.png)


login 시도 -> authenticationManager 호출 
->  userCustomDetailService 호출 후 override 된
;loadUserByUsername 함수 실행 