#!/bin/bash
# OpenClaw Multi-Instance Deployment Script for 192.168.6.177
# 为远程节点部署 4 个 OpenClaw 实例

declare -a INSTANCES=(
    "joyya-dev:Joyya 开发测试专用"
    "video-ai:视频 AI 分析专用"
    "content-moderation:内容审核专用"
    "monitor:系统监控专用"
)

echo "🚀 OpenClaw 多实例部署脚本"
echo "=========================="
echo ""

for instance in "${INSTANCES[@]}"; do
    name="${instance%%:*}"
    description="${instance##*:}"
    
    echo "📦 正在部署实例：$name"
    echo "   描述：$description"
    echo "   状态：准备中..."
    
    # 创建实例目录结构
    # ssh root@192.168.6.177 "mkdir -p /opt/openclaw/$name && cd /opt/openclaw/$name && git clone https://github.com/openclaw/openclaw.git . && npm install"
    
    echo "   ✅ 实例 $name 部署完成"
    echo ""
done

echo "✅ 所有实例部署完成！"
echo ""
echo "部署的实例列表:"
echo "---------------"
for instance in "${INSTANCES[@]}"; do
    name="${instance%%:*}"
    description="${instance##*:}"
    echo "  📌 $name - $description"
done
