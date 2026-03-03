# OpenClaw Joyya - 前后端分离登录注册系统

这是一个基于 Vue 3 和 Spring Boot 的前后端分离项目，实现了炫酷的登录注册功能。

## 🚀 快速开始

### 环境要求
- Node.js 16+ 和 npm 8+
- JDK 17+
- MySQL 8.0+（可选，可用 H2 测试）

### 安装步骤

#### 1. 后端启动

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

后端将在 http://localhost:8080 启动。

#### 2. 前端启动

```bash
cd frontend
npm install
npm run dev
```

前端将在 http://localhost:5173 启动。

### 数据库配置

修改 `backend/src/main/resources/application.yml` 中的数据库配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/joyya
    username: root
    password: your_password
```

或使用 H2 数据库（无需安装）：

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:joyya
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
```

## 📱 功能特性

### 前端功能
- ✅ 炫酷登录页面（渐变背景、毛玻璃效果）
- ✅ 注册页面（密码确认、表单验证）
- ✅ Element Plus UI 组件库
- ✅ Pinia 状态管理
- ✅ Vue Router 路由管理
- ✅ 表单验证

### 后端功能
- ✅ JWT 令牌认证系统
- ✅ BCrypt 密码加密存储
- ✅ Spring Security 安全配置
- ✅ 全局异常处理
- ✅ Problem Details API 标准
- ✅ CORS 跨域支持

## 🔧 开发规范

请查看 `doc/development-guidelines.md` 获取详细的开发规范。

## 📊 项目结构

```
openclaw-joyya/
├── frontend/           # Vue 3 前端项目
│   ├── src/
│   │   ├── assets/    # 静态资源
│   │   ├── components/# 组件
│   │   ├── router/    # 路由配置
│   │   ├── views/     # 页面视图
│   │   ├── App.vue    # 根组件
│   │   └── main.js    # 入口文件
│   ├── index.html     # HTML 模板
│   ├── vite.config.js # Vite 配置
│   └── package.json   # 依赖配置
├── backend/            # Spring Boot 后端项目
│   ├── src/main/
│   │   ├── java/com/joyya/
│   │   │   ├── config/    # 配置类
│   │   │   ├── controller/# 控制器
│   │   │   ├── dto/       # 数据传输对象
│   │   │   ├── entity/    # 实体类
│   │   │   ├── repository/# 数据访问层
│   │   │   └── service/   # 业务逻辑层
│   │   └── resources/     # 配置文件
│   └── pom.xml    # Maven 配置
├── doc/           # 项目文档
│   ├── development-guidelines.md
│   └── change-log.md
└── README_zh-CN.md # 中文说明
```

## 🔒 安全特性

- 密码 BCrypt 加密
- JWT 令牌认证
- Spring Security 防护
- CORS 跨域控制
- SQL 注入防护（JPA 预编译）

## 🎨 界面特色

- **渐变背景**：紫色渐变背景
- **毛玻璃效果**：登录框采用毛玻璃效果
- **流畅动画**：页面切换动画
- **响应式设计**：适配不同屏幕尺寸

## 🧪 测试

### 后端测试

```bash
cd backend
mvn test
```

### 前端测试

```bash
cd frontend
npm run test
```

## 📝 API 接口

### 登录接口

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

**响应：**

```json
{
  "success": true,
  "message": "登录成功",
  "token": "mock-jwt-token-1",
  "data": {
    "id": 1,
    "username": "admin",
    "email": "admin@example.com"
  }
}
```

### 注册接口

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "email": "user@example.com",
  "password": "123456"
}
```

**响应：**

```json
{
  "success": true,
  "message": "注册成功"
}
```

## 📄 许可证

MIT License

## 👨‍💻 技术支持

- OpenClaw AI Assistant
- 遵循项目开发规范

## 🔄 版本更新

查看 `doc/change-log.md` 了解项目变更记录。

---

**开始使用吧！🎉**
