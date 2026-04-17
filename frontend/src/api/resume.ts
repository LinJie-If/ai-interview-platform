import { del, get, post } from './request'
import type { Resume } from '../types'

export async function uploadResume(file: File): Promise<Resume> {
  const formData = new FormData()
  formData.append('file', file)

  const res = await post<Resume>('/resume/upload', formData)
  return res.data
}

export async function getResumeList(): Promise<Resume[]> {
  const res = await get<Resume[]>('/resume/list')
  return res.data
}

export async function getResumeById(id: number): Promise<Resume> {
  const res = await get<Resume>(`/resume/${id}`)
  return res.data
}

export async function deleteResume(id: number): Promise<void> {
  const res = await del<void>(`/resume/${id}`)
  return res.data
}
