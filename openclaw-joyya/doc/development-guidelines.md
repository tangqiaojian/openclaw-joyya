# 开发规范

本项目遵循统一的开发规范，确保代码质量和团队协作效率。

## 一、Java 后端开发规范

### 1. 框架基础
- 使用 Spring Boot 3.x 作为框架基础
- 使用 Maven 进行依赖管理和构建
- 使用 Java 17+ 作为最低版本要求

### 2. 命名规则
- 类名使用 PascalCase（大驼峰）：`UserController`, `OrderService`
- 方法名使用 camelCase（小驼峰）：`getUserInfo`, `createOrder`
- 变量名使用 camelCase：`userId`, `orderList`
- 常量使用 UPPER_CASE：`MAX_RETRY_COUNT`

### 3. 架构分层
- **Controller 层**：处理 HTTP 请求，参数校验，调用 Service
- **Service 层**：业务逻辑处理，事务管理
- **Repository 层**：数据访问，JPA 操作
- **DTO**：数据传输对象，与 Entity 分离
- **Entity**：实体类，对应数据库表

### 4. DTO 与 Entity 分离
```java
// ❌ 禁止直接返回 Entity 对象
@RestController
public class UserController {
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}

// ✅ 正确做法：使用 DTO
@RestController
public class UserController {
    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return UserDTO.from(user);
    }
}
```

### 5. 异常处理
- 使用 `@ControllerAdvice` 进行全局异常捕获
- 返回 Problem Details 格式（RFC 7807）
- 定义自定义异常类

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> handleException(Exception e) {
        ProblemDetails problemDetails = ProblemDetails.builder()
            .type("https://example.com/errors/internal-error")
            .title("Internal Server Error")
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(problemDetails);
    }
}
```

### 6. 数据库规范
- 使用 JPA/Hibernate 进行 ORM 映射
- 表名使用复数形式：`users`, `orders`
- 字段名使用 snake_case：`user_id`, `created_at`
- 每个表必须有主键（ID）
- 添加通用字段：`created_at`, `updated_at`

### 7. 安全规范
- 密码使用 BCrypt 加密存储
- 使用 JWT 进行身份认证
- 接口权限通过 Spring Security 控制
- SQL 注入通过 JPA 预编译防止

## 二、Vue 前端开发规范

### 1. 技术栈
- 使用 Vue 3 Composition API
- 使用 `<script setup>` 语法糖
- 构建工具：Vite
- 状态管理：Pinia（不使用 Vuex）
- UI 框架：Element Plus 或自定义

### 2. 组件命名
- 所有组件命名采用 PascalCase（大驼峰）
- 文件名使用 PascalCase：`UserProfile.vue`, `LoginForm.vue`
- 组件名与文件名保持一致

### 3. API 调用规范
- 所有 API 调用必须通过封装的 axios 实例
- 配置请求/响应拦截器
- 统一错误处理

```javascript
// api/request.js
import axios from 'axios'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// 响应拦截器
request.interceptors.response.use(
  response => response.data,
  error => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

export default request
```

### 4. 状态管理
- 使用 Pinia 进行状态管理
- 每个功能模块有独立的 store
- 使用 composition API 定义 store

```javascript
// stores/user.js
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(null)
  const token = ref(localStorage.getItem('token'))

  const isLoggedIn = computed(() => !!token.value)

  async function login(credentials) {
    // 登录逻辑
  }

  return { userInfo, token, isLoggedIn, login }
})
```

### 5. 组件通信
- 父子组件：`props` 和 `emits`
- 跨级组件：提供/注入（provide/inject）
- 全局状态：Pinia store

### 6. 代码风格
- 使用 Prettier 统一代码格式
- 使用 ESLint 进行代码检查
- 单文件组件结构顺序：`<script setup>` → `<template>` → `<style>`

### 7. 性能优化
- 懒加载路由
- 虚拟滚动长列表
- 防抖/节流处理高频事件
- 使用 `v-memo` 优化静态内容

### 8. 组件复用
- 可复用组件放入 `components/common/`
- 页面组件放入 `components/pages/`
- 使用 Composition API 提取 composable 函数

```javascript
// composables/useForm.js
export function useForm(initialValues) {
  const values = ref({ ...initialValues })
  const errors = ref({})

  function validate() {
    // 验证逻辑
  }

  return { values, errors, validate }
}
```

## 三、Git 提交规范

### 1. 提交类型
- `feat`: 新功能
- `fix`: Bug 修复
- `docs`: 文档修改
- `style`: 代码格式（不影响代码运行的变动）
- `refactor`: 代码重构（既不是新增功能，也不是修复 bug）
- `chore`: 构建过程或辅助工具的变动

### 2. 提交格式
```
<type>(<scope>): <subject>

<body>

<footer>
```

**示例：**
```
feat(auth): 实现用户登录功能

- 新增登录页面
- 实现 JWT 认证
- 添加登录验证

Closes #123
```

## 四、文档规范

### 1. 代码注释
- 类/方法必须有注释（Java）
- 复杂逻辑必须有注释（JS/TS）
- 使用 JSDoc/JavaDoc 格式

### 2. API 文档
- 使用 Swagger/OpenAPI 生成 API 文档
- 所有接口必须有文档说明

### 3. 项目文档
- README.md：项目说明
- 每个模块有 README.md
- 更新 doc/change-log.md 记录变更

## 五、测试规范

### 1. 单元测试
- Controller 层：使用 @SpringBootTest + MockMvc
- Service 层：使用 JUnit + Mockito
- 前端组件：使用 Vitest + Vue Test Utils

### 2. 测试覆盖
- 核心业务逻辑必须有测试
- 测试覆盖率达到 80% 以上
- 所有新增代码必须有对应测试

### 3. 测试命名
- 测试方法名：`should_预期结果_when_测试条件`
- 示例：`should_return_404_when_user_not_found`

## 六、部署规范

### 1. 环境变量
- 使用 `.env` 文件管理环境变量
- 不同环境使用不同配置文件
- 敏感信息不提交到 Git

### 2. Docker 化
- 后端：使用多阶段构建 Dockerfile
- 前端：使用 Nginx 容器部署

### 3. CI/CD
- GitHub Actions 自动构建
- 自动运行测试
- 自动部署到生产环境

---

**遵守以上规范，确保代码质量，提升团队协作效率！**
