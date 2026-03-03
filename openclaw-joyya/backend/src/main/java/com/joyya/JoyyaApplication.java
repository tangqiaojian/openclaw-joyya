package com.joyya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 主应用类
 * 启动类，作为应用程序的入口点
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@SpringBootApplication
public class JoyyaApplication {

    /**
     * 主方法
     * 启动 Spring Boot 应用程序
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(JoyyaApplication.class, args);
    }
}
