import urllib.request
import json

# 构造发布请求
publish_request = {
    'jsonrpc': '2.0',
    'method': 'tools/call',
    'params': {
        'name': 'publish_content',
        'arguments': {
            'title': 'AI 电商小技巧',
            'content': '''🤖 AI 电商小技巧分享！

在这个数字化时代，AI 正在改变电商行业的方方面面。分享几个实用的 AI 电商技巧：

📌 **1. 智能选品**
利用 AI 数据分析工具，快速识别爆款趋势，预测市场热点，让你的商品走在市场前面。

📌 **2. 个性化推荐**
通过 AI 算法分析用户行为，为每个客户提供精准的个性化推荐，提升转化率。

📌 **3. 智能客服**
24 小时在线的 AI 客服，即时响应客户咨询，提升购物体验。

📌 **4. 内容生成**
用 AI 快速生成商品描述、营销文案，节省人力成本，提高效率。

📌 **5. 库存管理**
AI 预测销量，智能补货，避免库存积压或缺货问题。

💡 **小贴士：**
- 不要过度依赖 AI，保持人工审核
- 数据质量决定 AI 效果
- 持续优化训练模型

电商+AI=无限可能！你用过哪些 AI 电商工具？评论区告诉我~

#电商 AI #AI 电商 #黑科技 #数字化转型''',
            'images': ['https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg'],
            'tags': ['电商 AI'],
            'is_original': False
        }
    },
    'id': 1
}

print('准备发送请求...')
print('请求内容:')
print(json.dumps(publish_request, indent=2, ensure_ascii=False))

# 发送请求
try:
    data = json.dumps(publish_request).encode()
    req = urllib.request.Request('http://localhost:18060/mcp', data=data, headers={'Content-Type': 'application/json'})
    resp = urllib.request.urlopen(req, timeout=30)
    print('\n响应结果:')
    print(resp.read().decode())
except Exception as e:
    print(f'错误：{e}')
