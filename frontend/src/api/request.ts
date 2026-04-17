import axios, {
  type AxiosError,
  type AxiosRequestConfig,
  type AxiosResponse,
} from 'axios'
import type { HttpResult } from '../types/http'

const SUCCESS_CODE = 200

// HTTP 请求的统一封装模块，方便后续的接口调用


const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
})

// 配置 axios 实例，设置 baseURL 和 timeout
  // axios.create()：创建一个独立的 axios 实例，而不是直接用全局的 axios。好处是可以针对不同的后端服务创建不同的实例，互不影响。
  // baseURL: '/api'：所有通过这个实例发出的请求，URL 都会自动加上 /api 前缀。比如你调用 request.get('/users')，实际发出的请求是 GET /api/users。
  // timeout: 10000：请求超时时间设为 10 秒（10000 毫秒）。如果 10 秒内服务器没有响应，
    // 请求就会自动报错。
    
//request-请求
request.interceptors.request.use(
  (config) => {
    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)


  // 成功回调 (res) => res.data
  // axios 的原始响应对象 res 包含很多元信息（status、headers、config 等），实际业务只关心 res.data（即服务端返回的 JSON 数据）。
  // 这个拦截器直接返回 res.data，这样在业务代码中调用 request.get(...) 拿到的就是数据本身，不用每次都写 .data。

  // 失败回调 (err) => { ... }
  // 当请求失败时（网络错误、HTTP 4xx/5xx 等），进入这个回调。
  // 优先取服务端返回的错误信息 err.response?.data?.message，如果没有则使用默认提示 '网络异常，请稍后重试'。
  // 用 Promise.reject() 把错误继续抛出去，让调用方可以用 .catch() 或 try/catch 捕获。

// response-响应
// 响应拦截器，统一处理响应数据
request.interceptors.response.use(
  (response: AxiosResponse<HttpResult<unknown>>) => {
    const result = response.data

    if (!result) {
      return Promise.reject(new Error('响应数据为空'))
    }

    if (result.code !== SUCCESS_CODE) {
      return Promise.reject(new Error(result.message || '请求失败'))
    }

    return response
  },
  (error: AxiosError<{ message?: string }>) => {
    const message =
      error.response?.data?.message ||
      error.message ||
      '网络异常，请稍后重试'

    return Promise.reject(new Error(message))
  },
)




// get、post、put、del 方法 
export async function get<T>(
  url: string,
  config?: AxiosRequestConfig,
): Promise<HttpResult<T>> {
  const response = await request.get<HttpResult<T>>(url, config)
  return response.data
}

export async function post<T>(
  url: string,
  data?: unknown,
  config?: AxiosRequestConfig,
): Promise<HttpResult<T>> {
  const response = await request.post<HttpResult<T>>(url, data, config)
  return response.data
}

export async function put<T>(
  url: string,
  data?: unknown,
  config?: AxiosRequestConfig,
): Promise<HttpResult<T>> {
  const response = await request.put<HttpResult<T>>(url, data, config)
  return response.data
}

export async function del<T>(
  url: string,
  config?: AxiosRequestConfig,
): Promise<HttpResult<T>> {
  const response = await request.delete<HttpResult<T>>(url, config)
  return response.data
}

export default request
