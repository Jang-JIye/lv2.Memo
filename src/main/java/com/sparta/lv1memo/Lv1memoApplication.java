package com.sparta.lv1memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Lv1memoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lv1memoApplication.class, args);
    }

}
