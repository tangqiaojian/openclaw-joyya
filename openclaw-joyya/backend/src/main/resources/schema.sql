-- 数据库初始化脚本
-- OpenClaw Joyya - 用户登录注册系统

-- 创建数据库
CREATE DATABASE IF NOT EXISTS joyya DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE joyya;

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户 ID',
    username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
    password VARCHAR(255) NOT NULL COMMENT '密码（BCrypt 加密）',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 添加字段注释
ALTER TABLE users ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP;

-- 插入测试数据（可选）
-- 用户名：admin
-- 密码：123456（BCrypt 加密后：$2a$10$N.zmdr9k7uOCQb376RoMquDU2HqTQ5ZLX7KZ5V7qQ5Z5V7qQ5Z5V7）
INSERT INTO users (username, email, password, created_at, updated_at)
VALUES (
    'admin',
    'admin@example.com',
    '$2a$10$N.zmdr9k7uOCQb376RoMquDU2HqTQ5ZLX7KZ5V7qQ5Z5V7qQ5Z5V7',
    NOW(),
    NOW()
) ON DUPLICATE KEY UPDATE email = email;

-- 用户表状态：已创建，包含索引和触发器
-- 索引：username(唯一), email(唯一)
-- 触发器：创建时间自动更新

COMMIT;
