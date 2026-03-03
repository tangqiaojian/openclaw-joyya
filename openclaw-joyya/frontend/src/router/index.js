import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import VideoDetail from '../views/VideoDetail.vue'
import VideoUpload from '../views/VideoUpload.vue'
import UserProfile from '../views/UserProfile.vue'
import HotVideos from '../views/HotVideos.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/videos/hot', name: 'Hot', component: HotVideos },
  { path: '/videos/:id', name: 'VideoDetail', component: VideoDetail },
  { path: '/videos/upload', name: 'Upload', component: VideoUpload },
  { path: '/videos/my', name: 'MyVideos', component: UserProfile }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
