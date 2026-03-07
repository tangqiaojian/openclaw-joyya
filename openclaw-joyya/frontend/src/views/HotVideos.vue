<template>
  <div class="hot-container container">
    <div class="section-header">
      <h1 class="section-title"><span class="emoji">🔥</span> 热门视频</h1>
    </div>
    <div class="video-grid">
      <VideoCard v-for="video in videos" :key="video.id" :video="video" @click="goToVideo(video.id)" />
    </div>
    <div v-if="loading" class="loading">加载中...</div>
    <div v-if="error" class="error" @click="loadVideos">{{ error }} - 点击重试</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import VideoCard from '../components/VideoCard.vue'

const router = useRouter()
const videos = ref([])
const loading = ref(true)
const error = ref(null)

const loadVideos = async () => {
  loading.value = true
  error.value = null
  try {
    const res = await axios.get('/api/videos/hot', { params: { page: 0, size: 20 } })
    videos.value = res.data.content
  } catch (err) {
    console.error(err)
    error.value = '加载失败'
  } finally {
    loading.value = false
  }
}

const goToVideo = (id) => {
  router.push(`/videos/${id}`)
}

onMounted(loadVideos)
</script>

<style scoped>
.section-title {
  font-size: 1.5rem;
  color: #1a1a1a;
}

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 1.5rem;
  margin-top: 1.5rem;
}

.loading, .error {
  text-align: center;
  padding: 2rem;
  color: #666;
  margin-top: 1rem;
}

.error {
  color: #f56c6c;
  cursor: pointer;
}
</style>
