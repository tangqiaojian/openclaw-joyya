# Step 1: Initialize session
$initBody = '{"jsonrpc":"2.0","method":"initialize","params":{"protocolVersion":"2024-11-05","capabilities":{},"clientInfo":{"name":"test","version":"1.0.0"}},"id":1}'
$h = @{ 'Content-Type'='application/json';'Accept'='application/json, text/event-stream' }

Write-Host "Step 1: 初始化会话..."
$initResp = Invoke-RestMethod -Uri 'http://localhost:18060/mcp' -Method POST -Body $initBody -Headers $h
Write-Host "  会话初始化成功!"

# Step 2: Initialize notification
$notifBody = '{"jsonrpc":"2.0","method":"notifications/initialized","params":{},"id":null}'
Write-Host "Step 2: 发送初始化通知..."
Invoke-RestMethod -Uri 'http://localhost:18060/mcp' -Method POST -Body $notifBody -Headers $h | Out-Null

# Step 3: Publish content
$publishBody = '{"jsonrpc":"2.0","method":"tools/call","params":{"name":"publish_content","arguments":{"title":"AI 电商小技巧","content":"AI 电商小技巧分享","images":["https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"],"tags":["电商 AI"],"is_original":false}},"id":2}'

Write-Host "Step 3: 发布小红书笔记..."
try {
    $result = Invoke-RestMethod -Uri 'http://localhost:18060/mcp' -Method POST -Body $publishBody -Headers $h
    Write-Host "  发布成功!"
    $result.result
} catch {
    Write-Host "  发布失败：$($_.Exception.Message)"
}
