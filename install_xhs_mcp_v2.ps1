# 小红书 MCP 工具安装脚本 (Windows)
# 运行方式：powershell -ExecutionPolicy Bypass -File install_xhs_mcp_v2.ps1

Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "小红书 MCP 工具安装 - Windows 版" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host ""

# 设置安装目录
$LOCAL_BIN = "$env:USERPROFILE\.local\bin"

# 创建目录
Write-Host "1. 准备目录..." -ForegroundColor Yellow
if (-not (Test-Path $LOCAL_BIN)) {
    New-Item -ItemType Directory -Path $LOCAL_BIN | Out-Null
    Write-Host "   ✓ 创建目录：$LOCAL_BIN" -ForegroundColor Green
}

# 检查 GitHub API
Write-Host ""
Write-Host "2. 检查最新 Release 版本..." -ForegroundColor Yellow
try {
    $apiUrl = "https://api.github.com/repos/xpzouying/xiaohongshu-mcp/releases/latest"
    $release = Invoke-RestMethod -Uri $apiUrl -ErrorAction Stop
    Write-Host "   最新版本：$($release.tag_name)" -ForegroundColor Green
    Write-Host "   发布日期：$($release.published_at)" -ForegroundColor Gray
    
    # 查找 Windows 资产
    $windowsAsset = $null
    foreach ($asset in $release.assets) {
        if ($asset.name -match "windows.*amd64") {
            $windowsAsset = $asset
            Write-Host "   找到 Windows 版本：$($asset.name)" -ForegroundColor Green
            break
        }
    }
    
    if (-not $windowsAsset) {
        Write-Host "   未找到 Windows 版本" -ForegroundColor Red
        Write-Host "   可用版本:" -ForegroundColor Yellow
        $release.assets | ForEach-Object { Write-Host "     - $($_.name)" -ForegroundColor Gray }
        exit 1
    }
    
    # 下载 MCP 工具
    Write-Host ""
    Write-Host "3. 下载 MCP 工具..." -ForegroundColor Yellow
    $mcpPath = "$LOCAL_BIN\xiaohongshu-mcp.exe"
    Invoke-WebRequest -Uri $windowsAsset.browser_download_url -OutFile $mcpPath -UseBasicParsing
    Write-Host "   ✓ 下载完成：$mcpPath" -ForegroundColor Green
    
    # 查找登录工具
    $loginAsset = $null
    foreach ($asset in $release.assets) {
        if ($asset.name -match "windows.*login") {
            $loginAsset = $asset
            break
        }
    }
    
    if ($loginAsset) {
        Write-Host ""
        Write-Host "4. 下载登录工具..." -ForegroundColor Yellow
        $loginPath = "$LOCAL_BIN\xiaohongshu-login.exe"
        Invoke-WebRequest -Uri $loginAsset.browser_download_url -OutFile $loginPath -UseBasicParsing
        Write-Host "   ✓ 下载完成：$loginPath" -ForegroundColor Green
    }
    
    # 安装完成
    Write-Host ""
    Write-Host "=========================================" -ForegroundColor Cyan
    Write-Host "安装完成！" -ForegroundColor Green
    Write-Host "工具路径：$LOCAL_BIN" -ForegroundColor Cyan
    Write-Host "=========================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "下一步：运行 start-mcp.sh 启动 MCP 服务" -ForegroundColor Yellow
    Write-Host ""
    
} catch {
    Write-Host ""
    Write-Host "❌ 错误：$($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    Write-Host "请手动下载:" -ForegroundColor Yellow
    Write-Host "https://github.com/xpzouying/xiaohongshu-mcp/releases" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "手动安装步骤:" -ForegroundColor Yellow
    Write-Host "1. 从上面的 URL 下载 Windows 版本" -ForegroundColor Gray
    Write-Host "2. 解压到 %USERPROFILE%\.local\bin\" -ForegroundColor Gray
    Write-Host "3. 运行 ./start-mcp.sh 启动服务" -ForegroundColor Gray
}
