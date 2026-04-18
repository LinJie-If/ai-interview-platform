<template>
  <div class="resume-list-page">
    <div class="page-header">
      <div>
        <h1 class="page-title">简历库</h1>
        <p class="page-desc">管理您上传的所有简历</p>
      </div>
      <router-link to="/upload" class="btn-primary">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
        </svg>
        上传简历
      </router-link>
    </div>

    <div v-if="loading" class="loading-state">
      <span class="loading-spinner"></span>
      加载中...
    </div>

    <div v-else-if="resumes.length === 0" class="empty-state">
      <div class="empty-icon">
        <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="#C7C8FF" stroke-width="1.5">
          <path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z"/>
          <polyline points="14 2 14 8 20 8"/>
        </svg>
      </div>
      <p class="empty-title">暂无简历</p>
      <p class="empty-desc">上传一份简历，开始 AI 模拟面试之旅</p>
      <router-link to="/upload" class="btn-primary" style="margin-top: 8px;">上传第一份简历</router-link>
    </div>

    <div v-else class="resume-grid">
      <div
        v-for="resume in resumes"
        :key="resume.id"
        class="resume-card"
        @click="$router.push(`/resumes/${resume.id}`)"
      >
        <div class="card-icon">
          <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#5B5BD6" stroke-width="1.5">
            <path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z"/>
            <polyline points="14 2 14 8 20 8"/>
            <line x1="16" y1="13" x2="8" y2="13"/>
            <line x1="16" y1="17" x2="8" y2="17"/>
          </svg>
        </div>
        <div class="card-body">
          <p class="card-name">{{ resume.originalName }}</p>
          <div class="card-meta">
            <span class="card-size">{{ formatFileSize(resume.fileSize) }}</span>
            <span class="card-dot">·</span>
            <span class="card-date">{{ formatDate(resume.createdAt) }}</span>
          </div>
        </div>
        <span class="card-status" :class="`status-${resume.status?.toLowerCase()}`">
          {{ statusText(resume.status) }}
        </span>
        <svg class="card-arrow" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <polyline points="9 18 15 12 9 6"/>
        </svg>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getResumeList } from '../../api/resume'
import type { Resume } from '../../types'

const resumes = ref<Resume[]>([])
const loading = ref(true)

onMounted(async () => {
  try {
    const list = await getResumeList()
    resumes.value = list ?? []
  } catch {
    // silently handle
  } finally {
    loading.value = false
  }
})

function formatFileSize(bytes: number): string {
  if (!bytes) return '0 B'
  if (bytes < 1024) return bytes + ' B'
  if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB'
  return (bytes / (1024 * 1024)).toFixed(1) + ' MB'
}

function formatDate(dateStr: string): string {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
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
</script>

<style scoped>
.resume-list-page {
  padding: 32px 40px;
  max-width: 900px;
  margin: 0 auto;
}

.page-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  margin-bottom: 32px;
}

.page-title {
  font-size: 24px;
  font-weight: 700;
  color: #1A1A2E;
  margin: 0;
  line-height: 1.3;
}

.page-desc {
  font-size: 14px;
  color: #9CA3AF;
  margin-top: 4px;
}

.btn-primary {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 9px 20px;
  background: #5B5BD6;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  text-decoration: none;
  transition: background 0.15s;
  white-space: nowrap;
}

.btn-primary:hover {
  background: #4949C4;
}

/* Loading */
.loading-state {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 80px 0;
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

/* Empty */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 80px 0;
  gap: 8px;
}

.empty-icon {
  margin-bottom: 8px;
}

.empty-title {
  font-size: 16px;
  font-weight: 600;
  color: #1A1A2E;
}

.empty-desc {
  font-size: 13px;
  color: #9CA3AF;
}

/* Grid */
.resume-grid {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.resume-card {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 16px 20px;
  background: #fff;
  border-radius: 12px;
  cursor: pointer;
  transition: box-shadow 0.2s, transform 0.15s;
  box-shadow: 0 1px 4px rgba(91, 91, 214, 0.06);
}

.resume-card:hover {
  box-shadow: 0 4px 16px rgba(91, 91, 214, 0.12);
  transform: translateY(-1px);
}

.card-icon {
  width: 44px;
  height: 44px;
  background: #F5F5FF;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.card-body {
  flex: 1;
  min-width: 0;
}

.card-name {
  font-size: 14px;
  font-weight: 500;
  color: #1A1A2E;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin: 0;
}

.card-meta {
  display: flex;
  align-items: center;
  gap: 4px;
  margin-top: 3px;
  font-size: 12px;
  color: #9CA3AF;
}

.card-dot {
  color: #D1D5DB;
}

.card-status {
  font-size: 12px;
  font-weight: 500;
  padding: 3px 10px;
  border-radius: 20px;
  flex-shrink: 0;
}

.status-uploaded {
  background: #EEF2FF;
  color: #6366F1;
}

.status-analyzing {
  background: #FEF3C7;
  color: #D97706;
}

.status-analyzed {
  background: #D1FAE5;
  color: #059669;
}

.status-failed {
  background: #FEE2E2;
  color: #DC2626;
}

.card-arrow {
  flex-shrink: 0;
  color: #D1D5DB;
  transition: color 0.15s;
}

.resume-card:hover .card-arrow {
  color: #5B5BD6;
}
</style>
