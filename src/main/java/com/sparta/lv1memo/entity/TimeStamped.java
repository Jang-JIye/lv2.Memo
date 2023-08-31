package com.sparta.lv1memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Entity 클래스의 상속
@EntityListeners(AuditingEntityListener.class)
//Timestamped 클래스 자체가 다른 클래스에 상속되는 클래스기 때문에 abstract 으로 선언
public abstract class TimeStamped {

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column // updatable = true 가 default
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}

