import { get } from './request'

export interface HealthInfo {
  status: string
  service: string
}

export async function getHealth(): Promise<HealthInfo> {
  const res = await get<HealthInfo>('/health')
  return res.data
}
