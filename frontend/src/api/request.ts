import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 30000,
})

request.interceptors.response.use(
  (res) => res.data,
  (err) => {
    const message = err.response?.data?.message || '网络异常，请稍后重试'
    return Promise.reject(new Error(message))
  },
)

export default request
