package com.zyh.javaim.logic;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class LogicApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogicApplication.class, args);
    }

}
