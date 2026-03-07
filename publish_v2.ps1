# 直接定义 JSON 字符串
$body = '{"jsonrpc":"2.0","method":"tools/call","params":{"name":"publish_content","arguments":{"title":"AI 电商小技巧","content":"AI 电商小技巧分享","images":["https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"],"tags":["电商 AI"],"is_original":false}},"id":3}'

$headers = @{ 'Content-Type'='application/json';'Accept'='application/json, text/event-stream' }
Write-Host "请求内容长度：$($body.Length)"
try {
    $result = Invoke-RestMethod -Uri 'http://localhost:18060/mcp' -Method POST -Headers $headers -Body $body
    Write-Host "发布成功!"
    $result.result
} catch {
    Write-Host "错误：$($_.Exception.Message)"
}
