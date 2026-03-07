# 小红书 MCP 工具安装脚本 (Windows) - 简化版

$LOCAL_BIN = "$env:USERPROFILE\.local\bin"

Write-Host "检查目录..."
if (-not (Test-Path $LOCAL_BIN)) {
    Write-Host "创建目录：$LOCAL_BIN"
    New-Item -ItemType Directory -Path $LOCAL_BIN
}

Write-Host ""
Write-Host "请访问：https://github.com/xpzouying/xiaohongshu-mcp/releases"
Write-Host ""
Write-Host "下载 Windows 版本后，放到：$LOCAL_BIN" -ForegroundColor Yellow
Write-Host ""
Write-Host "下载完成后，运行：./start-mcp.sh 启动 MCP 服务" -ForegroundColor Cyan
