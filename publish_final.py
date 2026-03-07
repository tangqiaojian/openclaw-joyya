import urllib.request
import json

request = {
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
}

print("发送发布请求...")
data = json.dumps(request, ensure_ascii=False).encode('utf-8')
headers = {
    "Content-Type": "application/json",
    "Accept": "application/json, text/event-stream"
}

req = urllib.request.Request("http://localhost:18060/mcp", data=data, headers=headers)

try:
    resp = urllib.request.urlopen(req, timeout=120)
    result = json.loads(resp.read().decode('utf-8'))
    print("\n发布成功!")
    print("\n发布结果:")
    print(json.dumps(result, ensure_ascii=False, indent=2))
except Exception as e:
    print(f"\n错误：{e}")
