<template>
  <div class="detail-page">
    <div v-if="loading" class="loading-state">
      <span class="loading-spinner"></span>
      加载中...
    </div>

    <template v-else-if="resume">
      <!-- 顶部返回 -->
      <div class="back-row">
        <button class="btn-back" @click="$router.push('/resumes')">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="15 18 9 12 15 6"/>
          </svg>
          返回简历库
        </button>
      </div>

      <!-- 简历信息卡片 -->
      <div class="info-card">
        <div class="info-header">
          <div class="file-icon-lg">
            <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="#5B5BD6" stroke-width="1.5">
              <path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z"/>
              <polyline points="14 2 14 8 20 8"/>
              <line x1="16" y1="13" x2="8" y2="13"/>
              <line x1="16" y1="17" x2="8" y2="17"/>
            </svg>
          </div>
          <div class="info-title-area">
            <h1 class="info-name">{{ resume.originalName }}</h1>
            <span class="info-status" :class="`status-${resume.status?.toLowerCase()}`">
              {{ statusText(resume.status) }}
            </span>
          </div>
        </div>

        <div class="info-grid">
          <div class="info-item">
            <span class="info-label">文件名称</span>
            <span class="info-value">{{ resume.originalName }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">文件大小</span>
            <span class="info-value">{{ formatFileSize(resume.fileSize) }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">上传时间</span>
            <span class="info-value">{{ formatDateTime(resume.createdAt) }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">文件状态</span>
            <span class="info-value">{{ statusText(resume.status) }}</span>
          </div>
        </div>

        <div class="info-actions">
          <button class="btn-primary" @click="startInterview">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M17 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2"/>
              <circle cx="9" cy="7" r="4"/>
              <path d="M23 21v-2a4 4 0 00-3-3.87"/>
              <path d="M16 3.13a4 4 0 010 7.75"/>
            </svg>
            开始模拟面试
          </button>
          <button class="btn-danger" @click="handleDelete">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polyline points="3 6 5 6 21 6"/>
              <path d="M19 6v14a2 2 0 01-2 2H7a2 2 0 01-2-2V6m3 0V4a2 2 0 012-2h4a2 2 0 012 2v2"/>
            </svg>
            删除简历
          </button>
        </div>
      </div>

      <!-- 面试历史 -->
      <div class="section">
        <h2 class="section-title">面试记录</h2>
        <div v-if="interviews.length === 0" class="empty-section">
          <p>暂无面试记录，点击「开始模拟面试」创建第一次面试</p>
        </div>
        <div v-else class="interview-list">
          <div
            v-for="item in interviews"
            :key="item.id"
            class="interview-item"
            @click="$router.push(`/interview/${resume.id}?interviewId=${item.id}`)"
          >
            <div class="interview-info">
              <p class="interview-position">{{ item.position || '综合面试' }}</p>
              <p class="interview-meta">
                {{ formatDateTime(item.createdAt) }}
                <span v-if="item.status" class="interview-status" :class="`istatus-${item.status.toLowerCase()}`">
                  {{ interviewStatusText(item.status) }}
                </span>
              </p>
            </div>
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#D1D5DB" stroke-width="2">
              <polyline points="9 18 15 12 9 6"/>
            </svg>
          </div>
        </div>
      </div>
    </template>

    <div v-else class="error-state">
      <p>简历不存在或已被删除</p>
      <button class="btn-primary" @click="$router.push('/resumes')">返回简历库</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getResumeById, deleteResume } from '../../api/resume'
import { getInterviewsByResumeId } from '../../api/interview'
import type { Resume, Interview } from '../../types'

const route = useRoute()
const router = useRouter()

const resume = ref<Resume | null>(null)
const interviews = ref<Interview[]>([])
const loading = ref(true)

onMounted(async () => {
  const id = Number(route.params.id)
  if (!id) {
    loading.value = false
    return
  }
  try {
    const [resumeRes, interviewRes] = await Promise.all([
      getResumeById(id),
      getInterviewsByResumeId(id),
    ])
    resume.value = resumeRes.data
    interviews.value = interviewRes.data ?? []
  } catch {
    // silently handle
  } finally {
    loading.value = false
  }
})

function startInterview() {
  if (resume.value) {
    router.push(`/interview/${resume.value.id}`)
  }
}

async function handleDelete() {
  if (!resume.value) return
  if (!confirm('确定要删除该简历吗？此操作不可恢复。')) return
  try {
    await deleteResume(resume.value.id)
    router.push('/resumes')
  } catch {
    alert('删除失败，请稍后重试')
  }
}

function formatFileSize(bytes: number): string {
  if (!bytes) return '0 B'
  if (bytes < 1024) return bytes + ' B'
  if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB'
  return (bytes / (1024 * 1024)).toFixed(1) + ' MB'
}

function formatDateTime(dateStr: string): string {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`
}

function statusText(status: string): string {
  const map: Record<string, string> = {
    UPLOADED: '已上传',
    ANALYZING: '分析中',
    ANALYZED: '已分析',
    FAILED: '分析失败',
  }
  return map[status] || status || '未知'
}

function interviewStatusText(status: string): string {
  const map: Record<string, string> = {
    PENDING: '待开始',
    IN_PROGRESS: '进行中',
    COMPLETED: '已完成',
    CANCELLED: '已取消',
  }
  return map[status] || status
}
</script>

<style scoped>
.detail-page {
  padding: 32px 40px;
  max-width: 800px;
  margin: 0 auto;
}

/* Loading & Error */
.loading-state {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 120px 0;
  color: #9CA3AF;
  font-size: 14px;
}

.loading-spinner {
  width: 18px;
  height: 18px;
  border: 2px solid #E8E9FF;
  border-top-color: #5B5BD6;
  border-radius: 50%;
  animation: spin 0.6s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.error-state {
  text-align: center;
  padding: 120px 0;
  color: #9CA3AF;
  font-size: 14px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

/* Back */
.back-row {
  margin-bottom: 20px;
}

.btn-back {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 6px 12px;
  background: transparent;
  border: none;
  border-radius: 6px;
  font-size: 13px;
  color: #6B7280;
  cursor: pointer;
  transition: color 0.15s, background 0.15s;
}

.btn-back:hover {
  color: #5B5BD6;
  background: #F5F5FF;
}

/* Info Card */
.info-card {
  background: #fff;
  border-radius: 14px;
  padding: 28px;
  box-shadow: 0 2px 12px rgba(91, 91, 214, 0.06);
}

.info-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
  padding-bottom: 20px;
  border-bottom: 1px solid #F0F0F5;
}

.file-icon-lg {
  width: 56px;
  height: 56px;
  background: #F0F0FF;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.info-title-area {
  flex: 1;
  min-width: 0;
}

.info-name {
  font-size: 20px;
  font-weight: 600;
  color: #1A1A2E;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.4;
}

.info-status {
  display: inline-block;
  font-size: 12px;
  font-weight: 500;
  padding: 2px 10px;
  border-radius: 20px;
  margin-top: 4px;
}

.status-uploaded { background: #EEF2FF; color: #6366F1; }
.status-analyzing { background: #FEF3C7; color: #D97706; }
.status-analyzed { background: #D1FAE5; color: #059669; }
.status-failed { background: #FEE2E2; color: #DC2626; }

/* Info Grid */
.info-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
  margin-bottom: 24px;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.info-label {
  font-size: 12px;
  color: #9CA3AF;
  font-weight: 500;
}

.info-value {
  font-size: 14px;
  color: #1A1A2E;
  font-weight: 500;
}

/* Actions */
.info-actions {
  display: flex;
  gap: 10px;
  padding-top: 20px;
  border-top: 1px solid #F0F0F5;
}

.btn-primary {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 10px 22px;
  background: #5B5BD6;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.15s;
  text-decoration: none;
}

.btn-primary:hover { background: #4949C4; }

.btn-danger {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 10px 22px;
  background: #FEF2F2;
  color: #DC2626;
  border: 1px solid #FECACA;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.15s;
}

.btn-danger:hover { background: #FEE2E2; }

/* Section */
.section {
  margin-top: 28px;
}

.section-title {
  font-size: 17px;
  font-weight: 600;
  color: #1A1A2E;
  margin: 0 0 14px;
}

.empty-section {
  padding: 32px;
  text-align: center;
  color: #9CA3AF;
  font-size: 13px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 1px 4px rgba(91, 91, 214, 0.06);
}

/* Interview list */
.interview-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.interview-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 18px;
  background: #fff;
  border-radius: 10px;
  cursor: pointer;
  transition: box-shadow 0.2s;
  box-shadow: 0 1px 4px rgba(91, 91, 214, 0.06);
}

.interview-item:hover {
  box-shadow: 0 4px 12px rgba(91, 91, 214, 0.12);
}

.interview-position {
  font-size: 14px;
  font-weight: 500;
  color: #1A1A2E;
  margin: 0;
}

.interview-meta {
  font-size: 12px;
  color: #9CA3AF;
  margin-top: 3px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.interview-status {
  font-size: 11px;
  font-weight: 500;
  padding: 1px 8px;
  border-radius: 20px;
}

.istatus-pending { background: #F3F4F6; color: #6B7280; }
.istatus-in_progress { background: #DBEAFE; color: #2563EB; }
.istatus-completed { background: #D1FAE5; color: #059669; }
.istatus-cancelled { background: #FEE2E2; color: #DC2626; }
</style>
