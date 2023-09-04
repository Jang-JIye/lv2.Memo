package com.sparta.lv1memo.entity;

import com.sparta.lv1memo.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "memo")
@NoArgsConstructor
public class Memo extends TimeStamped {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Memo(MemoRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
        this.user = user;
    }

    public void update(MemoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }

    public void setUser(User user) {
        this.user = user;
        user.getMemoList().add(this);
    }
}
