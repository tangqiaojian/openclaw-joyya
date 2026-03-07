# Step 1: Initialize session
$initBody = @'
{
  "jsonrpc": "2.0",
  "method": "initialize",
  "params": {
    "protocolVersion": "2024-11-05",
    "capabilities": {},
    "clientInfo": {"name": "openclaw", "version": "1.0.0"}
  },
  "id": 1
}
'@

$h = @{
    'Content-Type' = 'application/json'
    'Accept' = 'application/json, text/event-stream'
}

Write-Host "Initializing MCP session..."
$null = Invoke-RestMethod -Uri 'http://localhost:18060/mcp' -Method POST -Headers $h -Body $initBody
Write-Host "Session initialized!"

# Step 2: Initialize notification
$notifBody = @'
{
  "jsonrpc": "2.0",
  "method": "notifications/initialized",
  "params": {},
  "id": null
}
'@

Write-Host "Sending initialization notification..."
$null = Invoke-RestMethod -Uri 'http://localhost:18060/mcp' -Method POST -Headers $h -Body $notifBody
Write-Host "Notification sent!"

# Step 3: Publish content
$publishBody = @'
{
  "jsonrpc": "2.0",
  "method": "tools/call",
  "params": {
    "name": "publish_content",
    "arguments": {
      "title": "AI 电商小技巧",
      "content": "AI 电商小技巧分享",
      "images": ["https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"],
      "tags": ["电商 AI"],
      "is_original": false
    }
  },
  "id": 2
}
'@

Write-Host "Publishing Xiaohongshu note..."
try {
    $result = Invoke-RestMethod -Uri 'http://localhost:18060/mcp' -Method POST -Headers $h -Body $publishBody
    Write-Host "" 
    Write-Host "Success! Note published."
    Write-Host "" 
    $result.result | ConvertTo-Json -Depth 5
} catch {
    Write-Host "" 
    Write-Host "Error: $($_.Exception.Message)" 
}
