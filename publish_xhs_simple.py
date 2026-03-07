import urllib.request
import json
import urllib.parse

# 构造发布请求
publish_request = {
    "jsonrpc": "2.0",
    "method": "tools/call",
    "params": {
        "name": "publish_content",
        "arguments": {
            "title": "AI 电商小技巧",
            "content": "🤖 AI 电商小技巧分享！\n在这个数字化时代，AI 正在改变电商行业。\n\n📌 1. 智能选品\n📌 2. 个性化推荐\n📌 3. 智能客服\n📌 4. 内容生成\n📌 5. 库存管理\n\n电商+AI=无限可能！",
            "images": ["https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"],
            "tags": ["电商 AI"],
            "is_original": False
        }
    },
    "id": 1
}

print('发送请求...')
data = json.dumps(publish_request, ensure_ascii=False).encode('utf-8')
headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json, text/event-stream'
}
req = urllib.request.Request('http://localhost:18060/mcp', data=data, headers=headers)
try:
    resp = urllib.request.urlopen(req, timeout=60)
    result = json.loads(resp.read().decode('utf-8'))
    print('✅ 发布成功!')
    print(json.dumps(result, ensure_ascii=False, indent=2))
except Exception as e:
    print(f'❌ 错误：{e}')
