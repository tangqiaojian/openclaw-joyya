import { defineStore } from 'pinia'
import axios from 'axios'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    token: localStorage.getItem('token') || null,
    isAuthenticated: false
  }),

  getters: {
    isLoggedIn: (state) => state.isAuthenticated,
    currentUserId: (state) => state.user?.id
  },

  actions: {
    setToken(token) {
      this.token = token
      localStorage.setItem('token', token)
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },

    setUser(user) {
      this.user = user
      this.isAuthenticated = true
    },

    logout() {
      this.user = null
      this.token = null
      this.isAuthenticated = false
      localStorage.removeItem('token')
      delete axios.defaults.headers.common['Authorization']
    },

    async login(username, password) {
      try {
        const res = await axios.post('/api/auth/login', { username, password })
        this.setToken(res.data.token)
        this.setUser(res.data.user)
        return res.data
      } catch (error) {
        throw error
      }
    },

    async register(userData) {
      try {
        const res = await axios.post('/api/auth/register', userData)
        this.setToken(res.data.token)
        this.setUser(res.data.user)
        return res.data
      } catch (error) {
        throw error
      }
    }
  }
})

export const useVideoStore = defineStore('video', {
  state: () => ({
    videos: [],
    hotVideos: [],
    loading: false,
    error: null
  }),

  actions: {
    async loadVideos(params = {}) {
      this.loading = true
      this.error = null
      try {
        const res = await axios.get('/api/videos', { params })
        this.videos = res.data.content
        this.loading = false
      } catch (error) {
        this.error = error.message
        this.loading = false
      }
    },

    async loadHotVideos() {
      try {
        const res = await axios.get('/api/videos/hot')
        this.hotVideos = res.data.content
      } catch (error) {
        console.error('加载热门视频失败:', error)
      }
    }
  }
})
