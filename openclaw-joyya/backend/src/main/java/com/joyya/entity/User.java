package com.joyya.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库中的 users 表
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Entity
@Table(name = "users")
@Data
public class User {

    /**
     * 用户 ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名，唯一，最大长度 20 字符
     * 用于登录验证和用户识别
     */
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    /**
     * 邮箱地址，唯一，最大长度 100 字符
     * 用于用户注册和找回密码
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * 密码，加密存储（BCrypt）
     * 存储前需使用 BCryptPasswordEncoder.encode() 进行加密
     */
    @Column(nullable = false)
    private String password;

    /**
     * 创建时间，系统自动填充
     * 记录用户账户创建的时刻
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新时间，系统自动更新
     * 记录用户信息最后修改的时刻
     */
    @Column
    private LocalDateTime updatedAt;

    /**
     * 实体类创建前的回调方法
     * 自动设置创建时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 实体类更新前的回调方法
     * 自动更新时间戳
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
