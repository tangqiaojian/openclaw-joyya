# OpenClaw Joyya - Bilibili 网站开发方案

本文档详细规划 Bilibili 风格视频分享平台的开发方案。

## 📋 项目概述

### 项目目标
开发一个类似 Bilibili 的视频分享平台，包含视频上传、播放、评论、点赞、收藏等核心功能。

### 技术栈

**后端：**
- Spring Boot 3.x
- Spring Data JPA
- MySQL 8.0
- JWT 认证
- BCrypt 密码加密

**前端：**
- Vue 3 + Vite
- Element Plus UI 组件库
- Pinia 状态管理
- Axios API 调用

---

## 📊 数据库设计

### 表结构清单

| 表名 | 说明 | 主要字段 |
|-----|-|---|
| `users` | 用户表 | id, username, email, avatar, nickname, signature |
| `categories` | 视频分类表 | id, name, description, sort_order |
| `videos` | 视频表 | id, title, description, cover, video_url, duration |
| `tags` | 标签表 | id, name, description, sort_order |
| `video_tags` | 视频标签关联表 | video_id, tag_id |
| `comments` | 评论表 | id, video_id, user_id, content, like_count |
| `video_likes` | 视频点赞表 | video_id, user_id |
| `collections` | 收藏表 | user_id, video_id |
| `watch_history` | 观看历史记录 | user_id, video_id, progress, watched_at |
| `followings` | 关注关系表 | follower_id, followee_id |

### 核心关系
- User 1-N Video（用户上传视频）
- Category 1-N Video（视频属于分类）
- Video N-M Tag（视频多标签）
- User N-M Comment（用户发表评论）

---

## 🎯 功能模块规划

### 模块 1：视频管理（Video）

**功能：**
- ✅ 视频上传
- ✅ 视频列表
- ✅ 视频详情
- ✅ 视频搜索
- ✅ 视频分类
- ✅ 热门推荐
- ✅ 最新视频
- ✅ 视频统计（播放、点赞、分享）

**API 接口：**
- `POST /api/videos` - 上传视频
- `GET /api/videos` - 获取视频列表（支持分页、搜索、分类筛选）
- `GET /api/videos/{id}` - 获取视频详情
- `PUT /api/videos/{id}` - 更新视频信息
- `DELETE /api/videos/{id}` - 删除视频
- `POST /api/videos/{id}/like` - 点赞视频
- `POST /api/videos/{id}/collect` - 收藏视频
- `GET /api/videos/hot` - 获取热门视频
- `GET /api/videos/latest` - 获取最新视频
- `GET /api/videos/search` - 搜索视频

### 模块 2：视频分类（Category）

**功能：**
- ✅ 分类列表
- ✅ 分类详情
- ✅ 分类下的视频

**API 接口：**
- `GET /api/categories` - 获取分类列表
- `GET /api/categories/{id}` - 获取分类详情
- `GET /api/categories/{id}/videos` - 获取分类下的视频

### 模块 3：标签管理（Tag）

**功能：**
- ✅ 标签列表
- ✅ 标签搜索
- ✅ 视频标签管理

**API 接口：**
- `GET /api/tags` - 获取标签列表
- `GET /api/tags/search` - 搜索标签
- `POST /api/videos/{id}/tags` - 为视频添加标签

### 模块 4：评论系统（Comment）

**功能：**
- ✅ 发表评论
- ✅ 回复评论
- ✅ 评论点赞
- ✅ 评论管理

**API 接口：**
- `POST /api/videos/{id}/comments` - 发表评论
- `GET /api/videos/{id}/comments` - 获取视频评论
- `PUT /api/comments/{id}` - 更新评论
- `DELETE /api/comments/{id}` - 删除评论
- `POST /api/comments/{id}/like` - 点赞评论

### 模块 5：用户互动（Interaction）

**功能：**
- ✅ 视频点赞
- ✅ 视频收藏
- ✅ 观看历史
- ✅ 关注关系

**API 接口：**
- `POST /api/videos/{id}/likes` - 点赞/取消点赞
- `POST /api/videos/{id}/collections` - 收藏/取消收藏
- `GET /api/users/{id}/collections` - 获取用户收藏
- `POST /api/users/{id}/followings` - 关注/取消关注
- `GET /api/users/{id}/watch-history` - 获取观看历史

### 模块 6：用户个人主页

**功能：**
- ✅ 用户基本信息
- ✅ 用户上传的视频
- ✅ 用户收藏的视频
- ✅ 用户统计数据

**API 接口：**
- `GET /api/users/{id}` - 获取用户信息
- `PUT /api/users/{id}` - 更新用户信息
- `GET /api/users/{id}/videos` - 获取用户上传的视频
- `GET /api/users/{id}/collections` - 获取用户收藏

---

## 🏗️ 项目架构

### 目录结构

```
backend/
├── src/main/java/com/joyya/
│   ├── config/                  # 配置类
│   ├── controller/              # 控制器
│   │   ├── AuthController.java
│   │   ├── VideoController.java
│   │   ├── CategoryController.java
│   │   ├── TagController.java
│   │   ├── CommentController.java
│   │   └── UserController.java
│   ├── dto/                     # 数据传输对象
│   │   ├── LoginRequest.java
│   │   ├── VideoDTO.java
│   │   ├── CategoryDTO.java
│   │   ├── CommentDTO.java
│   │   └── UserDTO.java
│   ├── entity/                  # 实体类
│   │   ├── User.java
│   │   ├── Video.java
│   │   ├── Category.java
│   │   ├── Tag.java
│   │   └── Comment.java
│   ├── repository/              # 数据访问层
│   │   ├── UserRepository.java
│   │   ├── VideoRepository.java
│   │   ├── CategoryRepository.java
│   │   ├── TagRepository.java
│   │   └── CommentRepository.java
│   └── service/                 # 业务逻辑层
│       ├── AuthService.java
│       ├── VideoService.java
│       ├── CategoryService.java
│       ├── TagService.java
│       ├── CommentService.java
│       └── InteractionService.java
├── src/main/resources/
│   ├── application.yml
│   ├── schema.sql
│   └── schema_bilibili.sql
```

### 前端目录结构

```
frontend/
├── src/
│   ├── api/                     # API 调用
│   │   ├── request.js
│   │   ├── video.js
│   │   ├── category.js
│   │   ├── comment.js
│   │   └── user.js
│   ├── assets/                  # 静态资源
│   │   └── main.css
│   ├── components/              # 组件
│   │   ├── common/              # 公共组件
│   │   │   ├── VideoCard.vue
│   │   │   ├── CommentCard.vue
│   │   │   ├── Pagination.vue
│   │   │   └── Header.vue
│   │   └── pages/               # 页面组件
│   │       ├── VideoPlayer.vue
│   │       ├── VideoUpload.vue
│   │       └── UserProfile.vue
│   ├── router/                  # 路由
│   │   └── router.js
│   ├── stores/                  # Pinia Store
│   │   ├── user.js
│   │   └── video.js
│   ├── views/                   # 页面视图
│   │   ├── Home.vue             # 首页
│   │   ├── VideoDetail.vue      # 视频详情页
│   │   ├── VideoList.vue        # 视频列表页
│   │   ├── Upload.vue           # 上传页面
│   │   ├── UserProfile.vue      # 用户主页
│   │   ├── Login.vue            # 登录页
│   │   └── Register.vue         # 注册页
│   ├── App.vue
│   └── main.js
```

---

## 📝 开发步骤

### 第一阶段：后端开发

**1. 创建数据库结构**
- ✅ 创建 users 表（已实现）
- ✅ 创建 categories 表
- ✅ 创建 videos 表
- ✅ 创建 tags 表
- ✅ 创建 video_tags 关联表
- ✅ 创建 comments 表
- ✅ 创建 video_likes 表
- ✅ 创建 collections 表
- ✅ 创建 watch_history 表
- ✅ 创建 followings 表
- ✅ 创建 trigger

**2. 创建实体类**
- ✅ User.java（已实现）
- ✅ Video.java
- ✅ Category.java
- ✅ Tag.java
- ✅ Comment.java
- ✅ VideoLike.java
- ✅ Collection.java
- ✅ WatchHistory.java

**3. 创建 Repository 层**
- ✅ UserRepository.java（已实现）
- ✅ VideoRepository.java
- ✅ CategoryRepository.java
- ✅ TagRepository.java
- ✅ CommentRepository.java
- ✅ VideoLikeRepository.java
- ✅ CollectionRepository.java
- ✅ WatchHistoryRepository.java

**4. 创建 Service 层**
- ✅ UserService.java（已实现）
- VideoService.java
- CategoryService.java
- TagService.java
- CommentService.java
- InteractionService.java

**5. 创建 Controller 层**
- ✅ AuthController.java（已实现）
- VideoController.java
- CategoryController.java
- TagController.java
- CommentController.java
- UserController.java

**6. 创建 DTO**
- LoginRequest.java（已实现）
- VideoDTO.java
- CategoryDTO.java
- TagDTO.java
- CommentDTO.java
- UserDTO.java

### 第二阶段：前端开发

**1. 基础组件**
- VideoCard - 视频卡片
- CommentCard - 评论卡片
- Pagination - 分页组件
- Header - 顶部导航
- Footer - 底部导航
- SearchBar - 搜索框

**2. 核心页面**
- VideoPlayer - 视频播放器
- VideoDetail - 视频详情
- VideoList - 视频列表
- Upload - 视频上传
- UserProfile - 用户主页
- Home - 首页

**3. 状态管理**
- userStore - 用户状态
- videoStore - 视频状态
- commentStore - 评论状态

---

## 🎨 UI 设计

### 首页设计
- 顶部导航栏（Logo、搜索、上传、用户头像）
- 分类导航
- 推荐视频轮播
- 热门视频列表
- 最新视频列表

### 视频详情页设计
- 播放器区域
- 视频信息（标题、UP 主、播放量等）
- 操作按钮（点赞、收藏、分享）
- 评论区
- 相关推荐

### 用户主页设计
- 用户信息卡片（头像、昵称、统计数据）
- 视频列表（上传、收藏）
- 个人信息编辑

---

## 🧪 测试方案

### 单元测试
- Service 层测试（使用 Mockito）
- Repository 层测试（使用 Testcontainers）
- Controller 层测试（使用 MockMvc）
- 前端组件测试（使用 Vitest）

### 集成测试
- API 接口测试（使用 Postman/Insomnia）
- 完整流程测试（登录→上传→播放→评论）

### E2E 测试
- 使用 Cypress 进行端到端测试

---

## 📦 部署方案

### 开发环境
- MySQL 8.0
- Node.js 18

### 生产环境
- Docker 容器化
- Nginx 反向代理
- MySQL 数据库
- 文件存储服务（阿里云 OSS/MinIO）

### 环境变量
```yaml
# .env
db_url=jdbc:mysql://localhost:3306/joyya
db_username=root
db_password=your_password
jwt_secret=your-secret-key
jwt_expiration=86400000
file_storage_url=https://oss.example.com
```

---

## 🚀 快速开始

### 1. 初始化数据库
```bash
mysql -u root -p < backend/src/main/resources/schema_bilibili.sql
```

### 2. 启动后端
```bash
cd backend
mvn spring-boot:run
```

### 3. 启动前端
```bash
cd frontend
npm install
npm run dev
```

---

## 📊 开发计划

### 阶段一：基础功能（预计 2 天）
- [ ] 完成所有实体类
- [ ] 完成 Repository 层
- [ ] 完成 Service 层
- [ ] 完成 Controller 层

### 阶段二：前端基础（预计 2 天）
- [ ] 基础组件开发
- [ ] 页面布局
- [ ] API 对接

### 阶段三：功能完善（预计 2 天）
- [ ] 视频上传功能
- [ ] 评论区功能
- [ ] 点赞收藏功能

### 阶段四：测试优化（预计 1 天）
- [ ] 单元测试
- [ ] 性能优化
- [ ] 文档编写

---

## 📚 相关文档

- [开发规范](./development-guidelines.md)
- [测试指南](./testing-guide.md)
- [项目结构说明](./结构说明.md)
- [变更日志](./CHANGE-LOG.md)

---

**开始开发！🚀**
