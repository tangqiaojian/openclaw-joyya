$ErrorActionPreference = 'Continue'
$McpUrl = 'http://localhost:18060/mcp'
$h = @{ 'Content-Type'='application/json';'Accept'='application/json, text/event-stream' }

Write-Host "Initializing MCP session..."
$init = @{
    jsonrpc = "2.0"
    method = "initialize"
    params = @{
        protocolVersion = "2024-11-05"
        capabilities = @{}
        clientInfo = @{ name = "test"; version = "1.0.0" }
    }
    id = 1
}

$body = $init | ConvertTo-Json -Compress
try {
    Invoke-RestMethod -Uri $McpUrl -Method POST -Headers $h -Body $body -ContentType 'application/json' | Out-Null
    Write-Host "Session initialized"
} catch {
    Write-Host "Init failed: $_"
    exit 1
}

Write-Host "Sending notification..."
$notif = @{
    jsonrpc = "2.0"
    method = "notifications/initialized"
    params = @{}
    id = $null
}

$body = $notif | ConvertTo-Json -Compress
try {
    Invoke-RestMethod -Uri $McpUrl -Method POST -Headers $h -Body $body -ContentType 'application/json' | Out-Null
    Write-Host "Notification sent"
} catch {
    Write-Host "Notify failed: $_"
    exit 1
}

Write-Host "Publishing note with English tags..."

# Define content
$note_title = "电商趋势：数字化转型机遇"
$note_content = "在数字经济蓬勃发展的今天，电商行业正经历着前所未有的变革。AI、大数据、物联网重塑零售生态。AI 推荐提升转化率 30%，直播电商创造沉浸式体验，社交电商熟人推荐转化率高，新零售店仓一体 30 分钟达，跨境电商破 2 万亿美元。持续创新是关键。"
$img_url = "https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"

# Build tags array with English names
tags = @("e-commerce", "AI", "new-retail")

$pub = @{
    jsonrpc = "2.0"
    method = "tools/call"
    params = @{
        name = "publish_content"
        arguments = @{
            title = $note_title
            content = $note_content
            images = @($img_url)
            tags = tags
            is_original = $false
        }
    }
    id = 2
}

$body = $pub | ConvertTo-Json -Compress
try {
    $result = Invoke-RestMethod -Uri $McpUrl -Method POST -Headers $h -Body $body -ContentType 'application/json'
    Write-Host ""
    Write-Host "===== SUCCESS! ====="
    Write-Host ""
    $result.result | ConvertTo-Json -Depth 3
} catch {
    Write-Host ""
    Write-Host "===== FAILED ====="
    Write-Host "Error: $_"
}
