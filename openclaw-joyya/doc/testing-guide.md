# OpenClaw Joyya - 测试指南

本指南提供项目测试的详细步骤和最佳实践。

## 📋 测试清单

### 1. 环境检查

#### 后端环境
- [ ] JDK 17+ 已安装
- [ ] Maven 3.8+ 已安装
- [ ] MySQL 8.0+ 已安装（或使用 H2）
- [ ] Git 已安装

#### 前端环境
- [ ] Node.js 16+ 已安装
- [ ] npm 8+ 已安装

#### 检查命令
```bash
java -version
mvn -version
node -v
npm -v
```

### 2. 数据库配置

#### 方案 1: 使用 MySQL

```sql
CREATE DATABASE joyya DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 导入 schema.sql
mysql -u root -p joyya < backend/src/main/resources/schema.sql
```

#### 方案 2: 使用 H2（开发测试）

修改 `application.yml`：
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

### 3. 后端测试

#### 编译项目
```bash
cd backend
mvn clean compile
```

#### 运行测试
```bash
mvn test
```

#### 启动服务
```bash
mvn spring-boot:run
```

#### 验证服务
```bash
# 检查健康状态
curl http://localhost:8080/actuator/health

# 测试登录接口
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'

# 测试注册接口
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"123456"}'
```

### 4. 前端测试

#### 安装依赖
```bash
cd frontend
npm install
```

#### 启动开发服务器
```bash
npm run dev
```

#### 访问测试
```bash
# 打开浏览器
open http://localhost:5173

# 或使用 curl 测试
curl -I http://localhost:5173
```

### 5. 集成测试

#### 测试场景 1: 用户登录
1. 访问 http://localhost:5173
2. 输入用户名：admin
3. 输入密码：123456
4. 点击"登录"按钮
5. 验证：
   - 登录成功提示
   - Token 保存
   - 页面跳转（如果实现）

#### 测试场景 2: 用户注册
1. 点击"立即注册"
2. 填写用户名、邮箱、密码
3. 确认密码
4. 验证：
   - 表单验证通过
   - 注册成功提示
   - 自动跳转到登录页

#### 测试场景 3: 表单验证
1. 留空用户名，点击登录
2. 验证错误提示："请输入用户名"
3. 用户名少于 3 个字符
4. 验证错误提示："用户名长度在 3 到 20 个字符"
5. 密码少于 6 个字符
6. 验证错误提示："密码长度在 6 到 20 个字符"
7. 两次密码不一致
8. 验证错误提示："两次输入的密码不一致"

#### 测试场景 4: 界面交互
1. 页面加载动画
2. 渐变背景显示
3. 毛玻璃效果
4. 响应式布局
5. 表单输入焦点
6. 密码显示/隐藏切换

### 6. 性能测试

#### 后端性能
```bash
# 使用 Apache Bench 进行负载测试
ab -n 100 -c 10 \
  -H "Content-Type: application/json" \
  -p login.json \
  http://localhost:8080/api/auth/login
```

#### 前端性能
```bash
# 使用 Lighthouse 进行性能分析
npm install -g @lhci/cli
lhci autorun
```

### 7. 安全测试

#### 密码加密验证
```bash
# 验证密码是否 BCrypt 加密
mysql -u root -p joyya
SELECT username, password FROM users WHERE username='admin';
# 结果：密码应为 BCrypt 加密格式（$2a$开头）
```

#### SQL 注入测试
```bash
# 尝试 SQL 注入
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin'--","password":"123456"}'
# 应该返回错误而不是执行注入
```

#### CORS 测试
```bash
# 从不同域名测试跨域请求
curl -H "Origin: http://different-domain.com" \
  http://localhost:8080/api/auth/login
# 响应应包含正确的 CORS 头
```

### 8. 错误处理测试

#### 后端错误处理
```bash
# 测试无效参数
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"invalid":"data"}'

# 测试网络超时（模拟）
# 添加延迟到后端处理
```

#### 前端错误处理
1. 模拟网络错误
2. 验证错误提示显示
3. 验证错误日志记录
4. 验证用户友好提示

### 9. 持续集成测试

#### GitHub Actions 配置
创建 `.github/workflows/ci.yml`：
```yaml
name: CI/CD

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  backend-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Maven
        run: |
          cd backend
          mvn clean install

  frontend-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Setup Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '18'
      - name: Install dependencies
        run: |
          cd frontend
          npm install
      - name: Run tests
        run: |
          npm run test
```

### 10. 测试报告生成

#### 后端测试报告
```bash
mvn test -Dmaven.reportAllFailures=true
mvn site
```

#### 前端测试报告
```bash
npm run test -- --coverage
```

---

## 📝 测试检查清单

### 功能测试
- [ ] 用户登录成功
- [ ] 用户登录失败（错误密码）
- [ ] 用户注册成功
- [ ] 用户注册失败（用户名已存在）
- [ ] 表单验证正确
- [ ] Token 正确生成和存储
- [ ] 密码正确加密存储

### 性能测试
- [ ] 页面加载时间 < 2 秒
- [ ] API 响应时间 < 500ms
- [ ] 并发处理正常

### 安全测试
- [ ] 密码 BCrypt 加密
- [ ] SQL 注入防护
- [ ] CORS 正确配置
- [ ] JWT Token 有效
- [ ] 敏感信息不泄露

### UI/UX 测试
- [ ] 界面美观
- [ ] 动画流畅
- [ ] 响应式设计
- [ ] 交互友好

### 兼容性测试
- [ ] Chrome 浏览器
- [ ] Firefox 浏览器
- [ ] Safari 浏览器
- [ ] Edge 浏览器
- [ ] 移动端浏览器

---

## 🐛 常见问题

### Q: 后端启动失败
A: 检查端口 8080 是否被占用，检查数据库连接。

### Q: 前端无法访问
A: 检查 Vite 端口配置，确认 npm install 成功。

### Q: 跨域错误
A: 检查 CORS 配置，确保允许所有来源或配置具体域名。

### Q: 数据库连接失败
A: 检查 application.yml 配置，确认 MySQL/H2 运行正常。

---

## 📚 参考资源

- [Spring Boot Testing](https://spring.io/projects/spring-boot#learn)
- [Vue Test Utils](https://test-utils.vuejs.org/)
- [Element Plus Testing](https://element-plus.org/zh-CN/guide/test.html)

---

**祝测试顺利！** 🎉
