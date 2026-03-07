import json
import http.client

print('=== 检查小红书账号状态 ===\n')

conn = http.client.HTTPConnection('localhost', 18060)

# Step 1: Initialize
init_payload = json.dumps({
    'jsonrpc': '2.0',
    'method': 'initialize',
    'params': {
        'protocolVersion': '2024-11-05',
        'capabilities': {},
        'clientInfo': {'name': 'test', 'version': '1.0.0'}
    },
    'id': 1
})

conn.request('POST', '/mcp/messages', init_payload, {
    'Content-Type': 'application/json',
    'Accept': 'application/json, text/event-stream'
})
resp = conn.getresponse()
print(f'✓ 初始化：{resp.status}')

# Step 2: Notification
notif_payload = json.dumps({
    'jsonrpc': '2.0',
    'method': 'notifications/initialized',
    'params': {},
    'id': None
})

conn.request('POST', '/mcp/messages', notif_payload, {
    'Content-Type': 'application/json',
    'Accept': 'application/json, text/event-stream'
})
resp = conn.getresponse()
print('✓ 通知发送完成')

# Step 3: Check login status
status_payload = json.dumps({
    'jsonrpc': '2.0',
    'method': 'tools/call',
    'params': {
        'name': 'check_login_status',
        'arguments': {}
    },
    'id': 999
})

conn.request('POST', '/mcp/messages', status_payload, {
    'Content-Type': 'application/json',
    'Accept': 'application/json, text/event-stream'
})
resp = conn.getresponse()
result = resp.read().decode('utf-8')

print('\n=== 登录状态 ===')
print(result)

# Step 4: Get user info
user_payload = json.dumps({
    'jsonrpc': '2.0',
    'method': 'tools/call',
    'params': {
        'name': 'user_profile',
        'arguments': {}
    },
    'id': 1000
})

conn.request('POST', '/mcp/messages', user_payload, {
    'Content-Type': 'application/json',
    'Accept': 'application/json, text/event-stream'
})
resp = conn.getresponse()
result2 = resp.read().decode('utf-8')

print('\n=== 用户信息 ===')
print(result2)

conn.close()
