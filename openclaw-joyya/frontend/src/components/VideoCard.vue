<template>
  <div class="video-card card" @click="$emit('click', video)">
    <div class="video-thumbnail">
      <img :src="video.cover || '/placeholder.jpg'" :alt="video.title" class="thumbnail" />
      <span class="duration">{{ formatDuration(video.duration) }}</span>
    </div>
    <div class="video-info">
      <h3 class="video-title" :title="video.title">{{ video.title }}</h3>
      <p class="video-meta">
        <span class="stat" @click.stop="incrementView">▶️ {{ formatNumber(video.viewCount) }} 播放</span>
        <span class="stat" @click.stop="handleLike">👍 {{ formatNumber(video.likeCount) }}</span>
      </p>
      <div class="video-author">
        <span class="author">{{ video.userNickname || '作者' }}</span>
        <span class="time">· {{ formatDate(video.createdAt) }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'

const props = defineProps({
  video: {
    type: Object,
    required: true
  }
})

defineEmits(['click'])

const formatNumber = (num) => {
  return num >= 10000 ? (num/10000).toFixed(1) + '万' : num
}

const formatDuration = (seconds) => {
  if (!seconds) return ''
  const mins = Math.floor(seconds / 60)
  const secs = seconds % 60
  return `${mins}:${secs.toString().padStart(2, '0')}`
}

const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleDateString('zh-CN')
}

const incrementView = async () => {
  try {
    await axios.post(`/api/videos/${props.video.id}/view`)
  } catch (err) {
    console.error('记录播放失败:', err)
  }
}

const handleLike = async () => {
  try {
    await axios.post(`/api/interactions/videos/${props.video.id}/like?userId=1`)
  } catch (err) {
    console.error('点赞失败:', err)
  }
}
</script>

<style scoped>
:root {
  --primary-color: #fb7299;
}

/* 视频卡片 */
.video-card {
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
}

.video-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(251, 114, 153, 0.1),
    transparent
  );
  transition: left 0.5s;
  pointer-events: none;
}

.video-card:hover::before {
  left: 100%;
}

.video-card:hover {
  transform: translateY(-8px) scale(1.02);
  box-shadow: 
    0 12px 32px rgba(251, 114, 153, 0.2),
    0 0 0 1px rgba(251, 114, 153, 0.1);
}

.video-thumbnail {
  position: relative;
  aspect-ratio: 16/9;
  overflow: hidden;
  border-radius: 8px 8px 0 0;
  transition: all 0.3s;
}

.video-card:hover .video-thumbnail {
  border-radius: 8px 8px 16px 16px;
  box-shadow: 0 8px 24px rgba(251, 114, 153, 0.2);
}

.thumbnail {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s;
}

.video-card:hover .thumbnail {
  transform: scale(1.1);
}

/* 时长标签 */
.duration {
  position: absolute;
  bottom: 8px;
  right: 8px;
  background: rgba(0, 0, 0, 0.75);
  color: white;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 500;
  backdrop-filter: blur(4px);
  animation: pulseRing 2s ease-in-out infinite;
}

/* 信息区域 */
.video-info {
  padding: 1rem;
  transition: all 0.3s;
}

.video-card:hover .video-info {
  padding-left: 1.2rem;
}

.video-title {
  font-size: 1rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
  color: #1a1a1a;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  transition: color 0.3s;
}

.video-card:hover .video-title {
  color: var(--primary-color);
}

.video-meta {
  display: flex;
  gap: 1rem;
  margin-bottom: 0.5rem;
}

.stat {
  font-size: 0.75rem;
  color: #666;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  gap: 4px;
  transition: all 0.3s;
}

.stat:hover {
  color: var(--primary-color);
  transform: scale(1.1);
}

.stat:active {
  transform: scale(0.95);
}

/* 作者信息 */
.video-author {
  display: flex;
  align-items: center;
  font-size: 0.875rem;
  color: #999;
  transition: all 0.3s;
}

.video-author .author {
  color: #666;
  font-weight: 500;
  transition: all 0.3s;
}

.video-card:hover .video-author .author {
  color: var(--primary-color);
}

.video-author .time {
  margin-left: 0.5rem;
  opacity: 0.7;
}

/* 点赞动画 */
@keyframes likeAnimation {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.3);
  }
}

.stat.liked {
  animation: likeAnimation 0.3s ease-out;
}

/* 响应式 */
@media (max-width: 768px) {
  .video-card:hover {
    transform: translateY(-4px);
  }
  
  .video-card:hover .video-info {
    padding-left: 1rem;
  }
}
</style>
