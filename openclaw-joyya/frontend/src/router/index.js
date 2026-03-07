import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Home from '../views/Home.vue'
import VideoDetail from '../views/VideoDetail.vue'
import VideoUpload from '../views/VideoUpload.vue'
import UserProfile from '../views/UserProfile.vue'
import HotVideos from '../views/HotVideos.vue'

const routes = [
  { path: '/', name: 'Home', component: Home, meta: { requiresAuth: true } },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/videos/hot', name: 'Hot', component: HotVideos, meta: { requiresAuth: true } },
  { path: '/videos/:id', name: 'VideoDetail', component: VideoDetail, meta: { requiresAuth: true } },
  { path: '/videos/upload', name: 'Upload', component: VideoUpload, meta: { requiresAuth: true } },
  { path: '/videos/my', name: 'MyVideos', component: UserProfile, meta: { requiresAuth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else if (to.path === '/login' && token) {
    next('/')
  } else {
    next()
  }
})

export default router
