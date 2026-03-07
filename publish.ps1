$body = Get-Content -Path '.\publish_body.json' -Raw
$headers = @{ 'Content-Type'='application/json';'Accept'='application/json, text/event-stream' }
try {
    $result = Invoke-RestMethod -Uri 'http://localhost:18060/mcp' -Method POST -Headers $headers -Body $body
    Write-Output "成功!"
    $result.result
} catch {
    Write-Output "错误：$($_.Exception.Message)"
}
