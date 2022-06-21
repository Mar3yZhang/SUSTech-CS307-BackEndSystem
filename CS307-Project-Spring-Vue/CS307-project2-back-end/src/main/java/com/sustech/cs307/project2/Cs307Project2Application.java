package com.sustech.cs307.project2;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.sustech.cs307.project2.mapper")
public class Cs307Project2Application {
    public static void main(String[] args) {
        SpringApplication.run(Cs307Project2Application.class, args);
    }
}
