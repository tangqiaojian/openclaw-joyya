====================================================================
小红书 MCP 工具安装指南 (Windows 版)
====================================================================

问题诊断:
---------
当前 MCP 服务启动失败，原因是工具路径不存在:
  /c/Users/Administrator/.local/bin/xiaohongshu-mcp

安装步骤:
---------

1. 访问下载地址:
   https://github.com/xpzouying/xiaohongshu-mcp/releases

2. 下载 Windows 版本（amd64）:
   - xiaohongshu-mcp-windows-amd64.zip
   - xiaohongshu-login-windows-amd64.zip (可选)

3. 解压并安装:
   - 创建目录：C:\Users\Administrator\.local\bin
   - 将下载的文件解压到该目录

4. 启动服务:
   - 运行：cd C:\Users\Administrator\.agents\skills\xiaohongshu\scripts
   - 执行：./start-mcp.sh

5. 发布笔记:
   - 运行：./mcp-call.sh publish_content "..."

注意:
-----
- 确保 ~/.local/bin 在系统 PATH 环境变量中（推荐）
- 需要先登录小红书账号才能使用 MCP 服务
- 如需登录，先运行：./login.sh

====================================================================
