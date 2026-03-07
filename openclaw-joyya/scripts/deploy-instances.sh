#!/bin/bash
# OpenClaw Docker Deployment Script
# 为远程节点 192.168.6.177 部署 4 个 OpenClaw 实例

set -e

REMOTE_USER="hxzh"
REMOTE_HOST="192.168.6.177"
OLLAMA_HOST="192.168.6.62"

# 实例名称列表
INSTANCES=(
    "joyya-dev:Joyya 视频平台开发测试专用"
    "video-ai:视频 AI 分析专用，支持 OCR 和内容识别"
    "content-moderation:内容审核和敏感视频检测专用"
    "monitor:系统监控和日志收集专用"
)

echo "========================================"
echo "🚀 OpenClaw Docker 部署脚本"
echo "========================================"
echo "远程节点：$REMOTE_USER@$REMOTE_HOST"
echo "Ollama 服务：$OLLAMA_HOST:11434"
echo "实例数量：${#INSTANCES[@]}"
echo ""

# 步骤 1: 创建 Docker Compose 配置文件
echo "📝 步骤 1: 创建 Docker Compose 配置文件..."

DOCKER_COMPOSE_PATH="/opt/docker-compose.yml"
SSH_CMD="ssh $REMOTE_USER@$REMOTE_HOST"

# 创建 docker-compose.yml
$SSH_CMD "cat > $DOCKER_COMPOSE_PATH << 'COMPOSE_EOF'
services:
  joyya-dev:
    image: openclaw/openclaw:latest
    container_name: openclaw-joyya-dev
    hostname: joyya-dev
    restart: unless-stopped
    ports:
      - \"3001:8080\"
    environment:
      - NODE_NAME=joyya-dev
      - NODE_PORT=3001
      - NODE_DESC=Joyya 视频平台开发测试专用
      - OLLAMA_URL=http://$OLLAMA_HOST:11434
      - OLLAMA_MODEL=qwen3.5:35b
    volumes:
      - joyya-dev-data:/data
      - joyya-dev-cache:/root/.cache
    depends_on:
      - ollama
    networks:
      - openclaw-network

  video-ai:
    image: openclaw/openclaw:latest
    container_name: openclaw-video-ai
    hostname: video-ai
    restart: unless-stopped
    ports:
      - \"3002:8080\"
    environment:
      - NODE_NAME=video-ai
      - NODE_PORT=3002
      - NODE_DESC=视频 AI 分析专用
      - OLLAMA_URL=http://$OLLAMA_HOST:11434
      - OLLAMA_MODEL=qwen3.5:35b
    volumes:
      - video-ai-data:/data
      - video-ai-cache:/root/.cache
      - video-ai-ollama:/root/.ollama
    depends_on:
      - ollama
    networks:
      - openclaw-network

  content-moderation:
    image: openclaw/openclaw:latest
    container_name: openclaw-content-moderation
    hostname: content-moderation
    restart: unless-stopped
    ports:
      - \"3003:8080\"
    environment:
      - NODE_NAME=content-moderation
      - NODE_PORT=3003
      - NODE_DESC=内容审核和敏感视频检测专用
      - OLLAMA_URL=http://$OLLAMA_HOST:11434
      - OLLAMA_MODEL=qwen3.5:35b
    volumes:
      - content-moderation-data:/data
      - content-moderation-cache:/root/.cache
      - content-moderation-ollama:/root/.ollama
    depends_on:
      - ollama
    networks:
      - openclaw-network

  monitor:
    image: openclaw/openclaw:latest
    container_name: openclaw-monitor
    hostname: monitor
    restart: unless-stopped
    ports:
      - \"3004:8080\"
    environment:
      - NODE_NAME=monitor
      - NODE_PORT=3004
      - NODE_DESC=系统监控和日志收集专用
      - OLLAMA_URL=http://$OLLAMA_HOST:11434
      - OLLAMA_MODEL=qwen3.5:35b
    volumes:
      - monitor-data:/data
      - monitor-cache:/root/.cache
      - monitor-ollama:/root/.ollama
    depends_on:
      - ollama
    networks:
      - openclaw-network

  ollama:
    image: ollama/ollama:latest
    container_name: openclaw-ollama
    hostname: ollama
    restart: unless-stopped
    ports:
      - \"11434:11434\"
    volumes:
      - ollama-data:/root/.ollama
    networks:
      - openclaw-network

volumes:
  joyya-dev-data:
  joyya-dev-cache:
  video-ai-data:
  video-ai-cache:
  video-ai-ollama:
  content-moderation-data:
  content-moderation-cache:
  content-moderation-ollama:
  monitor-data:
  monitor-cache:
  monitor-ollama:
  ollama-data:

networks:
  openclaw-network:
    driver: bridge
COMPOSE_EOF
"

echo "✅ Docker Compose 配置文件已创建：$DOCKER_COMPOSE_PATH"
echo ""

# 步骤 2: 启动 Docker 服务
echo "🚀 步骤 2: 启动 Docker 服务..."
$SSH_CMD "cd /opt && docker compose up -d"

echo "✅ Docker 服务已启动"
echo ""

# 步骤 3: 等待服务就绪
echo "⏳ 步骤 3: 等待服务就绪..."
sleep 30

# 步骤 4: 检查服务状态
echo "🔍 步骤 4: 检查服务状态..."
$SSH_CMD "docker ps --format 'table {{.Names}}\t{{.Status}}\t{{.Ports}}'"

echo ""
echo "========================================"
echo "✅ 部署完成！"
echo "========================================"
echo ""
echo "📦 已部署的 OpenClaw 实例："
echo "----------------------------------------"
echo "1. 📌 joyya-dev      - Joyya 视频平台开发测试专用"
echo "   访问地址：http://192.168.6.177:3001"
echo ""
echo "2. 📌 video-ai       - 视频 AI 分析专用"
echo "   访问地址：http://192.168.6.177:3002"
echo ""
echo "3. 📌 content-moderation - 内容审核和敏感视频检测专用"
echo "   访问地址：http://192.168.6.177:3003"
echo ""
echo "4. 📌 monitor        - 系统监控和日志收集专用"
echo "   访问地址：http://192.168.6.177:3004"
echo ""
echo "📊 Ollama 服务："
echo "   访问地址：http://192.168.6.177:11434"
echo "   模型：qwen3.5:35b"
echo ""
echo "========================================"
