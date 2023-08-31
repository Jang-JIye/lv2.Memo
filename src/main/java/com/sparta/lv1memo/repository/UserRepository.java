package com.sparta.lv1memo.repository;

import com.sparta.lv1memo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);  // username 찾는 메서드
}
