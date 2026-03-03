# Joyya - Bilibili Clone 项目

> 🎉 这是一个基于 Vue 3 + Spring Boot 的 Bilibili 仿站项目

## 🚀 项目简介

Joyya 是一个现代化的视频平台仿站项目，前端使用 Vue 3 + Vite + Pinia，后端使用 Spring Boot + MyBatis Plus，实现了 B 站的核心功能。

## 📁 项目结构

```
openclaw-joyya/
├── backend/                   # Spring Boot 后端
│   └── src/main/
│       ├── java/com/joyya/  # Java 源代码
│       │   ├── config/      # 配置类
│       │   ├── controller/  # REST API
│       │   ├── dto/         # 数据传输对象
│       │   ├── entity/      # 实体类
│       │   ├── repository/  # 数据访问层
│       │   └── service/     # 业务逻辑层
│       └── resources/       # 配置文件
├── frontend/                 # Vue 3 前端
│   └── src/
│       ├── components/      # 公共组件
│       ├── views/           # 页面组件
│       ├── router/          # 路由配置
│       └── stores/          # Pinia store
└── doc/                     # 文档
    ├── bilibili-dev-plan.md     # 开发方案
    ├── bilibili-sql-schema.md   # 数据库结构
    ├── development-guidelines.md # 开发规范
    └── testing-guide.md         # 测试指南
```

## 🛠️ 技术栈

### 后端
- Java 17
- Spring Boot 3.x
- MyBatis Plus
- Spring Security & JWT
- Maven
- MySQL 8.0

### 前端
- Vue 3 (Composition API)
- Vite
- Vue Router 4
- Pinia
- Axios
- CSS3 (Flexbox/Grid)

### 数据库
- MySQL 8.0
- 10 个核心数据表
- 支持分页、搜索、关联查询

## 📦 环境要求

- Java 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.6+
- Git

## 🚀 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/tangqiaojian/openclaw-joyya.git
cd openclaw-joyya
```

### 2. 数据库配置

```bash
# 创建数据库
mysql -u root -p

CREATE DATABASE joyya DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入表结构
mysql -u root -p joyya < backend/src/main/resources/schema_bilibili.sql

# 验证
mysql -u root -p joyya -e "SHOW TABLES;"
```

### 3. 启动后端

```bash
cd backend
mvn spring-boot:run
```

或者使用 IDE 直接运行 `JoyyaApplication.java`

后端将在 `http://localhost:8080` 启动

### 4. 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端将在 `http://localhost:3000` 启动

### 5. 访问应用

在浏览器中打开：http://localhost:3000

## 📚 API 接口

### 视频相关
- `GET /api/videos` - 获取视频列表
- `GET /api/videos/{id}` - 获取视频详情
- `GET /api/videos/hot` - 获取热门视频
- `GET /api/videos/latest` - 获取最新视频
- `POST /api/videos` - 上传视频
- `PUT /api/videos/{id}` - 更新视频
- `DELETE /api/videos/{id}` - 删除视频

### 分类相关
- `GET /api/categories` - 获取分类列表
- `GET /api/categories/{id}` - 获取分类详情
- `POST /api/categories` - 创建分类

### 标签相关
- `GET /api/tags` - 获取标签列表
- `GET /api/tags/search` - 搜索标签
- `POST /api/tags` - 创建标签

### 评论相关
- `GET /api/videos/{id}/comments` - 获取视频评论
- `POST /api/videos/{id}/comments` - 发表评论
- `PUT /api/comments/{id}` - 更新评论
- `DELETE /api/comments/{id}` - 删除评论

### 互动相关
- `POST /api/interactions/videos/{id}/like` - 点赞视频
- `POST /api/interactions/videos/{id}/collect` - 收藏视频
- `GET /api/interactions/users/{id}/collections` - 获取收藏列表

详细 API 文档见：[开发方案文档](doc/bilibili-dev-plan.md)

## 🎨 页面路由

- `/` - 首页（推荐视频）
- `/videos/hot` - 热门视频
- `/videos/:id` - 视频详情
- `/videos/upload` - 上传视频
- `/videos/my` - 我的页面

## 🧪 测试

### 单元测试

```bash
cd backend
mvn test
```

### 集成测试

```bash
mvn verify
```

详细测试指南见：[测试指南](doc/testing-guide.md)

## 📊 数据库结构

### 核心表（10 张）
1. `users` - 用户表
2. `videos` - 视频表
3. `categories` - 分类表
4. `tags` - 标签表
5. `video_tags` - 视频标签关联表
6. `comments` - 评论表
7. `video_likes` - 点赞表
8. `collections` - 收藏表
9. `watch_history` - 观看历史表
10. `followings` - 关注关系表

详细数据库结构：[Bilibili 数据库结构](doc/bilibili-sql-schema.md)

## 🔧 开发指南

- [开发规范](doc/development-guidelines.md)
- [测试指南](doc/testing-guide.md)
- [Bilibili 开发方案](doc/bilibili-dev-plan.md)

## 📄 License

MIT License - 自由使用、修改和分发

## 👥 贡献者

- Joyya Team

## 📮 反馈

如有问题或建议，欢迎提交 Issue 或 Pull Request。

---

**🎉 开始你的视频平台开发之旅！**
