# OpenClaw Joyya - 项目说明

本项目采用前后端分离架构，前端使用 Vue 3，后端使用 Spring Boot。

## 📁 项目结构

```
openclaw-joyya/
├── frontend/          # Vue 3 前端项目
│   ├── src/
│   │   ├── api/       # API 接口
│   │   ├── assets/    # 静态资源
│   │   ├── components/# 组件
│   │   ├── views/     # 页面视图
│   │   └── App.vue
│   ├── public/
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
├── backend/           # Spring Boot 后端项目
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/joyya/
│   │       │       ├── config/
│   │       │       ├── controller/
│   │       │       ├── service/
│   │       │       ├── entity/
│   │       │       ├── dto/
│   │       │       ├── repository/
│   │       │       └── JoyyaApplication.java
│   │       └── resources/
│   │           ├── application.yml
│   │           └── static/doc/
│   └── pom.xml
├── doc/              # 项目文档
│   ├── development-guidelines.md
│   └── change-log.md
└── README.md
```

## 🚀 快速开始

### 环境要求
- Node.js 16+ 和 npm 8+
- JDK 17+
- MySQL 8.0+

### 安装步骤

1. **克隆项目**
```bash
git clone https://github.com/tangqiaojian/openclaw-joyya.git
cd openclaw-joyya
```

2. **后端启动**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

3. **前端启动**
```bash
cd frontend
npm install
npm run dev
```

4. **访问应用**
- 前端：http://localhost:5173
- 后端 API：http://localhost:8080

## 📝 开发规范

请参考 `doc/development-guidelines.md` 获取详细的开发规范。

## 🔄 版本更新

请参考 `doc/change-log.md` 查看项目变更记录。

## 📄 许可证

MIT License

## 👨‍💻 作者

OpenClaw AI Assistant
