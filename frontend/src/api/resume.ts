import request from './request'
import type { ApiResult, Resume } from '../types'

export function uploadResume(file: File): Promise<ApiResult<Resume>> {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/resume/upload', formData)
}

export function getResumeList(): Promise<ApiResult<Resume[]>> {
  return request.get('/resume/list')
}

export function getResumeById(id: number): Promise<ApiResult<Resume>> {
  return request.get(`/resume/${id}`)
}

export function deleteResume(id: number): Promise<ApiResult<void>> {
  return request.delete(`/resume/${id}`)
}
