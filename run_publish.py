import urllib.request
import json

# 初始化
init = {"jsonrpc":"2.0","method":"initialize","params":{"protocolVersion":"2024-11-05","capabilities":{},"clientInfo":{"name":"test","version":"1.0.0"}},"id":1}
h = {"Content-Type":"application/json","Accept":"application/json, text/event-stream"}
urllib.request.urlopen(urllib.request.Request('http://localhost:18060/mcp',data=json.dumps(init).encode('utf-8'),headers=h))

# 通知
notif = {"jsonrpc":"2.0","method":"notifications/initialized","params":{},"id":None}
urllib.request.urlopen(urllib.request.Request('http://localhost:18060/mcp',data=json.dumps(notif).encode('utf-8'),headers=h))

print('MCP 初始化成功')

# 发布
pub = {"jsonrpc":"2.0","method":"tools/call","params":{"name":"publish_content","arguments":{"title":"电商趋势：数字化转型机遇","content":"AI、大数据重塑电商生态。AI 推荐提升转化率 30%，直播电商创造沉浸式体验，社交电商熟人推荐转化率高，新零售店仓一体 30 分钟达，跨境电商破 2 万亿美元。持续创新是关键。","images":["https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"],"tags":["电商","AI 电商","新零售"],"is_original":False}},"id":2}

try:
    resp = urllib.request.urlopen(urllib.request.Request('http://localhost:18060/mcp',data=json.dumps(pub,ensure_ascii=False).encode('utf-8'),headers=h),timeout=120)
    r = json.loads(resp.read().decode('utf-8'))
    print('发布成功')
    print(json.dumps(r.get('result',{}),ensure_ascii=False,indent=2))
except Exception as e:
    print('发布失败:',e)
