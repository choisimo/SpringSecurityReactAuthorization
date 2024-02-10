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


회원가입 후 username, password 로그인으로 정상적으로 들어오면
토큰을 만들어서 frontend 에 생성된 token 을 응답해준다.

1. frontend 에서 request에 token 과 Header에 Authorization 을 함께 보낸다

2. OncePerRequestFilter는 모든 Http 요청에 대해 무조건 1번 실행되므로
    해당 request에서 Header의 Authorization 을 확인해서 토큰을 검증한다.

2.5. 스프링 시큐리티 UsernamePasswordAuthenticationFilter 는 "/login" 요청해서
    username, password 전송시 동작하지만, formLogin().disable() 할 때, 실행되지 않으므로
    .addFilter(new UsernamePasswordAuthenticationFilter) 를 등록해야함

    // UsernamePasswordAuthenticationFilter 를 extends 한
       JwtAuthenticationFilter(new authenticationManger) 를 적용!
    => JwtAuthenticationFilter 에서는 AuthenticationManager authenticationManager; 
        Authentication attemptAuthentication => 로그인 시도시 실행

3. UsernamePasswordAuthenticationToken 에서 username, password 받아서
         UsernamePasswordAuthenticationToken authenticationToken =
         new UsernamePasswordAuthenticationToken(username, password);
         으로 인증 토큰 생성 후
         Authentication authentication = authenticationManager.authentication(authenticationToken)
         으로 loadUserByUsername() 함수로 인증된 사용자 일 경우, UserDetails 를 return 해준다

3-1. authenticationManager가 로그인 시도를 하면, 
     UserDetailsService 가 호출되며, loadUserByUsername 함수가 
     해당 request의 username 이 실제로 존재하는 username 인지 확인하고 
     결과 값을 UserDetails 로 보내준다.

4. UserDetails 를 session에 담고 JWT 토큰을 만들어서 응답함.
    => session에 담지 않으면 스프링 시큐리티가 권한 확인을 할 수 없어서
       권한 관리를 할 수 없다!

6. attemptAuthentication 실행 후 인증이 정상적으로 되었다면 successAuthentication 함수 실행.
    -> jwt 만들어서 request 한 사용자에게 jwt response

   