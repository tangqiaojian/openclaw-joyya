<template>
  <div class="video-detail-container" v-if="video">
    <div class="video-player-section">
      <video :src="video.videoUrl" controls class="video-player" /u003e
      <div class="video-info-card card">
        <h1 class="video-title">{{ video.title }}</h1>
        <p class="video-description" v-if="video.description">{{ video.description }}</p>
        <div class="video-stats">
          <span class="stat"@click="incrementView">▶️ {{ formatNumber(video.viewCount) }} 播放</span>
          <span class="stat"@click="handleLike">👍 {{ formatNumber(video.likeCount) }}</span>
          <span class="stat"@click="incrementShare">🔗 {{ formatNumber(video.shareCount) }} 分享</span>
          <span class="stat"@click="handleCollect">⭐ {{ formatNumber(video.coinCount) }} 收藏</span>
        </div>
        <div class="upload-info">
          <div class="user-info">
            <img :src="video.userAvatar" alt="avatar" class="avatar" /u003e
            <span class="username">{{ video.userNickname || '作者' }}</span>
          </div>
          <span class="category-badge">{{ video.categoryName || '未分类' }}</span>
          <span class="upload-time">更新于 {{ formatDate(video.createdAt) }}</span>
        </div>
        <div class="tags-section">
          <span class="tag-label">标签:</span>
          <div class="tags">
            <span v-for="tag in tags" :key="tag.id" class="tag">{{ tag.name }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="comments-section">
      <h2>评论</h2>
      <div class="comment-form card">
        <textarea v-model="newComment" placeholder="发表评论..." rows="3" class="comment-input"></textarea>
        <div class="comment-actions">
          <button @click="submitComment" class="btn-primary" :disabled="!newComment.trim()">发布</button>
        </div>
      </div>
      <div class="comments-list">
        <CommentCard v-for="comment in comments" :key="comment.id" :comment="comment" />  
      </div>
      <div v-if="comments.length === 0" class="empty-comments">暂无评论</div>
    </div>
  </div>
  <div v-else class="loading">加载中...</div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import VideoCard from '../components/VideoCard.vue'
import CommentCard from '../components/CommentCard.vue'

const route = useRoute()
const video = ref(null)
const comments = ref([])
const tags = ref([])
const newComment = ref('')

const formatNumber = (num) => {
  return num >= 10000 ? (num/10000).toFixed(1) + '万' : num
}

const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleDateString('zh-CN')
}

const loadVideo = async () => {
  try {
    const res = await axios.get(`/api/videos/${route.params.id}`)
    video.value = res.data
  } catch (err) {
    console.error('加载视频失败:', err)
  }
}

const loadComments = async () => {
  try {
    const res = await axios.get(`/api/videos/${route.params.id}/comments`, { params: { page: 0, size: 20 } })
    comments.value = res.data.content
  } catch (err) {
    console.error('加载评论失败:', err)
  }
}

const incrementView = async () => {
  try {
    await axios.post(`/api/videos/${route.params.id}/view`)
  } catch (err) {
    console.error('记录播放失败:', err)
  }
}

const incrementShare = async () => {
  try {
    await axios.post(`/api/videos/${route.params.id}/share`)
  } catch (err) {
    console.error('记录分享失败:', err)
  }
}

const handleLike = async () => {
  try {
    await axios.post(`/api/interactions/videos/${route.params.id}/like?userId=1`)
    video.value.likeCount++
  } catch (err) {
    console.error('点赞失败:', err)
  }
}

const handleCollect = async () => {
  try {
    await axios.post(`/api/interactions/videos/${route.params.id}/collect?userId=1`)
    video.value.coinCount++
  } catch (err) {
    console.error('收藏失败:', err)
  }
}

const submitComment = async () => {
  if (!newComment.value.trim()) return
  try {
    await axios.post(`/api/videos/${route.params.id}/comments`, {
      userId: 1,
      content: newComment.value,
      parentId: 0
    })
    newComment.value = ''
    loadComments()
  } catch (err) {
    console.error('发布评论失败:', err)
  }
}

onMounted(() => {
  loadVideo()
  loadComments()
})
</script>

<style scoped>
.video-detail-container {
  max-width: 1200px;
  margin: 0 auto;
}

.video-player-section {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 2rem;
}

.video-player {
  width: 100%;
  border-radius: 8px;
  background: #000;
}

.video-info-card {
  max-width: 400px;
}

.video-title {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  color: #1a1a1a;
}

.video-description {
  color: #666;
  margin-bottom: 1rem;
  line-height: 1.6;
}

.video-stats {
  display: flex;
  gap: 1.5rem;
  margin-bottom: 1.5rem;
}

.stat {
  cursor: pointer;
  color: #666;
  display: flex;
  align-items: center;
  gap: 0.25rem;
}

.stat:hover {
  color: #fb7299;
}

.upload-info {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding-top: 1rem;
  border-top: 1px solid #eee;
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
}

.username {
  font-weight: bold;
}

.category-badge, .upload-time {
  font-size: 0.875rem;
  color: #666;
}

.tags-section {
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid #eee;
}

.tag-label {
  font-size: 0.875rem;
  color: #666;
}

.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.tag {
  background: #fb7299;
  color: white;
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-size: 0.75rem;
}

.comments-section {
  margin-top: 2rem;
}

.comments-section h2 {
  margin-bottom: 1.5rem;
}

.comment-form {
  margin-bottom: 1.5rem;
}

.comment-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  resize: vertical;
  margin-bottom: 0.5rem;
}

.comment-actions {
  text-align: right;
}

.comments-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.empty-comments {
  text-align: center;
  color: #999;
  padding: 2rem;
}

@media (max-width: 900px) {
  .video-player-section {
    grid-template-columns: 1fr;
  }
}
</style>
