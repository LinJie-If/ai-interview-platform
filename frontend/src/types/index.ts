// Resume：简历
export interface Resume {
  id: number
  fileName: string
  originalName: string
  fileSize: number
  status: string
  createdAt: string
}

// Interview：面试
export interface Interview {
  id: number
  resumeId: number
  position: string
  status: string
  result: string
  startTime: string
  endTime: string
  createdAt: string
}


// ChatMessage：聊天消息
export interface ChatMessage {
  role: 'interviewer' | 'user'
  content: string
  timestamp: number
}
