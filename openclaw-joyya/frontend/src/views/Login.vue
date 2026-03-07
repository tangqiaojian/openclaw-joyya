<template>
  <div class="login-page">
    <!-- 动态粒子背景 -->
    <div class="particles-background">
      <div class="particle" v-for="n in 50" :key="n" :style="getParticleStyle(n)"></div>
    </div>
    
    <!-- 主内容区 -->
    <div class="main-container">
      <!-- Logo 区域 -->
      <div class="logo-section">
        <div class="logo-icon">
          <span class="icon-play">▶️</span>
        </div>
        <h1 class="logo-text">Joyya 视频</h1>
        <p class="logo-slogan">你的专属视频平台</p>
      </div>
      
      <!-- 登录表单 -->
      <div class="login-card">
        <h2 class="card-title">欢迎登录</h2>
        
        <div class="form-section">
          <!-- 用户名输入 -->
          <div class="input-wrapper">
            <div class="input-icon-wrapper">
              <el-icon class="input-icon"><User /></el-icon>
            </div>
            <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              size="large"
              clearable
              @input="validateUsername"
            />
          </div>
          <div v-if="usernameError" class="error-text">{{ usernameError }}</div>
          
          <!-- 密码输入 -->
          <div class="input-wrapper">
            <div class="input-icon-wrapper">
              <el-icon class="input-icon"><Lock /></el-icon>
            </div>
            <el-input
              v-model="form.password"
              type="password"
              placeholder="请输入密码"
              size="large"
              show-password
              clearable
              @keyup.enter="handleLogin"
              @input="validatePassword"
            />
          </div>
          <div v-if="passwordError" class="error-text">{{ passwordError }}</div>
          
          <!-- 登录按钮 -->
          <button 
            class="login-btn" 
            @click="handleLogin"
            :disabled="loading"
          >
            <div class="btn-text">
              <el-icon v-if="loading" class="icon-spinning"><Loading /></el-icon>
              <span v-else>立即登录</span>
            </div>
          </button>
          
          <!-- 注册链接 -->
          <div class="register-section">
            <span class="register-text">还没有账号？</span>
            <router-link to="/register" class="register-link">立即注册</router-link>
          </div>
          
          <!-- 测试账号 -->
          <div class="test-account">
            <el-icon class="test-icon"><InfoFilled /></el-icon>
            <span class="test-text">测试账号：admin / 123456</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, Loading, InfoFilled } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const form = reactive({
  username: '',
  password: ''
})

const usernameError = ref('')
const passwordError = ref('')
const loading = ref(false)

// 粒子动画
const getParticleStyle = (index) => ({
  '--delay': `${Math.random() * 5}s`,
  '--duration': `${5 + Math.random() * 10}s`,
  '--size': `${4 + Math.random() * 6}px`,
  '--left': `${Math.random() * 100}%`,
  '--top': `${Math.random() * 100}%`
})

// 验证
const validateUsername = () => {
  if (!form.username) return
  if (form.username.length < 3) {
    usernameError.value = '用户名至少 3 个字符'
  } else if (form.username.length > 20) {
    usernameError.value = '用户名最多 20 个字符'
  } else {
    usernameError.value = ''
  }
}

const validatePassword = () => {
  if (!form.password) return
  if (form.password.length < 6) {
    passwordError.value = '密码至少 6 个字符'
  } else if (form.password.length > 20) {
    passwordError.value = '密码最多 20 个字符'
  } else {
    passwordError.value = ''
  }
}

// 登录处理
const handleLogin = async () => {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入完整的登录信息')
    return
  }
  if (usernameError.value || passwordError.value) {
    ElMessage.error('请填写正确的信息')
    return
  }
  
  loading.value = true
  
  try {
    await userStore.login(form)
    
    ElMessage.success({
      message: '登录成功！即将跳转...',
      duration: 1500
    })
    
    router.push('/')
  } catch (error) {
    ElMessage.error({
      message: error.message || '登录失败，请重试',
      duration: 3000
    })
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
@import '../style/theme.css';

/* 页面容器 */
.login-page {
  position: relative;
  min-height: 100vh;
  overflow: hidden;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
}

/* 粒子背景 */
.particles-background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
  overflow: hidden;
}

.particle {
  position: absolute;
  width: var(--size);
  height: var(--size);
  border-radius: 50%;
  background: radial-gradient(circle, rgba(251, 114, 153, 0.8), rgba(139, 92, 246, 0.6));
  box-shadow: 0 0 20px rgba(251, 114, 153, 0.5), 0 0 40px rgba(139, 92, 246, 0.3);
  animation: particleFloat calc(var(--duration)) ease-in-out infinite;
  animation-delay: var(--delay);
  opacity: 0.3;
}

@keyframes particleFloat {
  0%, 100% {
    transform: translate(0, 0) scale(1);
    opacity: 0.3;
  }
  25% {
    transform: translate(calc(var(--left) + 30px), calc(var(--top) - 50px)) scale(1.2);
    opacity: 0.8;
  }
  50% {
    transform: translate(calc(var(--left) - 20px), calc(var(--top) - 30px)) scale(0.9);
    opacity: 0.5;
  }
  75% {
    transform: translate(calc(var(--left) + 40px), calc(var(--top) - 70px)) scale(1.1);
    opacity: 0.6;
  }
}

/* 主容器 */
.main-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 20px;
  position: relative;
  z-index: 1;
}

/* Logo 区域 */
.logo-section {
  text-align: center;
  margin-bottom: 40px;
  animation: fadeInDown 0.8s ease-out;
}

.logo-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  border-radius: 50%;
  box-shadow: 0 10px 30px rgba(251, 114, 153, 0.3);
  margin-bottom: 16px;
}

.icon-play {
  font-size: 40px;
  color: white;
  filter: drop-shadow(0 2px 4px rgba(0,0,0,0.2));
}

.logo-text {
  font-size: var(--font-size-3xl);
  font-weight: 700;
  background: linear-gradient(45deg, var(--primary-color), var(--secondary-color), var(--accent-color));
  background-size: 200% 200%;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  animation: gradientMove 3s ease infinite;
  margin: 0;
}

@keyframes gradientMove {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

.logo-slogan {
  font-size: var(--font-size-md);
  color: var(--text-tertiary);
  margin: 8px 0 0 0;
}

/* 登录卡片 */
.login-card {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-radius: var(--radius-xl);
  padding: 40px;
  width: 100%;
  max-width: 450px;
  box-shadow: 
    var(--shadow-xl),
    0 0 0 1px rgba(255, 255, 255, 0.1),
    inset 0 0 60px rgba(251, 114, 153, 0.05);
  animation: slideUp 0.6s ease-out;
  position: relative;
  overflow: hidden;
}

.login-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(251, 114, 153, 0.1), transparent);
  animation: borderGlow 3s ease-in-out infinite;
}

@keyframes borderGlow {
  0%, 100% { left: -100%; }
  50% { left: 100%; }
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
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

.card-title {
  text-align: center;
  font-size: var(--font-size-2xl);
  font-weight: 600;
  color: var(--text-primary);
  margin: 0 0 32px 0;
  position: relative;
}

.card-title::after {
  content: '';
  position: absolute;
  bottom: -12px;
  left: 50%;
  transform: translateX(-50%);
  width: 60px;
  height: 3px;
  background: linear-gradient(90deg, var(--primary-color), var(--secondary-color));
  border-radius: var(--radius-full);
  box-shadow: 0 0 10px rgba(251, 114, 153, 0.5);
}

/* 表单部分 */
.form-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 输入框 */
.input-wrapper {
  position: relative;
}

.input-icon-wrapper {
  position: absolute;
  left: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: var(--primary-color);
  font-size: 18px;
  pointer-events: none;
  z-index: 1;
}

:deep(.el-input__wrapper) {
  background: rgba(255, 255, 255, 0.05) !important;
  border: 1px solid rgba(255, 255, 255, 0.1) !important;
  border-radius: var(--radius-lg) !important;
  padding: 0 !important;
  padding-left: 52px !important;
  box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.05) !important;
  backdrop-filter: blur(10px);
  transition: all var(--transition-base);
}

:deep(.el-input__wrapper:hover) {
  border-color: var(--primary-color) !important;
  box-shadow: 0 0 20px rgba(251, 114, 153, 0.3) !important;
}

:deep(.el-input__wrapper.is-focus) {
  border-color: var(--primary-color) !important;
  box-shadow: 0 0 25px rgba(251, 114, 153, 0.4), 0 0 50px rgba(251, 114, 153, 0.2) !important;
}

:deep(.el-input__inner) {
  color: var(--text-primary) !important;
  font-size: var(--font-size-md) !important;
  height: 56px !important;
}

:deep(.el-input__inner::placeholder) {
  color: var(--text-tertiary) !important;
}

:deep(.el-icon) {
  color: var(--primary-color) !important;
}

.error-text {
  color: var(--error-color);
  font-size: var(--font-size-xs);
  margin-top: 4px;
  animation: shake 0.3s ease-in-out;
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  25% { transform: translateX(-5px); }
  75% { transform: translateX(5px); }
}

/* 登录按钮 */
.login-btn {
  position: relative;
  width: 100%;
  height: 56px;
  border: none;
  border-radius: var(--radius-lg);
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--secondary-color) 100%);
  color: white;
  font-size: var(--font-size-lg);
  font-weight: 600;
  cursor: pointer;
  overflow: hidden;
  transition: all var(--transition-base);
  box-shadow: 0 8px 24px rgba(251, 114, 153, 0.3);
  margin-top: 8px;
}

.login-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(251, 114, 153, 0.5);
}

.login-btn:active:not(:disabled) {
  transform: translateY(0);
}

.login-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-text {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.icon-spinning {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

/* 注册链接 */
.register-section {
  text-align: center;
  color: var(--text-secondary);
  font-size: var(--font-size-sm);
}

.register-link {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 600;
  margin-left: 4px;
  position: relative;
  transition: all var(--transition-base);
}

.register-link::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 0;
  height: 2px;
  background: linear-gradient(90deg, var(--primary-color), var(--secondary-color));
  transition: width var(--transition-base);
}

.register-link:hover {
  color: var(--primary-color);
}

.register-link:hover::after {
  width: 100%;
}

/* 测试账号 */
.test-account {
  margin-top: 8px;
  padding: 12px 20px;
  background: rgba(251, 114, 153, 0.1);
  border-radius: var(--radius-lg);
  color: var(--primary-color);
  font-size: var(--font-size-xs);
  display: inline-flex;
  align-items: center;
  gap: 8px;
  border: 1px solid rgba(251, 114, 153, 0.2);
  animation: fadeIn 0.5s ease 0.3s both;
  margin: 8px auto 0;
}

.test-icon {
  font-size: 14px;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 响应式 */
@media (max-width: 480px) {
  .login-card {
    padding: 30px 20px;
  }
  
  .logo-text {
    font-size: var(--font-size-xl);
  }
}
</style>
