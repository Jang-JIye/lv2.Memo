package com.sparta.lv1memo.service;

import com.sparta.lv1memo.dto.SignupRequestDto;
import com.sparta.lv1memo.entity.User;
import com.sparta.lv1memo.entity.UserRoleEnum;
import com.sparta.lv1memo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<String> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"status\": 400, \"msg\": \"중복된 사용자가 존재합니다.\"}");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"status\": 400, \"msg\": \"관리자 암호가 일치하지 않습니다.\"}");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, role);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"status\": 200, \"msg\": \"회원가입 성공\"}");
    }
}