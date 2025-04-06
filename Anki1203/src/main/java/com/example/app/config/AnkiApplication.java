package com.example.app.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.app.mapper")
public class AnkiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnkiApplication.class, args);
    }
}