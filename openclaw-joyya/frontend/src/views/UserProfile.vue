<template>
  <div v-if="user" class="user-profile-container">
    <div class="user-header card">
      <img :src="user.avatar" alt="avatar" class="avatar" />
      <div class="user-info">
        <h1 class="username">{{ user.nickname || user.username }}</h1>
        <p class="signature" v-if="user.signature">{{ user.signature }}</p>
        <div class="stats">
          <span class="stat-item"><strong>{{ user.followers || 0 }}</strong> 粉丝</span>
          <span class="stat-item"><strong>{{ user.following || 0 }}</strong> 关注</span>
          <span class="stat-item"><strong>{{ user.coins || 0 }}</strong> 硬币</span>
        </div>
      </div>
    </div>
    
    <div class="tabs">
      <button :class="['tab', { active: activeTab === 'videos' }]" @click="activeTab = 'videos'">
        视频列表
      </button>
      <button :class="['tab', { active: activeTab === 'favorites' }]" @click="activeTab = 'favorites'">
        我的收藏
      </button>
    </div>
    
    <div class="content-section">
      <div v-if="activeTab === 'videos'" class="videos-section">
        <VideoCard v-for="video in videos" :key="video.id" :video="video" />
        <div v-if="videos.length === 0" class="empty">暂无视频</div>
      </div>
      <div v-else class="favorites-section">
        <VideoCard v-for="video in favorites" :key="video.id" :video="video" />
        <div v-if="favorites.length === 0" class="empty">暂无收藏</div>
      </div>
    </div>
  </div>
  <div v-else class="loading" @click="loadUser">用户信息加载中...点击重试</div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import VideoCard from '../components/VideoCard.vue'

const user = ref(null)
const videos = ref([])
const favorites = ref([])
const activeTab = ref('videos')
const userId = 1 // 示例用户 ID

const loadUser = async () => {
  try {
    user.value = {
      id: userId,
      username: 'joyya_user',
      nickname: 'Joyya 官方',
      avatar: '',
      signature: '这是 Joyya 官方账号',
      followers: 1000,
      following: 50,
      coins: 500,
      isVerified: true
    }
  } catch (err) {
    console.error('加载用户信息失败:', err)
  }
}

const loadUserVideos = async () => {
  try {
    const res = await axios.get('/api/videos', { params: { page: 0, size: 10 } })
    videos.value = res.data.content
  } catch (err) {
    console.error('加载视频失败:', err)
  }
}

const loadFavorites = async () => {
  try {
    const res = await axios.get(`/api/interactions/users/${userId}/collections`, { params: { page: 0, size: 10 } })
    favorites.value = res.data.content
  } catch (err) {
    console.error('加载收藏失败:', err)
  }
}

onMounted(() => {
  loadUser()
  loadUserVideos()
})
</script>

<style scoped>
.user-profile-container {
  max-width: 1000px;
  margin: 0 auto;
}

.user-header {
  display: flex;
  align-items: center;
  gap: 1.5rem;
  margin-bottom: 2rem;
  padding: 2rem;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background: #eee;
}

.username {
  font-size: 1.75rem;
  margin-bottom: 0.5rem;
}

.signature {
  color: #666;
  margin-bottom: 1rem;
}

.stats {
  display: flex;
  gap: 2rem;
}

.stat-item {
  color: #666;
}

.stat-item strong {
  color: #fb7299;
  margin-right: 0.25rem;
}

.tabs {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
  border-bottom: 2px solid #eee;
  padding-bottom: 0.5rem;
}

.tab {
  padding: 0.5rem 1.5rem;
  border: none;
  background: none;
  cursor: pointer;
  font-size: 1rem;
  color: #666;
  border-bottom: 2px solid transparent;
  margin-bottom: -2px;
}

.tab.active {
  color: #fb7299;
  border-bottom-color: #fb7299;
}

.content-section {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 1.5rem;
}

.empty {
  text-align: center;
  color: #999;
  padding: 3rem;
}

.loading {
  text-align: center;
  padding: 3rem;
  color: #666;
  cursor: pointer;
}
</style>
