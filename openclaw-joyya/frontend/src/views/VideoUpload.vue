<template>
  <div class="upload-container card">
    <h1>上传视频</h1>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="title">视频标题</label>
        <input type="text" id="title" v-model="form.title" required placeholder="请输入视频标题" class="form-input" />
      </div>
      
      <div class="form-group">
        <label for="description">视频描述</label>
        <textarea id="description" v-model="form.description" rows="4" placeholder="请输入视频描述" class="form-input" />
      </div>
      
      <div class="form-group">
        <label for="cover">封面 URL</label>
        <input type="url" id="cover" v-model="form.cover" placeholder="https://example.com/cover.jpg" class="form-input" />
      </div>
      
      <div class="form-group">
        <label for="videoUrl">视频 URL</label>
        <input type="url" id="videoUrl" v-model="form.videoUrl" required placeholder="https://example.com/video.mp4" class="form-input" />
      </div>
      
      <div class="form-group">
        <label for="category">分类</label>
        <select id="category" v-model="form.categoryId" class="form-select">
          <option :value="null">选择分类</option>
          <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
        </select>
      </div>
      
      <div class="form-group">
        <label for="duration">时长（秒）</label>
        <input type="number" id="duration" v-model.number="form.duration" placeholder="视频时长" class="form-input" />
      </div>
      
      <button type="submit" class="btn-primary" :disabled="loading">
        {{ loading ? '上传中...' : '发布视频' }}
      </button>
      <router-link to="/" class="cancel-link">取消</router-link>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const form = ref({
  title: '',
  description: '',
  cover: '',
  videoUrl: '',
  categoryId: null,
  duration: 0
})

const categories = ref([])
const loading = ref(false)

const loadCategories = async () => {
  try {
    const res = await axios.get('/api/categories')
    categories.value = res.data
  } catch (err) {
    console.error('加载分类失败:', err)
  }
}

const handleSubmit = async () => {
  loading.value = true
  try {
    const res = await axios.post('/api/videos', form.value)
    alert('上传成功！视频 ID: ' + res.data.id)
    window.location.href = `/videos/${res.data.id}`
  } catch (err) {
    alert('上传失败：' + (err.response?.data || err.message))
  } finally {
    loading.value = false
  }
}

onMounted(loadCategories)
</script>

<style scoped>
.upload-container {
  max-width: 600px;
  margin: 0 auto;
}

.upload-container h1 {
  margin-bottom: 2rem;
  text-align: center;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #333;
}

.form-input, .form-select {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.form-input:focus, .form-select:focus {
  outline: none;
  border-color: #fb7299;
}

.form-group textarea {
  resize: vertical;
}

button[type="submit"] {
  width: 100%;
}

.cancel-link {
  display: inline-block;
  margin-top: 1rem;
  color: #666;
}

.cancel-link:hover {
  color: #fb7299;
}
</style>
