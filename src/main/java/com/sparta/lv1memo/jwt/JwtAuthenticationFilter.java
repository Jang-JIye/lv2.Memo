package com.sparta.lv1memo.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.lv1memo.dto.LoginRequestDto;
import com.sparta.lv1memo.entity.UserRoleEnum;
import com.sparta.lv1memo.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
// ★★ JWT 인증 처리 Filter

// 이전에는 인증 및 토큰 생성을 컨트롤러-서비스에서 했는데 이젠 인증,인가와 비니지스 로직을 완전히 분리한다
// 더이성 UserController에서 로그인을 구현하지 않음, Filter단에서만 처리할거임

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // UsernamePasswordAuthenticationFilter : 사용자가 username과 password를 보내면 그걸 인증객체
    // 즉, usernamePasswordAuthenticationToken을 만든 다음에 AuthenticationManager을 통해서 확인까지 하는 작업을 함
    // 그 과정을 직접 구현하기 위해 상속받아옴, 직접구현하지 않으면 session 방식으로 작동함, 그리고 JWT도 생성해줘야 함
    // 그리고 상속받아서 진행하면 setFilterProcessesUrl()를 호출할 수 있음
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");  // 우리가 지정한 Post방식의 로그인 URL임
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            // 첫번째 파라미터: Request요청 Body부분에 username이랑 password가 넘어온다 (JSON 형식으로)
            // 두번째 파라미터: LoginRequestDto.class으로 변경해서 LoginRequestDto에 담아줌


            return getAuthenticationManager().authenticate(   // .authenticate() : 이게 인증,검증하는 메서드임
                    new UsernamePasswordAuthenticationToken( // 반환해줄 토큰을 여기서 생성함
                            requestDto.getUsername(),   // username
                            requestDto.getPassword(),   // password
                            null    // 권한
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");  // 로그인 성공시 이 메서드가 수행된다
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();  // @Authentication 했던걸 직접 구현, UserDetailsImpl로 cating함
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response); // jwtUtil 의 메서드 실행
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}