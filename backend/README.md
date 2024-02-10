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


![](C:\Users\csimo\Downloads\security.png)


1. 로그인 페이지에서 username + password 를 Http Request 로 인증 요청한다
2. 기본적으로 "/login" 요청을 받으면 AuthenticationFilter 가 해당 request 를 가로챈다
// attemptAuthentication 실행
2-1. UsernamePasswordAuthenticationToken 으로 인증용 객체를 생성해서
2-2. Authentication 을 보내면 userCustomDetailsService 의 loadUserByusername 이 실행되고
2-3. DB 에서 username 일치여부 확인 후 customUserDetails 객체를 만들어 반환한다.
2-4. authentication 권한 정보 확인 후 반환한다.
3. attemptAuthentication 이 성공하면 successfulAuthentication 이 실행되고 
    response 헤더에 Authorization 에 Bearer + token 정보를 담아 보낸다.
4. 이제 다음 요청들부터는 authorizationFilter 에서 request에서 받은 Header에서 Bearer 
    확인 후 SecurityContextHolder 에 저장하고 권한 관리할 수 있다.