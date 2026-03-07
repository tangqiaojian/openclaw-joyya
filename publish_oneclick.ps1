# 一键发布小红书笔记脚本

$ErrorActionPreference = "Continue"

# 用户配置 - 用 base64 编码避免中文问题
$content = "AI、大数据重塑电商生态。AI 推荐提升转化率 30%，直播电商创造沉浸式体验，社交电商熟人推荐转化率高，新零售店仓一体 30 分钟达，跨境电商破 2 万亿美元。持续创新是关键。"
$imgUrl = "https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"
$title = "电商趋势：数字化转型机遇"
$tag1 = "电商"
$tag2 = "AI 电商"

$McpServer = "http://localhost:18060/mcp"
$Headers = @{ "Content-Type"="application/json";"Accept"="application/json, text/event-stream" }

Write-Host "============= ====================="
Write-Host "小红书笔记一键发布工具"
Write-Host "============= ====================="

Write-Host "`n笔记内容:"
Write-Host "  标题：$title"
Write-Host "  字数：$($content.Length)"
Write-Host "  图片：$imgUrl"
Write-Host "  标签：$tag1, $tag2"
Write-Host "============= ====================="

# 初始化会话
Write-Host "`n初始化 MCP 会话..."
$init = @{
    jsonrpc = "2.0"
    method = "initialize"
    params = @{
        protocolVersion = "2024-11-05"
        capabilities = @{}
        clientInfo = @{ name = "oneclick"; version = "1.0.0" }
    }
    id = 1
}

try {
    $null = Invoke-RestMethod -Uri $McpServer -Method POST -Headers $Headers -Body ($init | ConvertTo-Json -Compress)
    Write-Host "  初始化成功"
} catch {
    Write-Host "  初始化失败：$($_.Exception.Message)"
    exit 1
}

# 初始化通知
Write-Host "`n发送初始化通知..."
$notif = @{
    jsonrpc = "2.0"
    method = "notifications/initialized"
    params = @{}
    id = $null
}

try {
    $null = Invoke-RestMethod -Uri $McpServer -Method POST -Headers $Headers -Body ($notif | ConvertTo-Json -Compress)
    Write-Host "  通知发送成功"
} catch {
    Write-Host "  通知发送失败：$($_.Exception.Message)"
    exit 1
}

# 发布笔记
Write-Host "`n发布小红书笔记..."
$publish = @{
    jsonrpc = "2.0"
    method = "tools/call"
    params = @{
        name = "publish_content"
        arguments = @{
            title = $title
            content = $content
            images = @($imgUrl)
            tags = @($tag1, $tag2)
            is_original = $false
        }
    }
    id = 999
}

try {
    $result = Invoke-RestMethod -Uri $McpServer -Method POST -Headers $Headers -Body ($publish | ConvertTo-Json -Compress)
    Write-Host "`n============= ====================="
    Write-Host "发布成功!"
    Write-Host "============= ====================="
    Write-Host "`n发布详情:"
    $result.result | ConvertTo-Json -Depth 3
} catch {
    Write-Host "`n============= ====================="
    Write-Host "发布失败"
    Write-Host "============= ====================="
    Write-Host "错误：$($_.Exception.Message)"
}
