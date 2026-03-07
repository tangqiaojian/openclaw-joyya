import urllib.request
import json

# 初始化 MCP
init = {"jsonrpc":"2.0","method":"initialize","params":{"protocolVersion":"2024-11-05","capabilities":{},"clientInfo":{"name":"test","version":"1.0.0"}},"id":1}
h = {"Content-Type":"application/json","Accept":"application/json, text/event-stream"}
urllib.request.urlopen(urllib.request.Request('http://localhost:18060/mcp',data=json.dumps(init).encode('utf-8'),headers=h))

# 通知
notif = {"jsonrpc":"2.0","method":"notifications/initialized","params":{},"id":None}
urllib.request.urlopen(urllib.request.Request('http://localhost:18060/mcp',data=json.dumps(notif).encode('utf-8'),headers=h))

print("MCP 会话初始化成功")

# 发布标题=电商趋势：数字化转型机遇
# 内容=AI 电商六大趋势
# 1.AI 推荐 2.直播电商 3.社交电商 4.新零售 5.跨境电商 6.企业布局
# 内容=在数字经济蓬勃发展的今天，电商行业正经历着前所未有的变革。AI、大数据、物联网重塑零售生态。

# AI 赋能
# 转化率提升 30%-50%，客单价增长，复购率增强

# 直播电商
# 直播创造沉浸式购物体验，单场销售额可达数亿元

# 社交电商
# 熟人推荐转化率是广告的 3-5 倍

# 新零售
# 店仓一体，30 分钟送达

# 跨境电商
# 市场规模突破 2 万亿美元

# 结语：持续创新是关键

c = "在数字经济蓬勃发展的今天，电商行业正经历着前所未有的变革。人工智能、大数据、物联网重塑零售生态。\n\nAI 赋能：转化率提升 30%-50%，客单价增长，复购率增强。\n\n直播电商：直播创造沉浸式购物体验，单场销售额可达数亿元。\n\n社交电商：熟人推荐转化率是广告的 3-5 倍。\n\n新零售：店仓一体，30 分钟送达。\n\n跨境电商：市场规模突破 2 万亿美元。\n\n结语：持续创新是关键。\n\n#电商 #AI 电商 #新零售"

t = "电商趋势：数字化转型机遇"
img = "https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg"
tags = ["电商", "AI 电商", "新零售"]

pub = {"jsonrpc":"2.0","method":"tools/call","params":{"name":"publish_content","arguments":{"title":t,"content":c,"images":[img],"tags":tags,"is_original":False}},"id":2}

try:
    resp = urllib.request.urlopen(urllib.request.Request('http://localhost:18060/mcp',data=json.dumps(pub,ensure_ascii=False).encode('utf-8'),headers=h),timeout=120)
    result = json.loads(resp.read().decode('utf-8'))
    print("发布成功！")
    print(json.dumps(result.get('result',{}),ensure_ascii=False,indent=2))
except Exception as e:
    print("发布失败:",e)
