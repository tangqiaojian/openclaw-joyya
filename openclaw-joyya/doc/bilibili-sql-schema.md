# OpenClaw Joyya - Bilibili 数据库结构说明

本文档详细说明 Bilibili 网站项目的数据库结构设计。

## 📊 数据库概览

**数据库名：** joyya

**总表数：** 10 张

**主要表：**
1. users - 用户表
2. categories - 视频分类表
3. videos - 视频表
4. tags - 标签表
5. video_tags - 视频标签关联表
6. comments - 评论表
7. video_likes - 视频点赞表
8. collections - 收藏表
9. watch_history - 观看历史表
10. followings - 关注关系表

---

## 表 1: users - 用户表

### 表结构
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户 ID',
    username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
    password VARCHAR(255) NOT NULL COMMENT '密码 (BCrypt 加密)',
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
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间'
);
```

### 索引
- `PRIMARY KEY`: id
- `UNIQUE`: username, email
- `INDEX`: username, email, followers

### 字段说明

| 字段 | 类型 | 说明 |
|-----|-----|-|
| id | BIGINT | 主键，自增 |
| username | VARCHAR(20) | 用户名，唯一，最长 20 字符 |
| email | VARCHAR(100) | 邮箱，唯一，最长 100 字符 |
| password | VARCHAR(255) | BCrypt 加密密码 |
| avatar | VARCHAR(255) | 头像 URL 地址 |
| nickname | VARCHAR(50) | 显示昵称 |
| signature | VARCHAR(200) | 个人签名 |
| gender | TINYINT | 性别 (0 未知，1 男，2 女) |
| level | INT | 用户等级 (0-6 级) |
| coins | BIGINT | 硬币数量 |
| followers | BIGINT | 粉丝数 |
| following | BIGINT | 关注数 |
| is_verified | TINYINT | 认证状态 |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

---

## 表 2: categories - 视频分类表

### 表结构
```sql
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '分类 ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    description VARCHAR(200) DEFAULT NULL COMMENT '分类描述',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类 ID',
    icon VARCHAR(255) DEFAULT NULL COMMENT '分类图标',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间'
);
```

### 索引
- `PRIMARY KEY`: id
- `INDEX`: parent_id, sort_order

### 字段说明

| 字段 | 类型 | 说明 |
|-----|-----|-|
| id | BIGINT | 主键，自增 |
| name | VARCHAR(50) | 分类名称 |
| description | VARCHAR(200) | 分类描述 |
| sort_order | INT | 排序顺序 |
| parent_id | BIGINT | 父分类 ID(0 为顶级) |
| icon | VARCHAR(255) | 图标 URL |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

---

## 表 3: videos - 视频表

### 表结构
```sql
CREATE TABLE videos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '视频 ID',
    title VARCHAR(200) NOT NULL COMMENT '视频标题',
    description TEXT COMMENT '视频简介',
    cover VARCHAR(255) NOT NULL COMMENT '封面图 URL',
    video_url VARCHAR(500) NOT NULL COMMENT '视频 URL',
    duration INT DEFAULT 0 COMMENT '时长 (秒)',
    category_id BIGINT DEFAULT NULL COMMENT '分类 ID',
    user_id BIGINT NOT NULL COMMENT '上传者 ID',
    view_count INT DEFAULT 0 COMMENT '播放量',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    dislike_count INT DEFAULT 0 COMMENT '踩数',
    share_count INT DEFAULT 0 COMMENT '分享数',
    coin_count INT DEFAULT 0 COMMENT '投币数',
    status TINYINT DEFAULT 1 COMMENT '状态：0 待审核，1 已发布，2 已下架',
    is_recommend TINYINT DEFAULT 0 COMMENT '是否推荐',
    is_hot TINYINT DEFAULT 0 COMMENT '是否热门',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间'
);
```

### 索引
- `PRIMARY KEY`: id
- `INDEX`: category_id, user_id, like_count, view_count, status, created_at

### 字段说明

| 字段 | 类型 | 说明 |
|-----|-----|-|
| id | BIGINT | 主键，自增 |
| title | VARCHAR(200) | 视频标题 |
| description | TEXT | 视频描述 |
| cover | VARCHAR(255) | 封面图 URL |
| video_url | VARCHAR(500) | 视频播放 URL |
| duration | INT | 时长 (秒) |
| category_id | BIGINT | 分类 ID |
| user_id | BIGINT | 上传者 ID |
| view_count | INT | 播放量 |
| like_count | INT | 点赞数 |
| dislike_count | INT | 踩数 |
| share_count | INT | 分享数 |
| coin_count | INT | 投币数 |
| status | TINYINT | 发布状态 |
| is_recommend | TINYINT | 是否推荐 |
| is_hot | TINYINT | 是否热门 |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

---

## 表 4: tags - 标签表

### 表结构
```sql
CREATE TABLE tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '标签 ID',
    name VARCHAR(50) NOT NULL UNIQUE COMMENT '标签名称',
    description VARCHAR(200) DEFAULT NULL COMMENT '标签描述',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    created_at DATETIME NOT NULL COMMENT '创建时间'
);
```

### 索引
- `PRIMARY KEY`: id
- `UNIQUE`: name
- `INDEX`: name, sort_order

---

## 表 5: video_tags - 视频标签关联表

### 表结构
```sql
CREATE TABLE video_tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '关联 ID',
    video_id BIGINT NOT NULL COMMENT '视频 ID',
    tag_id BIGINT NOT NULL COMMENT '标签 ID',
    created_at DATETIME NOT NULL COMMENT '关联时间',
    UNIQUE KEY uk_video_tag (video_id, tag_id)
);
```

### 索引
- `PRIMARY KEY`: id
- `UNIQUE`: video_id + tag_id
- `INDEX`: video_id, tag_id

---

## 表 6: comments - 评论表

### 表结构
```sql
CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '评论 ID',
    video_id BIGINT NOT NULL COMMENT '视频 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论 ID',
    content TEXT NOT NULL COMMENT '评论内容',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    reply_count INT DEFAULT 0 COMMENT '回复数',
    status TINYINT DEFAULT 1 COMMENT '状态：0 已删除，1 正常',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间'
);
```

### 索引
- `PRIMARY KEY`: id
- `INDEX`: video_id, user_id, parent_id, created_at

### 字段说明

| 字段 | 类型 | 说明 |
|-----|-----|-|
| id | BIGINT | 主键，自增 |
| video_id | BIGINT | 所属视频 ID |
| user_id | BIGINT | 评论用户 ID |
| parent_id | BIGINT | 父评论 ID(0 为一级) |
| content | TEXT | 评论内容 |
| like_count | INT | 点赞数 |
| reply_count | INT | 回复数 |
| status | TINYINT | 状态 (0 删除，1 正常) |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

---

## 表 7: video_likes - 视频点赞表

### 表结构
```sql
CREATE TABLE video_likes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '点赞 ID',
    video_id BIGINT NOT NULL COMMENT '视频 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    created_at DATETIME NOT NULL COMMENT '点赞时间',
    UNIQUE KEY uk_video_user (video_id, user_id)
);
```

### 索引
- `PRIMARY KEY`: id
- `UNIQUE`: video_id + user_id
- `INDEX`: video_id, user_id

---

## 表 8: collections - 收藏表

### 表结构
```sql
CREATE TABLE collections (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '收藏 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    video_id BIGINT NOT NULL COMMENT '视频 ID',
    created_at DATETIME NOT NULL COMMENT '收藏时间',
    UNIQUE KEY uk_user_video (user_id, video_id)
);
```

### 索引
- `PRIMARY KEY`: id
- `UNIQUE`: user_id + video_id
- `INDEX`: user_id, video_id

---

## 表 9: watch_history - 观看历史表

### 表结构
```sql
CREATE TABLE watch_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '历史 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    video_id BIGINT NOT NULL COMMENT '视频 ID',
    progress INT DEFAULT 0 COMMENT '观看进度 (秒)',
    duration INT DEFAULT 0 COMMENT '视频总长 (秒)',
    watched_at DATETIME NOT NULL COMMENT '观看时间',
    UNIQUE KEY uk_user_video (user_id, video_id)
);
```

### 索引
- `PRIMARY KEY`: id
- `UNIQUE`: user_id + video_id
- `INDEX`: user_id, video_id, watched_at

---

## 表 10: followings - 关注关系表

### 表结构
```sql
CREATE TABLE followings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '关注 ID',
    follower_id BIGINT NOT NULL COMMENT '关注者 ID',
    followee_id BIGINT NOT NULL COMMENT '被关注者 ID',
    created_at DATETIME NOT NULL COMMENT '关注时间',
    UNIQUE KEY uk_follower_followee (follower_id, followee_id)
);
```

### 索引
- `PRIMARY KEY`: id
- `UNIQUE`: follower_id + followee_id
- `INDEX`: follower_id, followee_id

---

## 🔗 表关系

### 1: N 关系
- User 1-N Video (一个用户可以上传多个视频)
- User N-M Comment (用户可以发表多条评论)
- Category 1-N Video (一个分类包含多个视频)
- User N-M Followings (用户关注/被关注)

### N:M 关系 (通过关联表)
- Video N-M Tag (视频多标签) -> video_tags
- User N-M Likes -> video_likes
- User N-M Collections -> collections
- User N-M WatchHistory -> watch_history

---

## ⚙️ 触发器

### 自动更新时间
```sql
DELIMITER $$

CREATE TRIGGER trg_videos_update
BEFORE UPDATE ON videos
FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW();
END$$

CREATE TRIGGER trg_users_update
BEFORE UPDATE ON users
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
```

---

## 📝 测试数据

### 分类测试数据
```
- 科技 (1)
- 生活 (2)
- 娱乐 (3)
- 游戏 (4)
- 影视 (5)
- 音乐 (6)
- 动画 (7)
- 知识 (8)
```

### 标签测试数据
```
- 搞笑
- 教程
- 评测
- vlog
- 音乐
```

### 用户测试数据
```
- admin (管理员)
- testuser (测试用户)
```

### 视频测试数据
```
- 2024 年最新技术趋势
- 生活小窍门分享
- 精彩游戏集锦
```

---

## 🎯 核心查询示例

### 1. 获取热门视频（按点赞数排序）
```sql
SELECT v.*, u.username, u.nickname, c.name as category_name
FROM videos v
LEFT JOIN users u ON v.user_id = u.id
LEFT JOIN categories c ON v.category_id = c.id
WHERE v.status = 1
ORDER BY v.like_count DESC
LIMIT 20;
```

### 2. 获取用户上传的所有视频
```sql
SELECT * FROM videos
WHERE user_id = 1
ORDER BY created_at DESC;
```

### 3. 获取视频的所有评论
```sql
SELECT c.*, u.username, u.nickname, u.avatar
FROM comments c
LEFT JOIN users u ON c.user_id = u.id
WHERE c.video_id = 1 AND c.status = 1
ORDER BY c.created_at DESC;
```

### 4. 获取用户的收藏视频
```sql
SELECT v.*, u.username, c.name as category_name
FROM collections col
LEFT JOIN videos v ON col.video_id = v.id
LEFT JOIN users u ON v.user_id = u.id
LEFT JOIN categories c ON v.category_id = c.id
WHERE col.user_id = 1
ORDER BY col.created_at DESC;
```

### 5. 获取视频标签
```sql
SELECT t.*
FROM tags t
INNER JOIN video_tags vt ON t.id = vt.tag_id
WHERE vt.video_id = 1;
```

### 6. 统计用户数据
```sql
SELECT 
    u.*, 
    COUNT(v.id) as video_count,
    SUM(v.view_count) as total_views,
    SUM(v.like_count) as total_likes
FROM users u
LEFT JOIN videos v ON u.id = v.user_id
WHERE u.id = 1
GROUP BY u.id;
```

---

## 📊 数据统计

| 统计项 | 说明 |
|-----|-|
| **总表数** | 10 张 |
| **索引数** | 20+ |
| **触发器** | 4 个自动更新 |
| **测试数据** | 分类 8 个，标签 5 个，视频 3 个 |

---

## 🔄 数据迁移

### 执行 SQL 文件
```bash
mysql -u root -p joyya < backend/src/main/resources/schema_bilibili.sql
```

### 验证表创建
```sql
SHOW TABLES;
SHOW CREATE TABLE users;
SHOW CREATE TABLE videos;
SHOW CREATE TABLE categories;
```

---

**数据库结构文档完成！🎉**
