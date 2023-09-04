package com.sparta.lv1memo.repository;

import com.sparta.lv1memo.entity.Memo;
import com.sparta.lv1memo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();

    List<Memo> findAllByUserOrderByModifiedAtDesc(User user);
}
