#!/bin/bash
# OpenClaw 部署检查脚本
# 检查 192.168.6.177 上的 Docker 部署状态

REMOTE_USER="hxzh"
REMOTE_HOST="192.168.6.177"

echo "===== ==================== ====="
echo "🔍 OpenClaw 部署检查脚本"
echo "===== ==================== ====="
echo ""

# 检查 SSH 连接
echo "📍 步骤 1: 检查 SSH 连接..."
if ssh -o ConnectTimeout=10 -o StrictHostKeyChecking=no $REMOTE_USER@$REMOTE_HOST "echo '连接成功'" > /dev/null 2>&1; then
    echo "✅ SSH 连接正常"
else
    echo "❌ SSH 连接失败"
    exit 1
fi
echo ""

# 检查 Docker 服务状态
echo "📍 步骤 2: 检查 Docker 服务状态..."
ssh $REMOTE_USER@$REMOTE_HOST "docker --version" 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✅ Docker 服务运行中"
else
    echo "❌ Docker 服务未安装或未运行"
fi
echo ""

# 检查 docker-compose.yml 文件
echo "📍 步骤 3: 检查 docker-compose.yml 文件..."
if ssh $REMOTE_USER@$REMOTE_HOST "test -f /opt/docker-compose.yml && echo '存在'" > /dev/null 2>&1; then
    echo "✅ /opt/docker-compose.yml 存在"
    echo "   文件大小："
    ssh $REMOTE_USER@$REMOTE_HOST "ls -lh /opt/docker-compose.yml | awk '{print \$5}'"
else
    echo "❌ docker-compose.yml 不存在"
fi
echo ""

# 检查 Docker 容器状态
echo "📍 步骤 4: 检查 Docker 容器状态..."
echo ""
echo "📦 所有 OpenClaw 相关容器:"
ssh $REMOTE_USER@$REMOTE_HOST "docker ps -a --filter name=openclaw --format 'table {{.Names}}\t{{.Status}}\t{{.Ports}}'" 2>&1
echo ""

# 检查 Docker Compose 服务状态
echo "📍 步骤 5: 检查 Docker Compose 服务状态..."
ssh $REMOTE_USER@$REMOTE_HOST "cd /opt && docker compose ps --all" 2>&1
echo ""

# 检查 Docker 镜像
echo "📍 步骤 6: 检查 Docker 镜像..."
echo ""
echo "📚 OpenClaw 相关镜像:"
ssh $REMOTE_USER@$REMOTE_HOST "docker images | grep -E 'openclaw|ollama'" 2>&1
echo ""

# 检查 Ollama 服务
echo "📍 步骤 7: 检查 Ollama 服务..."
ssh $REMOTE_USER@$REMOTE_HOST "curl -s http://192.168.6.62:11434/api/tags 2>/dev/null | jq -r '.models[] | .name' 2>/dev/null || echo 'Ollama API 返回格式不支持或无法连接'"
echo ""

# 检查端口占用
echo "📍 步骤 8: 检查端口占用..."
echo ""
echo "🔌 已分配的端口:"
ssh $REMOTE_USER@$REMOTE_HOST "netstat -tlnp 2>/dev/null | grep -E '3001|3002|3003|3004|11434' | awk '{print \$4}' | sed 's/.*://'" 2>&1 || ssh $REMOTE_USER@$REMOTE_HOST "ss -tlnp 2>/dev/null | grep -E '3001|3002|3003|3004|11434'" 2>&1
echo ""

# 总结
echo "===== ==================== ====="
echo "📋 部署状态总结"
echo "===== ==================== ====="
echo ""
echo "📌 实例列表:"
echo "  1. joyya-dev      - 端口：3001"
echo "  2. video-ai       - 端口：3002"
echo "  3. content-moderation - 端口：3003"
echo "  4. monitor        - 端口：3004"
echo ""
echo "🤖 Ollama 服务:"
echo "  地址：192.168.6.62:11434"
echo "  模型：qwen3.5:35b"
echo ""
echo "📝 下一步操作:"
echo "  - 如果容器未运行，执行：docker compose up -d"
echo "  - 查看日志：docker logs <container_name>"
echo "  - 重启服务：docker compose restart"
echo ""
echo "===== ==================== ====="
