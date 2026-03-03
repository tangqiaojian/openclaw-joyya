<template>
  <div class="comment-card card">
    <div class="comment-header">
      <div class="user-info">
        <img :src="comment.userAvatar || '/placeholder-avatar.jpg'" alt="avatar" class="avatar" /u003e
        <span class="username">{{ comment.userNickname || '匿名用户' }}</span>
      </div>
      <span class="comment-time">{{ formatTime(comment.createdAt) }}</span>
    </div>
    <div class="comment-content">{{ comment.content }}</div>
    <div class="comment-actions">
      <button class="action-btn"@click="handleLike">
        👍 {{ comment.likeCount }}
      </button>
      <button class="action-btn"@click="$emit('showReplies')">
        回复 {{ comment.replyCount }}
      </button>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  comment: {
    type: Object,
    required: true
  }
})

defineEmits(['showReplies'])

const formatTime = (dateStr) => {
  const date = new Date(dateStr)
  const now = new Date()
  const diff = (now - date) / 1000 // 秒
  
  if (diff < 60) return '刚刚'
  if (diff < 3600) return `${Math.floor(diff / 60)}分钟前`
  if (diff < 86400) return `${Math.floor(diff / 3600)}小时前`
  return date.toLocaleDateString('zh-CN')
}

const handleLike = async () => {
  try {
    // TODO: 实现点赞逻辑
  } catch (err) {
    console.error('点赞失败:', err)
  }
}
</script>

<style scoped>
.comment-card {
  padding: 1rem 1.5rem;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.75rem;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: #eee;
}

.username {
  font-weight: bold;
  color: #1a1a1a;
}

.comment-time {
  font-size: 0.75rem;
  color: #999;
}

.comment-content {
  color: #333;
  line-height: 1.6;
  margin-bottom: 1rem;
}

.comment-actions {
  display: flex;
  gap: 1rem;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  color: #666;
  font-size: 0.875rem;
  cursor: pointer;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
}

.action-btn:hover {
  color: #fb7299;
  background: rgba(251, 114, 153, 0.1);
}
</style>
