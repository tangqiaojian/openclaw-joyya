-- ============================================
-- OpenClaw Joyya - Bilibili 网站数据库结构
-- ============================================
-- 功能：视频分享平台核心数据库设计
-- 版本：v1.0.0
-- 日期：2026-03-03
-- ============================================

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS joyya DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE joyya;

-- ============================================
-- 1. 用户表（扩展）
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户 ID，主键，自增',
    username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名，唯一，最大长度 20 字符',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱地址，唯一，最大长度 100 字符',
    password VARCHAR(255) NOT NULL COMMENT '密码，BCrypt 加密存储',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像 URL',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    signature VARCHAR(200) DEFAULT NULL COMMENT '个性签名',
    gender TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    level INT DEFAULT 0 COMMENT '用户等级',
    coins BIGINT DEFAULT 0 COMMENT '硬币数量',
    followers BIGINT DEFAULT 0 COMMENT '粉丝数',
    following BIGINT DEFAULT 0 COMMENT '关注数',
    is_verified TINYINT DEFAULT 0 COMMENT '是否认证：0-否，1-是',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
    
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_followers (followers)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ============================================
-- 2. 视频分类表
-- ============================================
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '分类 ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    description VARCHAR(200) DEFAULT NULL COMMENT '分类描述',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类 ID，0 表示顶级分类',
    icon VARCHAR(255) DEFAULT NULL COMMENT '分类图标 URL',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
    
    INDEX idx_parent_id (parent_id),
    INDEX idx_sort (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频分类表';

-- ============================================
-- 3. 视频表
-- ============================================
CREATE TABLE IF NOT EXISTS videos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '视频 ID',
    title VARCHAR(200) NOT NULL COMMENT '视频标题',
    description TEXT COMMENT '视频简介',
    cover VARCHAR(255) NOT NULL COMMENT '封面图 URL',
    video_url VARCHAR(500) NOT NULL COMMENT '视频播放 URL',
    duration INT DEFAULT 0 COMMENT '视频时长（秒）',
    category_id BIGINT DEFAULT NULL COMMENT '分类 ID，外键',
    user_id BIGINT NOT NULL COMMENT '上传者用户 ID，外键',
    view_count INT DEFAULT 0 COMMENT '播放次数',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    dislike_count INT DEFAULT 0 COMMENT '踩数',
    share_count INT DEFAULT 0 COMMENT '分享次数',
    coin_count INT DEFAULT 0 COMMENT '收藏/投币数',
    status TINYINT DEFAULT 1 COMMENT '状态：0-待审核，1-已发布，2-已下架',
    is_recommend TINYINT DEFAULT 0 COMMENT '是否推荐：0-否，1-是',
    is_hot TINYINT DEFAULT 0 COMMENT '是否热门：0-否，1-是',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
    
    INDEX idx_category_id (category_id),
    INDEX idx_user_id (user_id),
    INDEX idx_like_count (like_count),
    INDEX idx_view_count (view_count),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频表';

-- ============================================
-- 4. 视频标签表
-- ============================================
CREATE TABLE IF NOT EXISTS tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '标签 ID',
    name VARCHAR(50) NOT NULL UNIQUE COMMENT '标签名称',
    description VARCHAR(200) DEFAULT NULL COMMENT '标签描述',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- ============================================
-- 5. 视频标签关联表（多对多）
-- ============================================
CREATE TABLE IF NOT EXISTS video_tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '关联 ID',
    video_id BIGINT NOT NULL COMMENT '视频 ID',
    tag_id BIGINT NOT NULL COMMENT '标签 ID',
    created_at DATETIME NOT NULL COMMENT '关联时间',
    
    UNIQUE KEY uk_video_tag (video_id, tag_id),
    INDEX idx_video_id (video_id),
    INDEX idx_tag_id (tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频标签关联表';

-- ============================================
-- 6. 评论表
-- ============================================
CREATE TABLE IF NOT EXISTS comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '评论 ID',
    video_id BIGINT NOT NULL COMMENT '视频 ID',
    user_id BIGINT NOT NULL COMMENT '评论用户 ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论 ID，0 表示一级评论',
    content TEXT NOT NULL COMMENT '评论内容',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    reply_count INT DEFAULT 0 COMMENT '回复数',
    status TINYINT DEFAULT 1 COMMENT '状态：0-已删除，1-正常',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
    
    INDEX idx_video_id (video_id),
    INDEX idx_user_id (user_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- ============================================
-- 7. 点赞表（视频）
-- ============================================
CREATE TABLE IF NOT EXISTS video_likes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '点赞 ID',
    video_id BIGINT NOT NULL COMMENT '视频 ID',
    user_id BIGINT NOT NULL COMMENT '点赞用户 ID',
    created_at DATETIME NOT NULL COMMENT '点赞时间',
    
    UNIQUE KEY uk_video_user (video_id, user_id),
    INDEX idx_video_id (video_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频点赞表';

-- ============================================
-- 8. 收藏表
-- ============================================
CREATE TABLE IF NOT EXISTS collections (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '收藏 ID',
    user_id BIGINT NOT NULL COMMENT '收藏用户 ID',
    video_id BIGINT NOT NULL COMMENT '收藏视频 ID',
    created_at DATETIME NOT NULL COMMENT '收藏时间',
    
    UNIQUE KEY uk_user_video (user_id, video_id),
    INDEX idx_user_id (user_id),
    INDEX idx_video_id (video_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';

-- ============================================
-- 9. 观看历史记录表
-- ============================================
CREATE TABLE IF NOT EXISTS watch_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '历史记录 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    video_id BIGINT NOT NULL COMMENT '视频 ID',
    progress INT DEFAULT 0 COMMENT '观看进度（秒）',
    duration INT DEFAULT 0 COMMENT '视频总时长（秒）',
    watched_at DATETIME NOT NULL COMMENT '观看时间',
    
    UNIQUE KEY uk_user_video (user_id, video_id),
    INDEX idx_user_id (user_id),
    INDEX idx_video_id (video_id),
    INDEX idx_watched_at (watched_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='观看历史表';

-- ============================================
-- 10. 关注关系表
-- ============================================
CREATE TABLE IF NOT EXISTS followings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '关注 ID',
    follower_id BIGINT NOT NULL COMMENT '关注用户 ID',
    followee_id BIGINT NOT NULL COMMENT '被关注用户 ID',
    created_at DATETIME NOT NULL COMMENT '关注时间',
    
    UNIQUE KEY uk_follower_followee (follower_id, followee_id),
    INDEX idx_follower_id (follower_id),
    INDEX idx_followee_id (followee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='关注关系表';

-- ============================================
-- 触发器：更新时间
-- ============================================
DELIMITER $$

CREATE TRIGGER trg_users_update
BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW();
END$$

CREATE TRIGGER trg_videos_update
BEFORE UPDATE ON videos
FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW();
END$$

CREATE TRIGGER trg_categories_update
BEFORE UPDATE ON categories
FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW();
END$$

CREATE TRIGGER trg_comments_update
BEFORE UPDATE ON comments
FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW();
END$$

DELIMITER ;

-- ============================================
-- 插入测试数据
-- ============================================

-- 插入默认分类
INSERT INTO categories (name, description, sort_order, created_at)
VALUES 
    ('科技', '数码科技相关视频', 1, NOW()),
    ('生活', '日常生活相关视频', 2, NOW()),
    ('娱乐', '娱乐综艺相关视频', 3, NOW()),
    ('游戏', '游戏相关视频', 4, NOW()),
    ('影视', '影视剪辑相关视频', 5, NOW()),
    ('音乐', '音乐相关视频', 6, NOW()),
    ('动画', '动画相关视频', 7, NOW()),
    ('知识', '知识科普相关视频', 8, NOW());

-- 插入测试标签
INSERT INTO tags (name, description, sort_order, created_at)
VALUES 
    ('搞笑', '搞笑幽默内容', 1, NOW()),
    ('教程', '教程教学类内容', 2, NOW()),
    ('评测', '产品评测内容', 3, NOW()),
    ('vlog', '个人 vlog 记录', 4, NOW()),
    ('音乐', '音乐相关内容', 5, NOW());

-- 插入测试用户
INSERT INTO users (username, email, password, nickname, level, coins, followers, is_verified, created_at)
VALUES 
    ('admin', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376RoMquDU2HqTQ5ZLX7KZ5V7qQ5Z5V7qQ5Z5V7', '管理员', 5, 1000, 99999, 1, NOW()),
    ('testuser', 'test@example.com', '$2a$10$N.zmdr9k7uOCQb376RoMquDU2HqTQ5ZLX7KZ5V7qQ5Z5V7qQ5Z5V7', '测试用户', 1, 0, 0, 0, NOW());

-- 插入测试视频
INSERT INTO videos (title, description, cover, video_url, duration, category_id, user_id, view_count, like_count, status, created_at)
VALUES 
    ('2024 年最新技术趋势', '介绍 2024 年科技发展趋势', 'https://example.com/cover1.jpg', 'https://example.com/video1.mp4', 600, 1, 1, 10000, 500, 1, NOW()),
    ('生活小窍门分享', '分享日常生活中的实用小技巧', 'https://example.com/cover2.jpg', 'https://example.com/video2.mp4', 300, 2, 1, 5000, 200, 1, NOW()),
    ('精彩游戏集锦', '本周游戏精彩操作集锦', 'https://example.com/cover3.jpg', 'https://example.com/video3.mp4', 900, 4, 1, 8000, 300, 1, NOW());

-- ============================================
-- 表状态：已创建，包含触发器和测试数据
-- ============================================
-- 总表数：10
-- 总索引数：20+
-- 触发器：3 个自动更新时间
-- 测试数据：已插入

COMMIT;
