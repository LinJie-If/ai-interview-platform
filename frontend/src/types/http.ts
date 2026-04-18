// 网络请求接口的返回值？
// HttpResult<T>：和后端 Result<T> 对齐，给 request.ts 用
export interface HttpResult<T> {
  code: number
  message: string
  data: T
}

// PageQuery：以后列表页查分页时直接复用
export interface PageQuery {
  pageNum?: number
  pageSize?: number
}

// PageResult<T>：如果后面列表接口做成分页，前端不用再改类型结构

export interface PageResult<T> {
  list: T[]
  total: number
}
