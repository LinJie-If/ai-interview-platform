import request from './request'
import type { ApiResult, Interview } from '../types'

export function createInterview(data: Partial<Interview>): Promise<ApiResult<Interview>> {
  return request.post('/interview', data)
}

export function getInterviewList(): Promise<ApiResult<Interview[]>> {
  return request.get('/interview/list')
}

export function getInterviewsByResumeId(resumeId: number): Promise<ApiResult<Interview[]>> {
  return request.get(`/interview/resume/${resumeId}`)
}

export function getInterviewById(id: number): Promise<ApiResult<Interview>> {
  return request.get(`/interview/${id}`)
}

export function updateInterview(id: number, data: Partial<Interview>): Promise<ApiResult<Interview>> {
  return request.put(`/interview/${id}`, data)
}

export function deleteInterview(id: number): Promise<ApiResult<void>> {
  return request.delete(`/interview/${id}`)
}
