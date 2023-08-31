package com.sparta.lv1memo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  // class위에 @Configuration를 설정한다
public class PasswordConfig {

    @Bean  // 빈 수동등록 하고자 하는 객체를 반환하는 메서드를 선언한다
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

        // PasswordEncoder : 인터페이스
        // BCryptPasswordEncoder: 비밀번호를 암호화해주는 Hash함수

    }
}