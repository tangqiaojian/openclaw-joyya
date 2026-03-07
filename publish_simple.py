import urllib.request
import json

request = {
    'jsonrpc': '2.0',
    'method': 'tools/call',
    'params': {
        'name': 'publish_content',
        'arguments': {
            'title': 'AI 电商小技巧',
            'content': 'AI 电商小技巧分享',
            'images': ['https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg'],
            'tags': ['电商 AI'],
            'is_original': False
        }
    },
    'id': 1
}

print('准备发送...')
data = json.dumps(request).encode()
headers = {'Content-Type': 'application/json', 'Accept': 'application/json, text/event-stream'}
req = urllib.request.Request('http://localhost:18060/mcp', data=data, headers=headers)
try:
    resp = urllib.request.urlopen(req, timeout=60)
    result = json.loads(resp.read().decode())
    print('发布成功!')
    print(json.dumps(result, indent=2))
except Exception as e:
    print(f'错误：{e}')
