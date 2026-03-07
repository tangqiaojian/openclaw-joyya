-- H2 Database Schema for Joyya Bilibili Clone
-- This script is compatible with H2 in-memory database

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    avatar VARCHAR(255),
    nickname VARCHAR(50),
    signature VARCHAR(200),
    gender SMALLINT DEFAULT 0,
    level INT DEFAULT 0,
    coins BIGINT DEFAULT 0,
    followers BIGINT DEFAULT 0,
    following BIGINT DEFAULT 0,
    is_verified SMALLINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200),
    sort_order INT DEFAULT 0,
    parent_id BIGINT DEFAULT 0,
    icon VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS videos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    cover VARCHAR(255) NOT NULL,
    video_url VARCHAR(500) NOT NULL,
    duration INT DEFAULT 0,
    category_id BIGINT,
    user_id BIGINT NOT NULL,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    dislike_count INT DEFAULT 0,
    share_count INT DEFAULT 0,
    coin_count INT DEFAULT 0,
    status SMALLINT DEFAULT 1,
    is_recommend SMALLINT DEFAULT 0,
    is_hot SMALLINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    sort_order INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS video_tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    video_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (video_id, tag_id)
);

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    video_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    parent_id BIGINT DEFAULT 0,
    content TEXT NOT NULL,
    like_count INT DEFAULT 0,
    reply_count INT DEFAULT 0,
    status SMALLINT DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS video_likes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    video_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (video_id, user_id)
);

CREATE TABLE IF NOT EXISTS collections (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    video_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, video_id)
);

CREATE TABLE IF NOT EXISTS watch_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    video_id BIGINT NOT NULL,
    progress INT DEFAULT 0,
    duration INT DEFAULT 0,
    watched_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, video_id)
);

CREATE TABLE IF NOT EXISTS followings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    followee_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (follower_id, followee_id)
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_followers ON users(followers);
CREATE INDEX IF NOT EXISTS idx_videos_category ON videos(category_id);
CREATE INDEX IF NOT EXISTS idx_videos_user ON videos(user_id);
CREATE INDEX IF NOT EXISTS idx_videos_likes ON videos(like_count);
CREATE INDEX IF NOT EXISTS idx_videos_views ON videos(view_count);
CREATE INDEX IF NOT EXISTS idx_videos_status ON videos(status);
CREATE INDEX IF NOT EXISTS idx_videos_created ON videos(created_at);
CREATE INDEX IF NOT EXISTS idx_tags_name ON tags(name);
CREATE INDEX IF NOT EXISTS idx_video_tags_video ON video_tags(video_id);
CREATE INDEX IF NOT EXISTS idx_video_tags_tag ON video_tags(tag_id);
CREATE INDEX IF NOT EXISTS idx_comments_video ON comments(video_id);
CREATE INDEX IF NOT EXISTS idx_comments_user ON comments(user_id);
CREATE INDEX IF NOT EXISTS idx_comments_parent ON comments(parent_id);
CREATE INDEX IF NOT EXISTS idx_comments_created ON comments(created_at);
CREATE INDEX IF NOT EXISTS idx_video_likes_video ON video_likes(video_id);
CREATE INDEX IF NOT EXISTS idx_video_likes_user ON video_likes(user_id);
CREATE INDEX IF NOT EXISTS idx_collections_user ON collections(user_id);
CREATE INDEX IF NOT EXISTS idx_collections_video ON collections(video_id);
CREATE INDEX IF NOT EXISTS idx_watch_history_user ON watch_history(user_id);
CREATE INDEX IF NOT EXISTS idx_watch_history_video ON watch_history(video_id);
CREATE INDEX IF NOT EXISTS idx_watch_history_watched ON watch_history(watched_at);
CREATE INDEX IF NOT EXISTS idx_followings_follower ON followings(follower_id);
CREATE INDEX IF NOT EXISTS idx_followings_followee ON followings(followee_id);

-- Test Data
INSERT INTO users (username, email, password, nickname, signature, level, is_verified, created_at) VALUES
('admin', 'admin@joyya.com', '$2a$10$test', '管理员', 'Joyya 平台管理员', 6, 1, CURRENT_TIMESTAMP),
('testuser', 'test@joyya.com', '$2a$10$test', '测试用户', '这是一个测试账号', 1, 0, CURRENT_TIMESTAMP),
('测试小天', 'testxiaotian@joyya.com', '$2a$10$test', '测试小天', '网站测试专用账号', 2, 0, CURRENT_TIMESTAMP);

INSERT INTO categories (name, description, sort_order, created_at) VALUES
('科技', '科技数码相关内容', 1, CURRENT_TIMESTAMP),
('生活', '日常生活分享', 2, CURRENT_TIMESTAMP),
('娱乐', '娱乐综艺节目', 3, CURRENT_TIMESTAMP),
('游戏', '各类游戏视频', 4, CURRENT_TIMESTAMP),
('影视', '电影电视剧剪辑', 5, CURRENT_TIMESTAMP),
('音乐', '音乐MV和翻唱', 6, CURRENT_TIMESTAMP),
('动画', '动漫二次元', 7, CURRENT_TIMESTAMP),
('知识', '知识科普教育', 8, CURRENT_TIMESTAMP);

INSERT INTO tags (name, description, sort_order, created_at) VALUES
('搞笑', '搞笑幽默类', 1, CURRENT_TIMESTAMP),
('教程', '教学教程类', 2, CURRENT_TIMESTAMP),
('评测', '产品评测类', 3, CURRENT_TIMESTAMP),
('vlog', '生活记录', 4, CURRENT_TIMESTAMP),
('音乐', '音乐相关内容', 5, CURRENT_TIMESTAMP);

INSERT INTO videos (title, description, cover, video_url, duration, category_id, user_id, view_count, like_count, status, is_recommend, is_hot, created_at) VALUES
('2024 年最新技术趋势', '分享 2024 年技术发展趋势，包括 AI、云计算、大数据等前沿技术', 'https://example.com/cover1.jpg', 'https://example.com/video1.mp4', 1200, 1, 1, 10000, 500, 1, 1, 1, CURRENT_TIMESTAMP),
('生活小窍门分享', '分享日常生活中的实用小窍门和技巧，让你的生活更便利', 'https://example.com/cover2.jpg', 'https://example.com/video2.mp4', 600, 2, 2, 5000, 200, 1, 0, 0, CURRENT_TIMESTAMP),
('精彩游戏集锦', '2024 年度最佳游戏精彩瞬间集锦，精彩不容错过', 'https://example.com/cover3.jpg', 'https://example.com/video3.mp4', 1800, 4, 1, 20000, 1500, 1, 1, 1, CURRENT_TIMESTAMP);

-- Video-Tag mappings
INSERT INTO video_tags (video_id, tag_id, created_at) VALUES
(1, 2, CURRENT_TIMESTAMP),
(1, 3, CURRENT_TIMESTAMP),
(2, 4, CURRENT_TIMESTAMP),
(3, 1, CURRENT_TIMESTAMP);
