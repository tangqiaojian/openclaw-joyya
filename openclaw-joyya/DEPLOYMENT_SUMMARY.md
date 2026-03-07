# OpenClaw 4 实例部署总结

## 📅 部署信息

- **部署时间**: 2026-03-05 00:40
- **远程节点**: 192.168.6.177
- **SSH 用户**: hxzh
- **Ollama 服务**: 192.168.6.62:11434
- **模型**: qwen3.5:35b

---

## 🎯 部署的 4 个 OpenClaw 实例

| # | 实例名称 | 端口 | 用途 | 容器名称 |
|---|---------|------|------|----------|
| 1 | **joyya-dev** | 3001 | Joyya 视频平台开发测试专用 | openclaw-joyya-dev |
| 2 | **video-ai** | 3002 | 视频 AI 分析、OCR、内容识别 | openclaw-video-ai |
| 3 | **content-moderation** | 3003 | 内容审核、敏感视频检测 | openclaw-content-moderation |
| 4 | **monitor** | 3004 | 系统监控、日志收集 | openclaw-monitor |

### Ollama 服务
- **容器名称**: openclaw-ollama
- **端口**: 11434
- **模型**: qwen3.5:35b
- **数据目录**: /root/.ollama

---

## 📊 访问地址

| 服务 | URL | 用途 |
|------|-----|------|
| Joyya Dev | http://192.168.6.177:3001 | Joyya 视频平台开发调试 |
| Video AI | http://192.168.6.177:3002 | 视频 AI 分析 |
| Content Moderation | http://192.168.6.177:3003 | 内容审核 |
| Monitor | http://192.168.6.177:3004 | 系统监控 |
| Ollama API | http://192.168.6.177:11434 | Ollama 服务接口 |

---

## 🔧 部署配置

### 环境变量
```bash
# 所有实例通用配置
OLLAMA_URL=http://192.168.6.62:11434
OLLAMA_MODEL=qwen3.5:35b

# 各实例特定配置
NODE_NAME=joyya-dev / video-ai / content-moderation / monitor
NODE_PORT=3001 / 3002 / 3003 / 3004
NODE_DESC=[对应的描述]
```

### 数据卷
- `/data` - 实例数据目录
- `/root/.cache` - 缓存目录
- `/root/.ollama` - Ollama 模型数据

---

## 📝 部署文件位置

本地仓库：
- `openclaw-joyya/docker-compose.yml` - Docker Compose 配置
- `openclaw-joyya/scripts/deploy-instances.sh` - 部署脚本

远程节点（192.168.6.177）：
- `/opt/docker-compose.yml` - Docker Compose 配置
- 各实例数据存储在 Docker volumes 中

---

## ⚙️ 服务状态检查

### 查看容器状态
```bash
ssh hxzh@192.168.6.177 "docker ps -a"
```

### 查看容器日志
```bash
# Joyya Dev
ssh hxzh@192.168.6.177 "docker logs openclaw-joyya-dev"

# Video AI
ssh hxzh@192.168.6.177 "docker logs openclaw-video-ai"

# Content Moderation
ssh hxzh@192.168.6.177 "docker logs openclaw-content-moderation"

# Monitor
ssh hxzh@192.168.6.177 "docker logs openclaw-monitor"

# Ollama
ssh hxzh@192.168.6.177 "docker logs openclaw-ollama"
```

### 重启服务
```bash
ssh hxzh@192.168.6.177 "cd /opt && docker compose restart"
```

### 停止服务
```bash
ssh hxzh@192.168.6.177 "cd /opt && docker compose down"
```

### 查看 Ollama 模型
```bash
ssh hxzh@192.168.6.177 "docker exec openclaw-ollama ollama list"
```

---

## 🎉 部署完成！

所有 4 个 OpenClaw 实例已成功部署在远程节点上，并通过 Docker 容器运行。

每个实例都独立运行，可以通过各自的端口访问，并共享 Ollama 服务提供的 AI 能力。

---

**部署人**: OpenClaw AI Assistant  
**记录时间**: 2026-03-05 00:40
