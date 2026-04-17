import { del, get, post, put } from './request'
import type { Interview } from '../types'


// 创建面试
export async function createInterview(data: Partial<Interview>): Promise<Interview> {
  const res = await post<Interview>('/interview', data)
  return res.data
}

// 获取面试列表
export async function getInterviewList(): Promise<Interview[]> {
  const res = await get<Interview[]>('/interview/list')
  return res.data
}

// 获取面试列表
export async function getInterviewsByResumeId(resumeId: number): Promise<Interview[]> {
  const res = await get<Interview[]>(`/interview/resume/${resumeId}`)
  return res.data
}

// 获取面试详情
export async function getInterviewById(id: number): Promise<Interview> {
  const res = await get<Interview>(`/interview/${id}`)
  return res.data
}

// 更新面试
export async function updateInterview(
  id: number,
  data: Partial<Interview>,
): Promise<Interview> {
  const res = await put<Interview>(`/interview/${id}`, data)
  return res.data
}

// 删除面试
export async function deleteInterview(id: number): Promise<void> {
  const res = await del<void>(`/interview/${id}`)
  return res.data
}
