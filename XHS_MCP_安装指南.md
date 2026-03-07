# XiaoHongShu MCP 工具安装指南 (Windows 版)

## 问题诊断
MCP 服务启动失败，原因是工具路径不存在:
  C:\Users\Administrator\.local\bin\xiaohongshu-mcp.exe

## 安装步骤

### 方法 1: 手动下载 (推荐)

1. **访问下载地址:**
   打开浏览器访问:
   https://github.com/xpzouying/xiaohongshu-mcp/releases

2. **下载 Windows 版本:**
   下载最新版本的 Windows amd64 文件:
   - xiaohongshu-mcp-windows-amd64.zip (MCP 服务)
   - xiaohongshu-login-windows-amd64.zip (登录工具，可选)

3. **解压并安装:**
   - 解压 `xiaohongshu-mcp-windows-amd64.zip`
   - 将解压出的 `xiaohongshu-mcp.exe` 复制到:
     `C:\Users\Administrator\.local\bin\`
   - (可选) 将 `xiaohongshu-login.exe` 也复制到同一目录

4. **启动服务:**
   打开命令行，运行:
   ```
   cd C:\Users\Administrator\.agents\skills\xiaohongshu\scripts
   .\start-mcp.sh
   ```

5. **登录账号 (首次使用):**
   如果需要登录小红书账号，运行:
   ```
   .\login.sh
   ```
   这将打开浏览器进行扫码登录

6. **发布笔记:**
   登录后，使用 MCP 发布笔记:
   ```
   .\mcp-call.sh publish_content "..."
   ```

### 方法 2: 使用 GitHub Desktop (如果方法 1 失败)

1. 使用 GitHub Desktop 或命令行克隆仓库:
   ```
   git clone https://github.com/xpzouying/xiaohongshu-mcp.git
   cd xiaohongshu-mcp
   ```

2. 在 Releases 页面下载 Windows 版本

3. 按上述步骤解压安装

### 验证安装

安装完成后，检查文件是否已存在:
```powershell
Test-Path "C:\Users\Administrator\.local\bin\xiaohongshu-mcp.exe"
```

## 常见问题

**Q: 下载速度慢？**
A: 尝试使用 GitHub 镜像或直接访问 GitHub CDN

**Q: PowerShell 脚本无法运行？**
A: 确保 Git Bash 已安装，或者使用 Windows PowerShell 直接运行命令

**Q: 服务启动失败？**
A: 检查是否有防火墙阻止，或者需要管理员权限

## 下一步

安装完成后，你可以:
- 使用 MCP 发布小红书笔记
- 自动上传图片、填写内容
- 管理账号和发布记录
