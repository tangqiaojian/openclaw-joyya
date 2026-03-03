#!/bin/bash

# OpenClaw Joyya - 项目测试启动脚本
# 用于启动前后端应用并测试功能

echo "=========================================="
echo "OpenClaw Joyya - 项目测试启动脚本"
echo "=========================================="
echo ""

# 设置颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 检查 Java 版本
if ! command -v java &> /dev/null; then
    echo -e "${RED}错误：未检测到 Java${NC}"
    echo "请安装 JDK 17+ 并配置 JAVA_HOME 环境变量"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
echo -e "Java 版本：${JAVA_VERSION}"

# 检查 Node.js 版本
if ! command -v node &> /dev/null; then
    echo -e "${RED}错误：未检测到 Node.js${NC}"
    echo "请安装 Node.js 16+"
    exit 1
fi

NODE_VERSION=$(node -v | cut -d'v' -f2 | cut -d'.' -f1)
echo -e "Node.js 版本：${NODE_VERSION}"

echo ""
echo "=========================================="
echo "准备启动项目..."
echo "=========================================="
echo ""

# 设置工作目录
PROJECT_DIR=$(dirname "$0")
BACKEND_DIR="$PROJECT_DIR/backend"
FRONTEND_DIR="$PROJECT_DIR/frontend"

# 启动后端服务
echo "[1/2] 启动后端服务..."
echo "目录：$BACKEND_DIR"
cd "$BACKEND_DIR"

# 检查 Maven
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}错误：未检测到 Maven${NC}"
    echo "请安装 Maven 并配置 M2_HOME 环境变量"
    exit 1
fi

# 编译并启动后端
echo "正在编译后端项目..."
mvn clean install -DskipTests
if [ $? -ne 0 ]; then
    echo -e "${RED}错误：后端编译失败${NC}"
    exit 1
fi

echo "后端编译成功！"
echo "正在启动后端服务..."
echo "访问：http://localhost:8080"

# 使用 nohup 在后台启动后端
nohup mvn spring-boot:run > backend.log 2>&1 &
BACKEND_PID=$!

echo "后端服务 PID: $BACKEND_PID"
echo "日志文件：$BACKEND_DIR/backend.log"

# 等待后端启动
echo "等待后端启动（约 10 秒）..."
sleep 10

# 检查后端是否正常运行
if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo -e "${GREEN}✓ 后端服务启动成功！${NC}"
else
    echo "后端服务可能还未完全启动，等待中..."
    sleep 10
    if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
        echo -e "${GREEN}✓ 后端服务已就绪！${NC}"
    else
        echo -e "${YELLOW}警告：无法访问健康检查端点，但可能已在运行${NC}"
    fi
fi

echo ""
echo "[2/2] 启动前端服务..."
echo "目录：$FRONTEND_DIR"
cd "$FRONTEND_DIR"

# 检查 npm
if ! command -v npm &> /dev/null; then
    echo -e "${RED}错误：未检测到 npm${NC}"
    exit 1
fi

# 安装依赖
echo "正在安装前端依赖..."
npm install
if [ $? -ne 0 ]; then
    echo -e "${RED}错误：前端依赖安装失败${NC}"
    exit 1
fi

echo "前端依赖安装成功！"
echo "正在启动前端开发服务器..."
echo "访问：http://localhost:5173"

# 启动前端开发服务器
npm run dev &
FRONTEND_PID=$!

echo "前端服务 PID: $FRONTEND_PID"

echo ""
echo "=========================================="
echo "项目启动完成！"
echo "=========================================="
echo ""
echo "📱 访问地址："
echo "   前端：http://localhost:5173"
echo "   后端：http://localhost:8080"
echo ""
echo "📋 服务状态："
echo "   后端 PID: $BACKEND_PID"
echo "   前端 PID: $FRONTEND_PID"
echo ""
echo "⚙️  操作指令："
echo "   - 停止后端：kill $BACKEND_PID"
echo "   - 停止前端：kill $FRONTEND_PID"
echo "   - 查看后端日志：tail -f backend/backend.log"
echo "   - 查看前端日志：tail -f frontend/npm-debug.log"
echo ""
echo "📝 API 测试："
echo "   登录：curl -X POST http://localhost:8080/api/auth/login \\"
echo "           -H 'Content-Type: application/json' \\"
echo "           -d '{\"username\":\"admin\",\"password\":\"123456\"}'"
echo ""
echo "   注册：curl -X POST http://localhost:8080/api/auth/register \\"
echo "           -H 'Content-Type: application/json' \\"
echo "           -d '{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"123456\"}'"
echo ""
echo "=========================================="
echo -e "${GREEN}项目运行中！${NC}"
echo "按 Ctrl+C 停止所有服务"
echo "=========================================="

# 等待进程
wait
