import urllib.request
import json

# 第一步：初始化会话
init_request = {
    "jsonrpc": "2.0",
    "method": "initialize",
    "params": {
        "protocolVersion": "2024-11-05",
        "capabilities": {},
        "clientInfo": {"name": "test", "version": "1.0.0"}
    },
    "id": 1
}

print("初始化会话...")
data = json.dumps(init_request).encode('utf-8')
headers = {"Content-Type": "application/json", "Accept": "application/json, text/event-stream"}
req = urllib.request.Request("http://localhost:18060/mcp", data=data, headers=headers)
resp = urllib.request.urlopen(req, timeout=30)
init_result = json.loads(resp.read().decode('utf-8'))
print(f"初始化结果：{json.dumps(init_result, indent=2)}")

# 第二步：检查登录状态
status_request = {
    "jsonrpc": "2.0",
    "method": "tools/call",
    "params": {
        "name": "check_login_status",
        "arguments": {}
    },
    "id": 2
}

print("\n检查登录状态...")
data = json.dumps(status_request).encode('utf-8')
req = urllib.request.Request("http://localhost:18060/mcp", data=data, headers=headers)
try:
    resp = urllib.request.urlopen(req, timeout=60)
    result = json.loads(resp.read().decode('utf-8'))
    print(f"登录状态：{json.dumps(result, indent=2)}")
except Exception as e:
    print(f"错误：{e}")
