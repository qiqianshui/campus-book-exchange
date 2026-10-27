import axios from 'axios'

// 统一的请求实例：所有请求自动带上 /api 前缀，并附加登录 token
const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 请求拦截器：每次发请求前，把 localStorage 里的 token 塞进 Authorization 头
request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// 响应拦截器：后端返回 401 / 403（未登录、token 失效、无权限）就清掉本地登录态，跳回登录页
request.interceptors.response.use(
  res => res.data,
  err => {
    if (err.response && (err.response.status === 401 || err.response.status === 403)) {
      localStorage.removeItem('token')
      localStorage.removeItem('username')
      localStorage.removeItem('role')
      window.location.href = '/login'
    }
    return Promise.reject(err)
  }
)

export default request
