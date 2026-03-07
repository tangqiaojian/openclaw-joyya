"""
小红书笔记发布脚本
使用方法：需要小红书账号的 cookies
"""
import os
import json

def create_note_content():
    """创建笔记内容"""
    title = "🛒电商新人必看！5 个避坑指南让你少走 3 年弯路"
    
    content = """
姐妹们！做电商这几年踩过太多坑了😭 今天把血泪经验整理出来，给想入行的小白们！

🔥 核心经验：

1️⃣ 选品是王道
不要自己觉得好卖，要看市场数据！用生意参谋、蝉妈妈等工具分析关键词搜索量和竞争度。

2️⃣ 供应链要靠谱
货比三家，样品一定要检查！交期、质量、售后都要谈清楚，不然后期超级头疼。

3️⃣ 图片决定转化
主图要突出卖点，详情页要有逻辑！建议前期找专业摄影师，这钱不能省。

4️⃣ 流量要多元化
别指望单一平台！淘宝、京东、抖音、小红书全布局，但重点 2-3 个做精就好。

💡 额外建议：
• 前期不要压太多货，先测款
• 重视店铺评分，影响流量权重
• 定期分析数据，及时调整策略
• 多关注平台规则变化，避免违规

结语：
电商这条路不容易，但坚持 + 学习 + 优化，真的能做出成绩！有问题的评论区见👇
"""
    
    tags = ["电商", "电商创业", "创业经验", "避坑指南", "电商运营", "创业干货"]
    
    return {
        "title": title,
        "content": content,
        "tags": tags,
        "images": [
            "C:/Users/Administrator/.openclaw/media/inbound/84320a4c-33b7-4c86-a60c-5ca53af65611.png",
            "C:/Users/Administrator/.openclaw/media/inbound/cf558826-7447-4829-8663-d81208f0759a.png"
        ]
    }

if __name__ == "__main__":
    note = create_note_content()
    print("笔记内容已准备好:")
    print(f"标题：{note['title']}")
    print(f"正文：{note['content'][:100]}...")
    print(f"标签：{', '.join(note['tags'])}")
    print(f"图片数量：{len(note['images'])}")
    print("\n发布方式：需手动复制粘贴到小红书创作者平台")
