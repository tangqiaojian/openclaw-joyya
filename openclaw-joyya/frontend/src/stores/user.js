import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login as loginApi, getCurrentUser, updateProfile } from '@/api/user'

export const useUserStore = defineStore('user', () => {
  // 状态
  const userInfo = ref(null)
  const token = ref(localStorage.getItem('token'))

  // 计算属性
  const isLoggedIn = computed(() => !!token.value)

  // 动作
  async function login(credentials) {
    try {
      const res = await loginApi(credentials)
      
      if (res.success) {
        token.value = res.token
        userInfo.value = res.data
        localStorage.setItem('token', res.token)
        localStorage.setItem('username', credentials.username)
        
        return true
      } else {
        throw new Error(res.message || '登录失败')
      }
    } catch (error) {
      throw error
    }
  }

  async function logout() {
    token.value = null
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('username')
  }

  async function fetchProfile() {
    try {
      const res = await getCurrentUser()
      userInfo.value = res.data || res
      return true
    } catch (error) {
      console.error('获取用户信息失败:', error)
      return false
    }
  }

  async function updateUserProfile(userData) {
    try {
      const res = await updateProfile(userData)
      userInfo.value = res.data || res
      return true
    } catch (error) {
      throw error
    }
  }

  return {
    userInfo,
    token,
    isLoggedIn,
    login,
    logout,
    fetchProfile,
    updateUserProfile
  }
})
