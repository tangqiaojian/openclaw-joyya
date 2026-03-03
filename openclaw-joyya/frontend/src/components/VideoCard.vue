<template>
  <div class="video-card card" @click="$emit('click', video)">
    <div class="video-thumbnail">
      <img :src="video.cover || '/placeholder.jpg'" :alt="video.title" class="thumbnail" /u003e
      <span class="duration">{{ formatDuration(video.duration) }}</span>
    </div>
    <div class="video-info">
      <h3 class="video-title" :title="video.title">{{ video.title }}</h3>
      <p class="video-meta">
        <span class="stat"@click.stop="incrementView">▶️ {{ formatNumber(video.viewCount) }} 播放</span>
        <span class="stat"@click.stop="handleLike">👍 {{ formatNumber(video.likeCount) }}</span>
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
.video-card {
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.video-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}

.video-thumbnail {
  position: relative;
  aspect-ratio: 16/9;
  overflow: hidden;
  border-radius: 8px 8px 0 0;
}

.thumbnail {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.duration {
  position: absolute;
  bottom: 8px;
  right: 8px;
  background: rgba(0, 0, 0, 0.75);
  color: white;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 0.75rem;
}

.video-info {
  padding: 1rem;
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
}

.stat:hover {
  color: #fb7299;
}

.video-author {
  display: flex;
  align-items: center;
  font-size: 0.875rem;
  color: #999;
}

.video-author .author {
  color: #666;
  font-weight: 500;
}

.video-author .time {
  margin-left: 0.5rem;
}
</style>
