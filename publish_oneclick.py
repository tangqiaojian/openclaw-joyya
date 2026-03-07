# 小红书一键发布脚本

import urllib.request
import json

print("===== 小红书笔记一键发布 ====")

# 用户配置
imgUrl = "https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"
content = "AI、大数据重塑电商生态。AI 推荐提升转化率 30%，直播电商创造沉浸式体验，社交电商熟人推荐转化率高，新零售店仓一体 30 分钟达，跨境电商破 2 万亿美元。持续创新是关键。"
title = "电商趋势：数字化转型机遇"
tags = ["电商", "AI 电商"]

print("\n笔记内容:")
print("  标题：" + title)
print("  字数：" + str(len(content)))
print("  图片：" + imgUrl)
print("  标签：" + ", ".join(tags))
print("===== ================= ====")

# 初始化 MCP 会话
print("\n初始化 MCP 会话...")
init = {
    "jsonrpc": "2.0",
    "method": "initialize",
    "params": {
        "protocolVersion": "2024-11-05",
        "capabilities": {},
        "clientInfo": {"name": "oneclick", "version": "1.0.0"}
    },
    "id": 1
}

h = {"Content-Type": "application/json", "Accept": "application/json, text/event-stream"}
req = urllib.request.Request('http://localhost:18060/mcp', data=json.dumps(init).encode('utf-8'), headers=h)
urllib.request.urlopen(req)
print("  初始化成功")

# 初始化通知
print("\n发送初始化通知...")
notif = {
    "jsonrpc": "2.0",
    "method": "notifications/initialized",
    "params": {},
    "id": None
}
req = urllib.request.Request('http://localhost:18060/mcp', data=json.dumps(notif).encode('utf-8'), headers=h)
urllib.request.urlopen(req)
print("  通知发送成功")

# 发布笔记
print("\n发布小红书笔记...")
publish = {
    "jsonrpc": "2.0",
    "method": "tools/call",
    "params": {
        "name": "publish_content",
        "arguments": {
            "title": title,
            "content": content,
            "images": [imgUrl],
            "tags": tags,
            "is_original": False
        }
    },
    "id": 999
}

try:
    req = urllib.request.Request('http://localhost:18060/mcp', data=json.dumps(publish, ensure_ascii=False).encode('utf-8'), headers=h)
    resp = urllib.request.urlopen(req, timeout=60)
    result = json.loads(resp.read().decode('utf-8'))
    
    print("\n============ ========= ===")
    print("发布成功!")
    print("===== ================= ====")
    print("\n发布详情:")
    print(json.dumps(result, ensure_ascii=False, indent=2))
except Exception as e:
    print("\n============ ========= ===")
    print("发布失败")
    print("===== ================= ====")
    print("错误：" + str(e))
