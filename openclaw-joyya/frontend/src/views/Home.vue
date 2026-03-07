<template>
  <div class="home-page">
    <!-- 顶部 Banner -->
    <div class="page-header">
      <div class="header-content">
        <h1 class="page-title">
          <span class="title-icon">🔥</span>
          热门推荐
        </h1>
        <p class="page-description">发现最精彩的视频内容</p>
      </div>
    </div>

    <!-- 视频网格 -->
    <div class="video-section" v-if="videos.length > 0">
      <div class="video-grid">
        <VideoCard 
          v-for="(video, index) in videos" 
          :key="video.id" 
          :video="video" 
          :style="`animation-delay: ${index * 0.1}s`"
          @click="goToVideo(video.id)"
        />
      </div>
    </div>

    <!-- 加载状态 -->
    <div v-else-if="loading" class="loading-section">
      <div class="loading-spinner">
        <el-icon class="spinner-icon" size="32"><Loading /></el-icon>
      </div>
      <p class="loading-text">正在加载精彩内容...</p>
    </div>

    <!-- 错误状态 -->
    <div v-else-if="error" class="error-section">
      <div class="error-content">
        <el-icon class="error-icon" size="48"><WarningFilled /></el-icon>
        <h3>{{ error }}</h3>
        <button class="retry-btn" @click="loadVideos">重试</button>
      </div>
    </div>

    <!-- 空状态 -->
    <div v-else class="empty-section">
      <div class="empty-content">
        <el-icon class="empty-icon" size="64"><VideoCamera /></el-icon>
        <h3>暂无视频</h3>
        <p>快来发布你的第一个视频吧！</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getHotVideos } from '@/api/video'
import { WarningFilled, VideoCamera, Loading } from '@element-plus/icons-vue'

const router = useRouter()
const videos = ref([])
const loading = ref(true)
const error = ref(null)

const loadVideos = async () => {
  loading.value = true
  error.value = null
  
  try {
    const res = await getHotVideos(0, 12)
    
    if (res && res.content) {
      videos.value = res.content
    } else if (Array.isArray(res)) {
      videos.value = res
    } else {
      videos.value = []
    }
  } catch (err) {
    console.error('加载视频失败:', err)
    error.value = '加载失败，请稍后重试'
    ElMessage.error('加载视频失败')
  } finally {
    loading.value = false
  }
}

const goToVideo = (id) => {
  router.push(`/videos/${id}`)
}

onMounted(() => {
  loadVideos()
})
</script>

<style scoped>
@import '../style/theme.css';

/* 页面容器 */
.home-page {
  min-height: calc(100vh - 140px);
  padding: 0;
}

/* 页面头部 */
.page-header {
  background: linear-gradient(135deg, rgba(251, 114, 153, 0.1), rgba(139, 92, 246, 0.1));
  padding: 40px 0;
  margin-bottom: 40px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  animation: fadeInDown 0.8s ease-out;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.page-title {
  font-size: var(--font-size-2xl);
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 8px 0;
  display: flex;
  align-items: center;
  gap: 12px;
  animation: fadeInDown 0.8s ease-out;
}

.title-icon {
  font-size: var(--font-size-2xl);
  animation: pulse 2s ease-in-out infinite;
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
    filter: blur(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
    filter: blur(0);
  }
}

@keyframes pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.1); }
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@keyframes float {
  0%, 100% { transform: translateY(0px); }
  50% { transform: translateY(-10px); }
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  10%, 30%, 50%, 70%, 90% { transform: translateX(-5px); }
  20%, 40%, 60%, 80% { transform: translateX(5px); }
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.page-description {
  font-size: var(--font-size-md);
  color: var(--text-secondary);
  margin: 0;
  animation: fadeInUp 0.8s ease-out 0.2s both;
}

/* 视频区域 */
.video-section {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  animation: fadeInUp 0.8s ease-out 0.4s both;
}

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
}

/* 视频卡片 */
.video-card {
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
}

/* 加载状态 */
.loading-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 20px;
}

.loading-spinner {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  animation: float 2s ease-in-out infinite;
}

.spinner-icon {
  animation: rotate 1s linear infinite;
  color: var(--primary-color);
}

.loading-text {
  color: var(--text-secondary);
  font-size: var(--font-size-md);
  margin: 0;
}

/* 错误状态 */
.error-section {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 80px 20px;
}

.error-content {
  text-align: center;
  padding: 40px;
  background: rgba(255, 77, 79, 0.05);
  border-radius: var(--radius-lg);
  border: 1px solid rgba(255, 77, 79, 0.2);
  animation: shake 0.5s;
}

.error-icon {
  color: var(--error-color);
  margin-bottom: 16px;
  animation: bounce 1s ease-in-out infinite;
}

.error-content h3 {
  color: var(--error-color);
  font-size: var(--font-size-lg);
  margin: 0 0 20px 0;
}

.retry-btn {
  padding: 12px 32px;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  border: none;
  border-radius: var(--radius-lg);
  font-size: var(--font-size-md);
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-base);
  box-shadow: 0 4px 12px rgba(251, 114, 153, 0.3);
}

.retry-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(251, 114, 153, 0.4);
}

.retry-btn:active {
  transform: translateY(0);
}

/* 空状态 */
.empty-section {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 80px 20px;
}

.empty-content {
  text-align: center;
  padding: 40px;
  color: var(--text-secondary);
  animation: fadeInUp 0.6s ease-out;
}

.empty-icon {
  color: var(--text-tertiary);
  margin-bottom: 16px;
  display: block;
  animation: float 3s ease-in-out infinite;
}

.empty-content h3 {
  color: var(--text-primary);
  font-size: var(--font-size-lg);
  margin: 0 0 8px 0;
}

.empty-content p {
  color: var(--text-secondary);
  font-size: var(--font-size-sm);
  margin: 0;
}

/* 响应式 */
@media (max-width: 768px) {
  .video-grid {
    grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
    gap: 16px;
  }
  
  .page-header {
    padding: 30px 0;
  }
  
  .page-title {
    font-size: var(--font-size-xl);
  }
}

@media (max-width: 480px) {
  .video-grid {
    grid-template-columns: 1fr;
  }
}
</style>
