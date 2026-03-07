import urllib.request
import json

headers = {"Content-Type": "application/json", "Accept": "application/json, text/event-stream"}

# 初始化会话
init_data = json.dumps({
    "jsonrpc": "2.0",
    "method": "initialize",
    "params": {"protocolVersion": "2024-11-05", "capabilities": {}, "clientInfo": {"name": "test", "version": "1.0.0"}},
    "id": 1
}).encode('utf-8')

print("初始化会话...")
req = urllib.request.Request("http://localhost:18060/mcp", data=init_data, headers=headers)
resp = urllib.request.urlopen(req, timeout=10)
print("初始化成功!")

# 检查登录状态
status_data = json.dumps({
    "jsonrpc": "2.0",
    "method": "tools/call",
    "params": {"name": "check_login_status", "arguments": {}},
    "id": 2
}).encode('utf-8')

print("\n检查登录状态...")
req = urllib.request.Request("http://localhost:18060/mcp", data=status_data, headers=headers)
try:
    resp = urllib.request.urlopen(req, timeout=60)
    result = json.loads(resp.read().decode('utf-8'))
    print(f"登录状态：{json.dumps(result, indent=2)}")
    
    # 如果登录成功，发布笔记
    if "result" in result and result["result"].get("logged_in"):
        print("\n=== 发布笔记 ===")
        publish_data = json.dumps({
            "jsonrpc": "2.0",
            "method": "tools/call",
            "params": {
                "name": "publish_content",
                "arguments": {
                    "title": "AI 电商小技巧",
                    "content": "AI 电商小技巧分享",
                    "images": ["https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"],
                    "tags": ["电商 AI"],
                    "is_original": False
                }
            },
            "id": 3
        }).encode('utf-8')
        
        req = urllib.request.Request("http://localhost:18060/mcp", data=publish_data, headers=headers)
        resp = urllib.request.urlopen(req, timeout=120)
        publish_result = json.loads(resp.read().decode('utf-8'))
        print(f"\n发布结果：{json.dumps(publish_result, indent=2)}")
    else:
        print("需要先登录小红书账号")
except Exception as e:
    print(f"错误：{e}")
