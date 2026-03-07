# 发送初始化请求
$body = Get-Content -Path '.\init.json' -Raw
$headers = @{
    'Content-Type' = 'application/json'
    'Accept' = 'application/json, text/event-stream'
}

Write-Host "发送初始化请求..."
try {
    $resp = Invoke-RestMethod -Uri 'http://localhost:18060/mcp' -Method POST -Headers $headers -Body $body
    Write-Host "初始化成功！"
} catch {
    Write-Host "初始化失败：$($_.Exception.Message)"
    exit 1
}

# 检查登录状态
$loginBody = '{"jsonrpc":"2.0","method":"tools/call","params":{"name":"check_login_status","arguments":{}},"id":2}'

Write-Host "`n检查登录状态..."
$resp = Invoke-RestMethod -Uri 'http://localhost:18060/mcp' -Method POST -Headers $headers -Body $loginBody
Write-Host "登录状态：$($resp.result | ConvertTo-Json -Compress)"
