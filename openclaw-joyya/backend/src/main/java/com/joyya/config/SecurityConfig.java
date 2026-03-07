package com.joyya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置类
 * 提供密码加密相关 Bean
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Configuration
public class SecurityConfig {

    /**
     * BCrypt 密码编码器
     * 用于密码加密和验证
     * 
     * @return BCryptPasswordEncoder 实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
