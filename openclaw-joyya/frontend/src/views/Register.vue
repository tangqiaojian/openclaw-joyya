<template>
  <div class="register-page">
    <!-- 动态背景 -->
    <div class="particles-background">
      <div class="particle" v-for="n in 30" :key="n" :style="getParticleStyle(n)"></div>
    </div>
    
    <div class="main-container">
      <!-- Logo 区域 -->
      <div class="logo-section">
        <div class="logo-icon">
          <span class="icon-play">▶️</span>
        </div>
        <h1 class="logo-text">Joyya 视频</h1>
        <p class="logo-slogan">加入我们的视频社区</p>
      </div>
      
      <!-- 注册卡片 -->
      <div class="register-card">
        <h2 class="card-title">创建账号</h2>
        
        <div class="form-section">
          <!-- 用户名 -->
          <div class="input-wrapper">
            <div class="input-icon-wrapper">
              <el-icon class="input-icon"><User /></el-icon>
            </div>
            <el-input
              v-model="form.username"
              placeholder="用户名 (3-20 个字符)"
              size="large"
              clearable
              @input="validateUsername"
            />
          </div>
          <div v-if="usernameError" class="error-text">{{ usernameError }}</div>
          
          <!-- 邮箱 -->
          <div class="input-wrapper">
            <div class="input-icon-wrapper">
              <el-icon class="input-icon"><Message /></el-icon>
            </div>
            <el-input
              v-model="form.email"
              placeholder="电子邮箱"
              size="large"
              clearable
              @input="validateEmail"
            />
          </div>
          <div v-if="emailError" class="error-text">{{ emailError }}</div>
          
          <!-- 密码 -->
          <div class="input-wrapper">
            <div class="input-icon-wrapper">
              <el-icon class="input-icon"><Lock /></el-icon>
            </div>
            <el-input
              v-model="form.password"
              type="password"
              placeholder="密码 (6-20 个字符)"
              size="large"
              show-password
              clearable
              @keyup.enter="handleRegister"
              @input="validatePassword"
            />
          </div>
          <div v-if="passwordError" class="error-text">{{ passwordError }}</div>
          
          <!-- 确认密码 -->
          <div class="input-wrapper">
            <div class="input-icon-wrapper">
              <el-icon class="input-icon"><Lock /></el-icon>
            </div>
            <el-input
              v-model="form.confirmPassword"
              type="password"
              placeholder="确认密码"
              size="large"
              show-password
              clearable
              @keyup.enter="handleRegister"
              @input="validateConfirmPassword"
            />
          </div>
          <div v-if="confirmError" class="error-text">{{ confirmError }}</div>
          
          <!-- 注册按钮 -->
          <button 
            class="register-btn" 
            @click="handleRegister"
            :disabled="loading"
          >
            <div class="btn-text">
              <el-icon v-if="loading" class="icon-spinning"><Loading /></el-icon>
              <span v-else>立即注册</span>
            </div>
          </button>
          
          <!-- 登录链接 -->
          <div class="login-section">
            <span class="login-text">已有账号？</span>
            <router-link to="/login" class="login-link">立即登录</router-link>
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
import { User, Lock, Message, Loading } from '@element-plus/icons-vue'
import { registerUser } from '@/api/user'

const router = useRouter()

const form = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const usernameError = ref('')
const emailError = ref('')
const passwordError = ref('')
const confirmError = ref('')
const loading = ref(false)

// 粒子动画
const getParticleStyle = (index) => ({
  '--delay': `${Math.random() * 5}s`,
  '--duration': `${5 + Math.random() * 10}s`,
  '--size': `${3 + Math.random() * 5}px`,
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
  } else if (!/^[a-zA-Z0-9_]+$/.test(form.username)) {
    usernameError.value = '用户名只能包含字母、数字和下划线'
  } else {
    usernameError.value = ''
  }
}

const validateEmail = () => {
  if (!form.email) return
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRegex.test(form.email)) {
    emailError.value = '请输入有效的邮箱地址'
  } else {
    emailError.value = ''
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

const validateConfirmPassword = () => {
  if (form.password !== form.confirmPassword) {
    confirmError.value = '两次输入的密码不一致'
  } else if (!form.confirmPassword) {
    confirmError.value = '请输入确认密码'
  } else {
    confirmError.value = ''
  }
}

// 注册处理
const handleRegister = async () => {
  if (!form.username || !form.email || !form.password) {
    ElMessage.warning('请输入完整的注册信息')
    return
  }
  if (usernameError.value || emailError.value || passwordError.value || confirmError.value) {
    ElMessage.error('请填写正确的信息')
    return
  }
  
  loading.value = true
  
  try {
    await registerUser(form)
    
    ElMessage.success({
      message: '注册成功！正在跳转...',
      duration: 1500
    })
    
    router.push('/login')
  } catch (error) {
    ElMessage.error({
      message: error.message || '注册失败，请重试',
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
.register-page {
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

.logo-slogan {
  font-size: var(--font-size-md);
  color: var(--text-tertiary);
  margin: 8px 0 0 0;
}

/* 注册卡片 */
.register-card {
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

.register-card::before {
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

.card-title {
  text-align: center;
  font-size: var(--font-size-2xl);
  font-weight: 600;
  color: var(--text-primary);
  margin: 0 0 32px 0;
  position: relative;
  animation: fadeInUp 0.8s ease-out 0.2s both;
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
  animation: pulseRing 2s ease-in-out infinite;
}

@keyframes pulseRing {
  0% { box-shadow: 0 0 0 0 rgba(251, 114, 153, 0.4); }
  70% { box-shadow: 0 0 0 10px rgba(251, 114, 153, 0); }
  100% { box-shadow: 0 0 0 0 rgba(251, 114, 153, 0); }
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

/* 注册按钮 */
.register-btn {
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

.register-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(251, 114, 153, 0.5);
}

.register-btn:active:not(:disabled) {
  transform: translateY(0);
}

.register-btn:disabled {
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

/* 登录链接 */
.login-section {
  text-align: center;
  color: var(--text-secondary);
  font-size: var(--font-size-sm);
  margin-top: 16px;
}

.login-link {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 600;
  margin-left: 4px;
  position: relative;
  transition: all var(--transition-base);
}

.login-link::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 0;
  height: 2px;
  background: linear-gradient(90deg, var(--primary-color), var(--secondary-color));
  transition: width var(--transition-base);
}

.login-link:hover {
  color: var(--primary-color);
}

.login-link:hover::after {
  width: 100%;
}

/* 响应式 */
@media (max-width: 480px) {
  .register-card {
    padding: 30px 20px;
  }
  
  .logo-text {
    font-size: var(--font-size-xl);
  }
}
</style>
