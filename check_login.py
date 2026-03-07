import urllib.request
import json

# 检查登录状态
request = {
    "jsonrpc": "2.0",
    "method": "tools/call",
    "params": {
        "name": "check_login_status",
        "arguments": {}
    },
    "id": 1
}

print("检查登录状态...")
data = json.dumps(request).encode('utf-8')
headers = {
    "Content-Type": "application/json",
    "Accept": "application/json, text/event-stream"
}
req = urllib.request.Request("http://localhost:18060/mcp", data=data, headers=headers)
try:
    resp = urllib.request.urlopen(req, timeout=60)
    result = json.loads(resp.read().decode('utf-8'))
    print("登录状态:")
    print(json.dumps(result, indent=2))
except Exception as e:
    print(f"错误：{e}")
