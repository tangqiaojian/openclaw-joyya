# GitHub 推送说明

## ⚠️ 当前网络问题

当前运行环境无法直接连接 GitHub，需要在**你自己的电脑**上执行推送。

## 🚀 推送步骤

### 方案 1: 在本地电脑上推送（推荐）⭐

**步骤 1: 打开 PowerShell 或 CMD**
在你的本地电脑上打开 PowerShell 或命令提示符。

**步骤 2: 进入项目目录**
```powershell
cd C:\Users\Administrator\.openclaw\workspace\openclaw-joyya
```

**步骤 3: 配置远程仓库**
```powershell
git remote set-url origin https://github.com/tangqiaojian/openclaw-joyya.git
```

**步骤 4: 推送代码**
```powershell
git push -u origin main
```

**步骤 5: 浏览器登录**
- 会自动打开浏览器
- 登录你的 GitHub 账号 (tangqiaojian)
- 授权访问
- 推送完成！

## 📊 推送内容

**本次提交：**
- 提交 ID: `6ddd4fb`
- 提交信息："feat: 初始化项目结构，实现登录注册功能"
- 文件数：24 个
- 代码行数：1,633 行

**包含内容：**
- Vue 3 前端项目
- Spring Boot 后端项目
- 登录注册功能
- 开发规范文档
- 项目说明文档

## 🎯 推送后验证

推送成功后，访问：
```
https://github.com/tangqiaojian/openclaw-joyya
```

你应该看到：
- 📁 frontend/ - 前端项目
- 📁 backend/ - 后端项目
- 📄 doc/ - 文档目录
- 📝 README.md - 项目说明

## 🔧 备选方案

### 方案 2: 使用 Token 推送

```powershell
cd C:\Users\Administrator\.openclaw\workspace\openclaw-joyya

# 1. 获取 GitHub Token (Settings -> Developer settings -> Personal access tokens)
# 2. 替换 YOUR_TOKEN 为你的 token
git remote set-url origin https://YOUR_TOKEN@github.com/tangqiaojian/openclaw-joyya.git

# 3. 推送
git push -u origin main
```

### 方案 3: 使用 SSH

```powershell
cd C:\Users\Administrator\.openclaw\workspace\openclaw-joyya

# 1. 配置 SSH 密钥（如果还没有）
# 2. 设置远程仓库为 SSH 地址
git remote set-url origin git@github.com:tangqiaojian/openclaw-joyya.git

# 3. 推送
git push -u origin main
```

## 📋 常见问题

### Q: 浏览器没有自动打开？
A: 确保使用 `git push -u origin main` 命令，会自动触发浏览器登录。

### Q: 提示认证失败？
A: 检查 GitHub Token 是否有 repo 权限，或重新生成新的 Token。

### Q: 推送失败？
A: 检查网络连接，或尝试方案 2/3。

### Q: 忘记密码？
A: 使用 GitHub 密码登录，或通过 Token 认证。

## ✅ 完成后

推送完成后，项目将在 GitHub 上公开可见，可以：
- 分享给他人
- 从任何位置拉取代码
- 查看项目历史
- 接受社区贡献

---

**祝你推送顺利！** 🎉
