package com.java;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 邱高强
 * MapperScan( basePackages = {"com.java.book.mapper"})   开启mapper包扫描
 * EnableScheduling       // 定时任务注解
 */
@SpringBootApplication
@MapperScan( basePackages = {"com.java.book.mapper"})
@EnableScheduling
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

}
