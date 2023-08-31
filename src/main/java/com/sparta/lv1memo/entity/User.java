package com.sparta.lv1memo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)  // Enum 타입을 데이터베이스에 저장할 때 사용하는 애노테이션
    private UserRoleEnum role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
//        this.email = email;
//        this.role = role;
    }
}

