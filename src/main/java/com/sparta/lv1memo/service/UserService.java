package com.sparta.lv1memo.service;

import com.sparta.lv1memo.dto.SignupRequestDto;
import com.sparta.lv1memo.entity.User;
import com.sparta.lv1memo.entity.UserRoleEnum;
import com.sparta.lv1memo.jwt.JwtUtil;
import com.sparta.lv1memo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service  // @RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;  // 내가 등록한거 가져오기

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ADMIN_TOKEN _일반 사용자인지 관리자인지 구분하기 위해 사용함
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";  // 실제로 이렇게 주진 않음

    public void signup(SignupRequestDto requestDto) {

        // - username 변수
        String username = requestDto.getUsername();
        // - password encoding
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(username, password);
        userRepository.save(user);
    }
}