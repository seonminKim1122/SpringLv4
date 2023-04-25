package com.example.hanghaehomework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HanghaehomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaehomeworkApplication.class, args);
    }

}
