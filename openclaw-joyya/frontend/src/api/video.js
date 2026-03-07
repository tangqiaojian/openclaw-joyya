import request from './request'

/**
 * 获取热门视频
 * @param {number} page - 页码
 * @param {number} size - 每页数量
 * @returns {Promise}
 */
export function getHotVideos(page = 0, size = 12) {
  return request.get('/videos/hot', { params: { page, size } })
}

/**
 * 获取最新视频
 * @param {number} page - 页码
 * @param {number} size - 每页数量
 * @returns {Promise}
 */
export function getLatestVideos(page = 0, size = 12) {
  return request.get('/videos/latest', { params: { page, size } })
}

/**
 * 获取所有视频
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
export function getAllVideos(params = {}) {
  return request.get('/videos', { params })
}

/**
 * 根据 ID 获取视频
 * @param {number} id - 视频 ID
 * @returns {Promise}
 */
export function getVideoById(id) {
  return request.get(`/videos/${id}`)
}

/**
 * 创建视频
 * @param {Object} video - 视频数据
 * @returns {Promise}
 */
export function createVideo(video) {
  return request.post('/videos', video)
}

/**
 * 获取分类列表
 * @returns {Promise}
 */
export function getCategories() {
  return request.get('/categories')
}
