#!/bin/bash

echo "🛑 停止开发环境..."

# 停止后端
if [ -f "backend.pid" ]; then
    PID=$(cat backend.pid)
    if ps -p $PID >/dev/null; then
        kill $PID
        echo "✅ 后端服务已停止 (PID: $PID)"
    fi
    rm backend.pid
fi

# 停止前端
if [ -f "frontend.pid" ]; then
    PID=$(cat frontend.pid)
    if ps -p $PID >/dev/null; then
        kill $PID
        echo "✅ 前端服务已停止 (PID: $PID)"
    fi
    rm frontend.pid
fi

# 停止所有相关进程
pkill -f "mvn spring-boot:run" 2>/dev/null
pkill -f "vite" 2>/dev/null

echo ""
echo "✅ 所有服务已停止"
