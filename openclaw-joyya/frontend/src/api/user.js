import request from './request'

/**
 * 用户登录
 * @param {Object} credentials - 登录凭证
 * @param {string} credentials.username - 用户名
 * @param {string} credentials.password - 密码
 * @returns {Promise}
 */
export function login(credentials) {
  return request.post('/auth/login', credentials)
}

/**
 * 用户注册
 * @param {Object} user - 用户信息
 * @param {string} user.username - 用户名
 * @param {string} user.email - 邮箱
 * @param {string} user.password - 密码
 * @returns {Promise}
 */
export function register(user) {
  return request.post('/auth/register', user)
}

/**
 * 获取当前用户信息
 * @returns {Promise}
 */
export function getCurrentUser() {
  return request.get('/auth/profile')
}

/**
 * 更新用户信息
 * @param {Object} userData - 用户数据
 * @returns {Promise}
 */
export function updateProfile(userData) {
  return request.put('/auth/profile', userData)
}
