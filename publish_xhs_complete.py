import urllib.request
import json

print("🚀 开始发布小红书笔记...")

# 初始化会话
init_req = {
    "jsonrpc": "2.0",
    "method": "initialize",
    "params": {
        "protocolVersion": "2024-11-05",
        "capabilities": {},
        "clientInfo": {"name": "openclaw", "version": "1.0.0"}
    },
    "id": 1
}

init_data = json.dumps(init_req).encode('utf-8')
init_headers = {
    "Content-Type": "application/json",
    "Accept": "application/json, text/event-stream"
}

print("📌 步骤 1: 初始化 MCP 会话...")
req = urllib.request.Request("http://localhost:18060/mcp", data=init_data, headers=init_headers)
try:
    resp = urllib.request.urlopen(req, timeout=30)
    init_result = json.loads(resp.read().decode('utf-8'))
    print("✅ 会话初始化成功!")
except Exception as e:
    print(f"❌ 初始化失败：{e}")
    exit(1)

# 初始化通知
notif_req = {
    "jsonrpc": "2.0",
    "method": "notifications/initialized",
    "params": {},
    "id": None
}

print("📌 步骤 2: 发送初始化通知...")
notif_data = json.dumps(notif_req).encode('utf-8')
req = urllib.request.Request("http://localhost:18060/mcp", data=notif_data, headers=init_headers)
try:
    resp = urllib.request.urlopen(req, timeout=30)
    print("✅ 通知发送成功!")
except Exception as e:
    print(f"❌ 通知发送失败：{e}")

# 发布笔记
publish_req = {
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
    "id": 2
}

print("\n📌 步骤 3: 发布小红书笔记...")
print(f"📝 标题：{publish_req['params']['arguments']['title']}")
print(f"📝 内容：{publish_req['params']['arguments']['content']}")
print(f"🖼️  图片：{publish_req['params']['arguments']['images'][0]}")
print(f"🏷️  标签：{publish_req['params']['arguments']['tags']}")

try:
    data = json.dumps(publish_req, ensure_ascii=False).encode('utf-8')
    req = urllib.request.Request("http://localhost:18060/mcp", data=data, headers=init_headers)
    resp = urllib.request.urlopen(req, timeout=120)
    result = json.loads(resp.read().decode('utf-8'))
    
    print("\n" + "="*40)
    print("🎉 发布成功!")
    print("="*40)
    print("\n发布结果:")
    print(json.dumps(result, ensure_ascii=False, indent=2))
    
except Exception as e:
    print("\n" + "="*40)
    print("❌ 发布失败")
    print("="*40)
    print(f"\n错误信息：{e}")
