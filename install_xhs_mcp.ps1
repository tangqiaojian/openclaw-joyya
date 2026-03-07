#!/usr/bin/env pwsh
# 安装小红书 MCP 工具到 Windows

Write-Host "检查目录..."
$LOCAL_BIN = "$env:USERPROFILE\.local\bin"
if (-not (Test-Path $LOCAL_BIN)) {
    Write-Host "创建目录：$LOCAL_BIN" -ForegroundColor Cyan
    New-Item -ItemType Directory -Path $LOCAL_BIN | Out-Null
}

# 定义下载 URL（从 GitHub releases 获取）
$RELEASES_URL = "https://api.github.com/repos/xpzouying/xiaohongshu-mcp/releases/latest"

Write-Host "获取最新版本信息..." -ForegroundColor Cyan

try {
    $latestRelease = Invoke-RestMethod -Uri $RELEASES_URL
    Write-Host "最新版本：$($latestRelease.tag_name)" -ForegroundColor Green
    
    # 查找 Windows 版本
    $asset = $latestRelease.assets | Where-Object {
        $_.name -match "windows.*amd64"
    } | Select-Object -First 1
    
    if (-not $asset) {
        Write-Host "❌ 未找到 Windows 版本" -ForegroundColor Red
        Write-Host "可用版本：" -ForegroundColor Yellow
        $latestRelease.assets | ForEach-Object { Write-Host "  - $($_.name)" }
        exit 1
    }
    
    $downloadUrl = $asset.browser_download_url
    Write-Host "下载链接：$downloadUrl" -ForegroundColor Cyan
    
    # 下载文件
    $outputFile = "$LOCAL_BIN\xiaohongshu-mcp.exe"
    Write-Host "正在下载..." -ForegroundColor Cyan
    Invoke-WebRequest -Uri $downloadUrl -OutFile $outputFile
    Write-Host "✅ 下载完成：$outputFile" -ForegroundColor Green
    
    # 检查 login 工具
    $loginAsset = $latestRelease.assets | Where-Object {
        $_.name -match "windows.*login"
    } | Select-Object -First 1
    
    if ($loginAsset) {
        $loginOutputFile = "$LOCAL_BIN\xiaohongshu-login.exe"
        Write-Host "正在下载 login 工具..." -ForegroundColor Cyan
        Invoke-WebRequest -Uri $loginAsset.browser_download_url -OutFile $loginOutputFile
        Write-Host "✅ 下载完成：$loginOutputFile" -ForegroundColor Green
    }
    
    Write-Host "" -ForegroundColor White
    Write-Host "安装完成！" -ForegroundColor Green
    Write-Host "工具路径：$LOCAL_BIN" -ForegroundColor Cyan
    
} catch {
    Write-Host "❌ 下载失败：$($_.Exception.Message)" -ForegroundColor Red
    Write-Host "请手动下载：https://github.com/xpzouying/xiaohongshu-mcp/releases" -ForegroundColor Yellow
}
