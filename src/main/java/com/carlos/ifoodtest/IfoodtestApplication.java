package com.carlos.ifoodtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IfoodtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IfoodtestApplication.class, args);
    }
}
