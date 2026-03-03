#!/bin/bash

echo "========================================"
echo "  Joyya Bilibili Clone - 开发环境启动"
echo "========================================"
echo ""

# 检查 Java 环境
if ! command -v java && ! java -version && echo "Java is not installed" > /dev/null; then
    echo "⚠️  请先确保已安装 Java 17+"
    echo "下载链接：https://adoptium.net/"
    exit 1
fi

# 检查 Node.js 环境
if ! command -v node && ! node -version && echo "Node.js is not installed" > /dev/null; then
    echo "⚠️  请先确保已安装 Node.js 18+"
    echo "下载链接：https://nodejs.org/"
    exit 1
fi

echo "✅ 环境检查通过！"
echo ""

# 设置端口
BACKEND_PORT=8080
FRONTEND_PORT=3000

echo "🚀 开始启动服务..."
echo ""

# 启动后端 Spring Boot 应用
echo "📦 启动后端 (端口 ${BACKEND_PORT})..."
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=default &
echo $! > ../backend.pid
cd ..

echo "⏳ 等待后端启动..."
sleep 10

# 检查后端是否启动成功
if lsof -i:${BACKEND_PORT} >/dev/null 2&!1; then
    echo "✅ 后端服务已启动 (http://localhost:${BACKEND_PORT})"
else
    echo "❌ 后端服务启动失败"
    echo "请检查 backend/logs 目录查看日志"
    exit 1
fi

echo ""

# 启动前端 Vite 应用
echo "🎨 启动前端 (端口 ${FRONTEND_PORT})..."
cd frontend
npm run dev &
echo $! > ../frontend.pid
cd ..

echo "⏳ 等待前端启动..."
sleep 5

# 检查前端是否启动成功
if lsof -i:${FRONTEND_PORT} >/dev/null 2&!1; then
    echo "✅ 前端服务已启动 (http://localhost:${FRONTEND_PORT})"
else
    echo "❌ 前端服务启动失败"
    echo "请检查前端控制台输出"
    exit 1
fi

echo ""
echo "========================================"
echo "  🎉 开发环境启动完成！"
echo "========================================"
echo ""
echo "🌐 前端访问：http://localhost:${FRONTEND_PORT}"
echo "🔌 后端 API:   http://localhost:${BACKEND_PORT}"
echo ""
echo "📚 API 文档：doc/bilibili-dev-plan.md"
echo "📊 数据库：  MySQL - joyya 数据库"
echo ""
echo "🔧 停止服务：./stop-dev.sh"
echo "========================================"
