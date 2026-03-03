package com.joyya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 安全配置类
 * 配置 Spring Security 安全策略和认证机制
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 配置安全过滤链
     * 定义哪些接口可以匿名访问，哪些需要认证
     * 
     * @param http HTTP 安全配置对象
     * @return 安全过滤链配置
     * @throws Exception 配置异常
     * 
     * @apiNote
     *         认证接口 (/api/auth/**) 对所有人开放
     *         其他接口需要认证后才能访问
     *         禁用表单登录和 Basic 认证
     *         禁用 CSRF 保护（JWT 认证不需要）
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用 CSRF 保护（JWT 认证不需要 CSRF Token）
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // 认证接口允许匿名访问
                .requestMatchers("/api/auth/**").permitAll()
                // 其他所有接口需要认证
                .anyRequest().authenticated()
            )
            // 禁用表单登录
            .formLogin(AbstractHttpConfigurer::disable)
            // 禁用 Basic 认证
            .httpBasic(AbstractHttpConfigurer::disable);
        
        return http.build();
    }

    /**
     * 配置密码加密编码器
     * 使用 BCrypt 算法进行密码加密
     * 
     * @return BCrypt 密码编码器实例
     * 
     * @apiNote
     *         BCrypt 是安全的密码哈希算法
     *         自动处理盐值生成
     *         加密强度因子为 10（默认值）
     *         可通过设置成本因子调整加密强度
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
