# Joyya Web - B 站仿站前端

## 项目简介
基于 Vue 3 + Vite + Pinia 的 Bilibili 仿站前端项目

## 技术栈
- Vue 3 - 渐进式 JavaScript 框架
- Vite - 下一代前端构建工具
- Vue Router - 官方路由
- Pinia - 官方状态管理
- Axios - HTTP 客户端

## 目录结构
```
frontend/
├── src/
│   ├── components/          # 公共组件
│   │   ├── VideoCard.vue
│   │   └── CommentCard.vue
│   ├── views/               # 页面组件
│   │   ├── Home.vue
│   │   ├── VideoDetail.vue
│   │   ├── VideoUpload.vue
│   │   ├── UserProfile.vue
│   │   └── HotVideos.vue
│   ├── router/              # 路由配置
│   │   └── index.js
│   ├── stores/              # Pinia store
│   │   └── index.js
│   ├── App.vue              # 根组件
│   ├── main.js              # 入口文件
│   └── style.css            # 全局样式
├── public/                  # 静态资源
├── index.html               # HTML 模板
├── package.json             # 依赖配置
└── vite.config.js           # Vite 配置
```

## 快速开始

### 安装依赖
```bash
npm install
```

### 开发模式
```bash
npm run dev
```
访问 http://localhost:3000

### 生产构建
```bash
npm run build
```

### 预览生产构建
```bash
npm run preview
```

## API 配置

默认代理配置：
- 前端端口：`3000`
- 后端端口：`8080`
- API 代理：`http://localhost:8080`

## 页面路由
- `/` - 首页
- `/videos/hot` - 热门视频
- `/videos/:id` - 视频详情
- `/videos/upload` - 上传视频
- `/videos/my` - 我的页面

## 特性
- ✅ 响应式布局
- ✅ 视频卡片展示
- ✅ 评论系统
- ✅ 收藏/点赞功能
- ✅ 路由保护
- ✅ 状态管理

## 环境变量
可在 `.env` 文件中配置环境变量

## License
MIT
