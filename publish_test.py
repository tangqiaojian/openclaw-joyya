import sys
import urllib.request
import json
import traceback

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
    "id": 1
}

print("准备发送...")
try:
    data = json.dumps(request).encode('utf-8')
    print(f"数据长度：{len(data)} 字节")
    print(f"Data: {data[:100]}...")
    headers = {
        "Content-Type": "application/json",
        "Accept": "application/json, text/event-stream"
    }
    req = urllib.request.Request("http://localhost:18060/mcp", data=data, headers=headers)
    print(f"发送请求到：{req.full_url}")
    print(f"请求头：{req.headers}")
    resp = urllib.request.urlopen(req, timeout=60)
    print(f"响应状态码：{resp.status}")
    result = json.loads(resp.read().decode('utf-8'))
    print("发布成功!")
    print(json.dumps(result, indent=2))
except urllib.error.HTTPError as e:
    print(f"HTTP 错误：{e.code}")
    print(f"响应内容：{e.read().decode()}")
except urllib.error.URLError as e:
    print(f"URL 错误：{e.reason}")
except Exception as e:
    print(f"错误：{e}")
    traceback.print_exc()
