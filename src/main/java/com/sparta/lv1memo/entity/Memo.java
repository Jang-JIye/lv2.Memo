package com.sparta.lv1memo.entity;

import com.sparta.lv1memo.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "memo")
@NoArgsConstructor
public class Memo extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;//제목

    @Column(name = "username", nullable = false)
    private String username;//작성자명

    @Column(name = "contents", nullable = false)
    private String contents;//작성 내용

    @Column(name = "password", nullable = false)
    private String password;//비밀번호
//    private LocalDateTime date;//작성 날짜

    public Memo(MemoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
//        this.date = LocalDateTime.now();
    }

    public void update(MemoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }
}
