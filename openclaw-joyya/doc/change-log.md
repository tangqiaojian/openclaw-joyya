# 需求变更日志

本文件记录项目的所有变更，每次代码提交时都需更新。采用 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/) 格式。

## [未发布]

### 添加

- **测试小天账号**：新增测试专用账号（username: '测试小天', email: 'testxiaotian@joyya.com', password: '123456'）⭐
- 项目结构说明文档（doc/结构说明.md，约 8.6KB）
- 数据库初始化脚本（schema.sql）
- 单元测试指南（doc/testing-guide.md）
- 项目测试启动脚本（test-run.sh）
- 详细 Javadoc 注释（所有 Java 类）
- SQL 字段注释（users 表）
- 测试场景文档
- 安全测试指南
- 性能测试指南
- GitHub 推送说明（PUSH_INSTRUCTIONS.md）
- Bilibili 开发方案（doc/bilibili-dev-plan.md，8.0KB）
- Bilibili 数据库结构说明（doc/bilibili-sql-schema.md，10.6KB）
- Bilibili SQL 脚本（schema_bilibili.sql，10.3KB）
- Video 实体类
- Category 实体类
- Tag 实体类
- Comment 实体类
- VideoLike 实体类
- Collection 实体类
- VideoService 服务层
- CategoryService 服务层
- TagService 服务层
- CommentService 服务层
- InteractionService 服务层
- VideoRepository 仓库层
- CategoryRepository 仓库层
- TagRepository 仓库层
- CommentRepository 仓库层
- VideoLikeRepository 仓库层
- CollectionRepository 仓库层
- VideoController（14 个 API 端点）
- CategoryController（8 个 API 端点）
- TagController（11 个 API 端点）
- CommentController（9 个 API 端点）
- InteractionController（10 个 API 端点）
- VideoDTO
- CategoryDTO
- TagDTO
- CommentDTO
- UserDTO
- **API 统一请求封装**（src/api/request.js，包含 axios 拦截器和错误处理）
- **用户 API 接口**（src/api/user.js，登录/注册/用户信息管理）
- **视频 API 接口**（src/api/video.js，热门视频/最新视频/分类查询）
- **用户状态管理**（src/stores/user.js，Pinia store 统一管理用户状态）
- **全局主题系统**（src/style/theme.css，CSS 变量统一色彩/阴影/圆角/间距）
- **路由路径别名配置**（vite.config.js，配置@路径别名）
- **环境配置文件**（.env，API 基础路径配置）

### 修改

- 更新 CHANGE-LOG.md 记录最新变更
- 完善开发规范文档
- 优化 README 文档结构
- 添加中文项目说明（README_zh-CN.md）
- 创建测试启动脚本
- **重写登录页面**（src/views/Login.vue，符合项目开发规范，统一主题样式）
- **重写首页**（src/views/Home.vue，统一主题样式，优化加载和错误处理）
- **更新 Vite 配置**（vite.config.js，添加路径别名支持）
- **更新 Element Plus 版本**（集成 UI 组件库）
- **更新 API 调用方式**（改用封装的 request 实例，统一错误处理）

### 修复

- 修复代码注释完整性
- 修复字段注释缺失
- 修复文档格式问题
- 添加完整项目结构说明
- 补充各文件详细说明
- 完善文档导航结构
- **修复登录页面无法使用问题**（Element Plus 未正确注册）
- **修复样式不统一问题**（创建全局主题变量系统）
- **修复数据无法加载问题**（改用统一 API 请求封装）
- **修复路径引用问题**（配置 Vite 路径别名@）

---

## [v1.0.0] - 2026-03-03 18:43-21:25

### 添加

- 创建了前后端基础架构
- Vue 3 + Vite 前端项目结构（包含登录注册页面，炫酷界面）
- Spring Boot 3.x 后端项目结构（包含 JWT 认证、BCrypt 密码加密）
- MySQL 数据库配置
- 开发规范文档（doc/development-guidelines.md）
- 项目 README 文档
- 用户登录注册功能
- JWT 认证系统
- 密码加密存储（BCrypt）
- 炫酷 UI 界面（渐变背景、毛玻璃效果）
- 前后端分离架构
- Element Plus UI 组件库集成
- Pinia 状态管理
- 路由守卫
- 全局异常处理
- Problem Details API 标准

### 修改

- 更新了 README.md，增加了项目说明
- 添加了部署指南
- 创建了 CHANGE-LOG.md 版本日志

### 修复

- 修复了登录验证逻辑中的 Bug
- 修复了表单验证问题
- 修复了密码加密逻辑
- 修复了前后端交互接口

---

## [v1.0.1] - 2026-03-04 10:59

### 添加

- **UI 统一主题系统**：创建全局 CSS 变量（主题色、阴影、圆角、间距）
- **API 统一请求层**：实现统一的 axios 拦截器，自动处理 token 和错误
- **Pinia 状态管理**：实现用户 store，统一管理登录状态和用户信息
- **路由路径别名**：配置 @ 路径别名，简化导入路径（@/api, @/stores, @/components）
- **环境配置**：添加 .env 配置文件，支持环境变量管理
- **动态粒子背景**：登录页面添加粒子浮动动画效果
- **霓虹渐变标题**：使用 CSS 动画实现标题渐变流光效果
- **加载状态优化**：首页添加优雅的加载动画和错误提示
- **空状态设计**：无数据时显示友好的空状态界面

### 修改

- **重写登录页面**：严格按照项目开发规范重构，统一主题风格
- **重写首页**：应用统一主题，优化加载和错误处理逻辑
- **更新 Vite 配置**：添加路径别名支持，支持@符号导入
- **集成 Element Plus**：统一 UI 组件库，提升界面一致性
- **优化 API 调用**：所有请求改用封装的请求实例，统一错误处理
- **完善表单验证**：实现用户名和密码的实时验证
- **响应式设计**：优化移动端显示效果

### 修复

- **修复 Element Plus 注册问题**：在 main.js 中正确注册 Element Plus 组件库
- **修复样式不一致问题**：通过主题系统统一全局样式变量
- **修复数据加载失败**：使用封装的 API 层，统一错误处理和 token 管理
- **修复路径引用错误**：配置 Vite 路径别名@，解决@符号导入问题
- **修复表单未包裹问题**：登录表单添加 form 标签支持
- **修复后端数据格式问题**：兼容 API 返回的不同数据格式
- **修复登录按钮状态**：实现 loading 状态，避免重复点击

---

## 如何更新此日志

每次完成新的功能或修复 Bug 时，请在此处添加相应描述。

**格式说明：**
- **未发布**：当前正在开发中的功能
- **[版本号]**：已发布的版本，格式为 `v主版本。次版本.修订版本`
- **分类**：添加、修改、修复
- **日期**：YYYY-MM-DD 格式

**示例：**

```
## [v1.0.2] - 2026-03-05

### 添加

- 新增用户资料编辑功能
- 支持头像上传和修改

### 修改

- 优化了登录页面加载速度
- 改进了 API 响应时间

### 修复

- 修复了密码验证逻辑 Bug
- 修复了表单提交错误提示
```

---

**每次提交后，记得在此处更新变更记录！**
