# Download-And-Install-XHS-MCP.ps1
# XiaoHongShu MCP Tool Auto-Install Script for Windows

$LOCAL_BIN = "$env:USERPROFILE\.local\bin"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "XiaoHongShu MCP Tool - Windows Auto-Install" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# Step 1: Prepare install directory
$LOCAL_BIN = "$env:USERPROFILE\.local\bin"
Write-Host "`n[1/5] Preparing install directory..." -ForegroundColor Yellow
if (-not (Test-Path $LOCAL_BIN)) {
    New-Item -ItemType Directory -Path $LOCAL_BIN -Force | Out-Null
    Write-Host "       Created: $LOCAL_BIN" -ForegroundColor Green
} else {
    Write-Host "       Directory already exists: $LOCAL_BIN" -ForegroundColor Gray
}

# Step 2: Get latest version info
Write-Host "`n[2/5] Getting latest version info..." -ForegroundColor Yellow
$apiUrl = "https://api.github.com/repos/xpzouying/xiaohongshu-mcp/releases/latest"
try {
    $release = Invoke-RestMethod -Uri $apiUrl -TimeoutSec 15
    Write-Host "       Latest version: $($release.tag_name)" -ForegroundColor Green
    Write-Host "       Published: $($release.published_at)" -ForegroundColor Gray
} catch {
    Write-Host "       Error getting version: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "       Please download manually from GitHub" -ForegroundColor Yellow
    exit 1
}

# Step 3: Find Windows assets
Write-Host "`n[3/5] Finding Windows version..." -ForegroundColor Yellow
$windowsMcp = $null
$windowsLogin = $null

foreach ($asset in $release.assets) {
    if ($asset.name -match "xiaohongshu-mcp.*windows.*amd64") {
        $windowsMcp = $asset
        Write-Host "       Found MCP tool: $($asset.name)" -ForegroundColor Green
    }
    if ($asset.name -match "xiaohongshu-login.*windows.*amd64") {
        $windowsLogin = $asset
        Write-Host "       Found Login tool: $($asset.name)" -ForegroundColor Green
    }
}

if (-not $windowsMcp) {
    Write-Host "       Error: No Windows MCP version found" -ForegroundColor Red
    exit 1
}

# Step 4: Download files
Write-Host "`n[4/5] Downloading files..." -ForegroundColor Yellow

$mcpPath = "$LOCAL_BIN\xiaohongshu-mcp.exe"
Write-Host "       Downloading MCP tool..." -ForegroundColor Gray
try {
    Invoke-WebRequest -Uri $windowsMcp.browser_download_url -OutFile $mcpPath -UseBasicParsing -TimeoutSec 120
    Write-Host "       OK: MCP tool downloaded: $mcpPath" -ForegroundColor Green
} catch {
    Write-Host "       Error downloading MCP: $($_.Exception.Message)" -ForegroundColor Red
}

if ($windowsLogin) {
    $loginPath = "$LOCAL_BIN\xiaohongshu-login.exe"
    Write-Host "       Downloading Login tool..." -ForegroundColor Gray
    try {
        Invoke-WebRequest -Uri $windowsLogin.browser_download_url -OutFile $loginPath -UseBasicParsing -TimeoutSec 120
        Write-Host "       OK: Login tool downloaded: $loginPath" -ForegroundColor Green
    } catch {
        Write-Host "       Warning: Login tool download failed" -ForegroundColor Yellow
    }
}

# Step 5: Installation complete
Write-Host "`n[5/5] Installation complete!" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Tools installed to: $LOCAL_BIN" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

Write-Host "`nNext steps:" -ForegroundColor Yellow
Write-Host "1. Run: cd ~/.agents/skills/xiaohongshu/scripts" -ForegroundColor Gray
Write-Host "2. Run: ./start-mcp.sh" -ForegroundColor Gray
Write-Host "3. To publish: ./mcp-call.sh publish_content ..." -ForegroundColor Gray
Write-Host "`nTip: You may need to login first:" -ForegroundColor Yellow
Write-Host "     Run: ./login.sh" -ForegroundColor Gray
