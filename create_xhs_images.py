"""
生成小红书电商避坑指南图文卡片
"""
from PIL import Image, ImageDraw, ImageFont
import os

# 创建输出目录
output_dir = r"C:\Users\Administrator\.openclaw\workspace\xhs_images"
os.makedirs(output_dir, exist_ok=True)

# 中文路径转换
def to_chinese_path(path):
    return path.replace("\\", "/")

# 字体配置（尝试多个字体）
font_families = [
    "STHeiti",  # macOS
    "Hiragino Sans GB",  # macOS 中文
    "Microsoft YaHei",  # Windows 微软雅黑
    "Microsoft JhengHei",  # Windows 中文字体
    "Arial Unicode MS",
    "Noto Sans CJK SC",
]

def find_font(size):
    for family in font_families:
        try:
            return ImageFont.truetype(family, size)
        except:
            continue
    return ImageFont.load_default()

# 颜色配置
BACKGROUND_COLOR = "#FFFFFF"  # 白底
TEXT_COLOR = "#333333"  # 深灰文字
ACCENT_COLOR = "#FF2442"  # 小红书红
HIGHLIGHT_COLOR = "#FF2442"  # 高亮色
BORDER_COLOR = "#FF2442"

# 小红书图片尺寸（4:3 比例）
BASE_WIDTH = 800
BASE_HEIGHT = 600

def create_cover():
    """创建封面图"""
    img = Image.new('RGB', (BASE_WIDTH, BASE_HEIGHT), BACKGROUND_COLOR)
    draw = ImageDraw.Draw(img)
    font_large = find_font(60)
    font_medium = find_font(36)
    
    # 主标题
    title = "电商新人必看!"
    draw.text((100, 120), title, font=font_medium, fill=TEXT_COLOR)
    
    # 副标题
    subtitle = "5 个避坑指南"
    draw.text((150, 190), subtitle, font=font_medium, fill=HIGHLIGHT_COLOR)
    
    # 装饰性矩形框
    draw.rectangle([50, 80, 750, 260], outline=BORDER_COLOR, width=3)
    
    # 底部标签
    tag = "让你少走 3 年弯路!"
    draw.text((120, 280), tag, font=font_medium, fill=TEXT_COLOR)
    
    # 保存
    filepath = os.path.join(output_dir, "00_封面.jpg")
    img.save(filepath, "JPEG", quality=95)
    return filepath

def create_tip_image(tip_num, tip_title, tip_desc):
    """创建单个经验点图片"""
    img = Image.new('RGB', (BASE_WIDTH, BASE_HEIGHT), BACKGROUND_COLOR)
    draw = ImageDraw.Draw(img)
    font_large = find_font(52)
    font_medium = find_font(32)
    font_small = find_font(24)
    
    # 序号
    draw.text((60, 60), f"经验点 {tip_num}", font=font_large, fill=HIGHLIGHT_COLOR)
    
    # 标题
    y_pos = 150
    draw.text((60, y_pos), tip_title, font=font_medium, fill=TEXT_COLOR)
    y_pos += 70
    
    # 描述
    lines = wrap_text(draw, tip_desc, BASE_WIDTH - 120, font_small)
    for line in lines:
        draw.text((60, y_pos), line, font=font_small, fill=TEXT_COLOR)
        y_pos += 40
    
    # 底部装饰线
    draw.line([(60, 550), (BASE_WIDTH - 60, 550)], fill=BORDER_COLOR, width=3)
    
    filepath = os.path.join(output_dir, f"0{tip_num}_经验点{tip_num}.jpg")
    img.save(filepath, "JPEG", quality=95)
    return filepath

def wrap_text(draw, text, max_width, font):
    """文本自动换行"""
    words = text.split()
    lines = []
    current_line = ""
    
    for word in words:
        test_line = f"{current_line} {word}" if current_line else word
        bbox = draw.textbbox((0, 0), test_line, font=font)
        text_width = bbox[2] - bbox[0]
        
        if text_width <= max_width:
            current_line = test_line
        else:
            if current_line:
                lines.append(current_line)
            current_line = word
    
    if current_line:
        lines.append(current_line)
    
    return lines

def create_conclusion():
    """创建总结图"""
    img = Image.new('RGB', (BASE_WIDTH, BASE_HEIGHT), BACKGROUND_COLOR)
    draw = ImageDraw.Draw(img)
    font_large = find_font(52)
    font_medium = find_font(36)
    font_small = find_font(28)
    
    # 标题
    draw.text((100, 60), "电商之路", font=font_large, fill=HIGHLIGHT_COLOR)
    draw.text((180, 140), "不容易但值得", font=font_medium, fill=TEXT_COLOR)
    
    # 核心要点
    y_pos = 220
    points = [
        "坚持 + 学习 + 优化",
        "真的能做出成绩!",
        "有问题的评论区见 👇"
    ]
    
    for point in points:
        draw.text((150, y_pos), point, font=font_small, fill=TEXT_COLOR, align="center")
        y_pos += 80
    
    # 底部装饰
    draw.rectangle([50, 520, 750, 580], outline=BORDER_COLOR, width=3)
    
    filepath = os.path.join(output_dir, "06_总结.jpg")
    img.save(filepath, "JPEG", quality=95)
    return filepath

# 生成所有图片
print("正在生成电商避坑指南图片...")
print("="*50)

# 1. 封面
cover_path = create_cover()
print(f"✓ 封面图：{cover_path}")

# 2-5. 经验点图片
tips = [
    ("选品是王道", "不要自己觉得好卖，要看市场数据!用生意参谋、蝉妈妈等工具分析关键词搜索量和竞争度。"),
    ("供应链要靠谱", "货比三家，样品一定要检查!交期、质量、售后都要谈清楚，不然后期超级头疼。"),
    ("图片决定转化", "主图要突出卖点，详情页要有逻辑!建议前期找专业摄影师，这钱不能省。"),
    ("流量要多元化", "别指望单一平台!淘宝、京东、抖音、小红书全布局，但重点 2-3 个做精就好。"),
]

for i, (title, desc) in enumerate(tips, 1):
    tip_path = create_tip_image(i, title, desc)
    print(f"✓ 经验点{i}: {tip_path}")

# 6. 总结图
conclusion_path = create_conclusion()
print(f"✓ 总结图：{conclusion_path}")

print("="*50)
print(f"图片已保存到：{output_dir}")
print(f"共生成 {len(tips) + 2} 张图片")
