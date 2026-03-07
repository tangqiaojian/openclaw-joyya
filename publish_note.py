import urllib.request
import json

print("📝 发布小红书笔记...")

# 初始化
init = json.dumps({
    "jsonrpc":"2.0",
    "method":"initialize",
    "params":{"protocolVersion":"2024-11-05","capabilities":{},"clientInfo":{"name":"test","version":"1.0.0"}},
    "id":1
}).encode()

h = {"Content-Type":"application/json","Accept":"application/json, text/event-stream"}
req = urllib.request.Request('http://localhost:18060/mcp',data=init,headers=h)
urllib.request.urlopen(req)

# 初始化通知
notif = json.dumps({"jsonrpc":"2.0","method":"notifications/initialized","params":{},"id":None}).encode()
req = urllib.request.Request('http://localhost:18060/mcp',data=notif,headers=h)
urllib.request.urlopen(req)

print("✓ MCP 会话已初始化")

# 发布笔记
pub = json.dumps({
    "jsonrpc":"2.0",
    "method":"tools/call",
    "params":{
        "name":"publish_content",
        "arguments":{
            "title":"电商趋势：如何抓住数字化转型机遇",
            "content":"在数字经济蓬勃发展的今天，电商行业正经历着前所未有的变革。AI、大数据、物联网等新技术正在重塑零售生态。\n\n## 一、AI 赋能\n\nAI 驱动的推荐系统能精准分析用户行为，带来显著提升：\n- 转化率提升 30%-50%\n- 客单价增长\n- 复购率增强\n\n## 二、直播电商\n\n直播带货创造沉浸式购物体验，头部主播单场销售额可达数亿元。\n\n## 三、社交电商\n\n通过社交关系链实现裂变传播，熟人推荐转化率是广告的 3-5 倍。\n\n## 四、新零售\n\n线上线下深度融合，实现店仓一体，用户下单后最快 30 分钟送达。\n\n## 五、跨境电商\n\n全球市场无限可能，2025 年市场规模突破 2 万亿美元。\n\n## 结语\n\n企业只有持续创新，紧跟趋势，才能在竞争中立于不败之地。\n\n#电商 #数字化转型 #AI 电商 #新零售",
            "images":["https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"],
            "tags":["电商","数字化转型","AI 电商","新零售"],
            "is_original":False
        }
    },
    "id":2
}).encode()

req = urllib.request.Request('http://localhost:18060/mcp',data=pub,headers=h)
resp = urllib.request.urlopen(req)
result = json.loads(resp.read().decode())

print("\n🎉 发布成功!")
print(json.dumps(result, ensure_ascii=False, indent=2))
