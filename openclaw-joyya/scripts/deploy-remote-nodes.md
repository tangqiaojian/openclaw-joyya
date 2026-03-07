# OpenClaw Multi-Instance Deployment Commands
# 在 192.168.6.177 上执行以下命令部署 4 个实例

# 1. 创建部署目录
ssh root@192.168.6.177 "mkdir -p /opt/openclaw/{joyya-dev,video-ai,content-moderation,monitor}"

# 2. 为每个实例克隆和安装 OpenClaw
ssh root@192.168.6.177 "cd /opt/openclaw/joyya-dev && git clone https://github.com/openclaw/openclaw.git ."
ssh root@192.168.6.177 "cd /opt/openclaw/video-ai && git clone https://github.com/openclaw/openclaw.git ."
ssh root@192.168.6.177 "cd /opt/openclaw/content-moderation && git clone https://github.com/openclaw/openclaw.git ."
ssh root@192.168.6.177 "cd /opt/openclaw/monitor && git clone https://github.com/openclaw/openclaw.git ."

# 3. 为每个实例安装依赖
ssh root@192.168.6.177 "cd /opt/openclaw/joyya-dev && npm install"
ssh root@192.168.6.177 "cd /opt/openclaw/video-ai && npm install"
ssh root@192.168.6.177 "cd /opt/openclaw/content-moderation && npm install"
ssh root@192.168.6.177 "cd /opt/openclaw/monitor && npm install"

# 4. 为每个实例创建配置文件
ssh root@192.168.6.177 "cat > /opt/openclaw/joyya-dev/.env << EOF
OPENCLAW_NODE_NAME=joyya-dev
OPENCLAW_NODE_PORT=3001
OPENCLAW_NODE_DESC=Joyya 视频平台开发测试专用
EOF"

ssh root@192.168.6.177 "cat > /opt/openclaw/video-ai/.env << EOF
OPENCLAW_NODE_NAME=video-ai
OPENCLAW_NODE_PORT=3002
OPENCLAW_NODE_DESC=视频 AI 分析专用，需要 GPU 支持
EOF"

ssh root@192.168.6.177 "cat > /opt/openclaw/content-moderation/.env << EOF
OPENCLAW_NODE_NAME=content-moderation
OPENCLAW_NODE_PORT=3003
OPENCLAW_NODE_DESC=内容审核和处理专用
EOF"

ssh root@192.168.6.177 "cat > /opt/openclaw/monitor/.env << EOF
OPENCLAW_NODE_NAME=monitor
OPENCLAW_NODE_PORT=3004
OPENCLAW_NODE_DESC=系统监控和日志收集专用
EOF"

# 5. 启动所有实例（使用 systemd 或 supervisord）
ssh root@192.168.6.177 "systemctl start openclaw-joyya-dev"
ssh root@192.168.6.177 "systemctl start openclaw-video-ai"
ssh root@192.168.6.177 "systemctl start openclaw-content-moderation"
ssh root@192.168.6.177 "systemctl start openclaw-monitor"

echo "✅ 部署完成！实例名称："
echo "  - joyya-dev (Joyya 开发测试专用)"
echo "  - video-ai (视频 AI 分析专用)"
echo "  - content-moderation (内容审核专用)"
echo "  - monitor (系统监控专用)"
