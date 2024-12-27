package com.farfarcoder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan(basePackages = "com.farfarcoder.domain.*.repository")
public class TourApplication {
    public static void main(String[] args) {
        SpringApplication.run(TourApplication.class, args);
    }
}