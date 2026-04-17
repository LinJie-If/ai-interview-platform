<template>
  <div class="history-page">
    <div class="page-header">
      <div>
        <h1 class="page-title">面试记录</h1>
        <p class="page-desc">查看所有模拟面试的历史记录</p>
      </div>
    </div>

    <div v-if="loading" class="loading-state">
      <span class="loading-spinner"></span>
      加载中...
    </div>

    <div v-else-if="interviews.length === 0" class="empty-state">
      <div class="empty-icon">
        <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="#C7C8FF" stroke-width="1.5">
          <path d="M17 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2"/>
          <circle cx="9" cy="7" r="4"/>
          <path d="M23 21v-2a4 4 0 00-3-3.87"/>
          <path d="M16 3.13a4 4 0 010 7.75"/>
        </svg>
      </div>
      <p class="empty-title">暂无面试记录</p>
      <p class="empty-desc">上传简历并开始模拟面试后，记录将在此显示</p>
      <router-link to="/upload" class="btn-primary" style="margin-top: 8px;">上传简历</router-link>
    </div>

    <div v-else class="interview-grid">
      <div
        v-for="item in interviews"
        :key="item.id"
        class="interview-card"
        @click="goToInterview(item)"
      >
        <div class="card-left">
          <div class="card-icon" :class="`icon-${item.status?.toLowerCase()}`">
            <svg v-if="item.status === 'COMPLETED'" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M22 11.08V12a10 10 0 11-5.93-9.14"/>
              <polyline points="22 4 12 14.01 9 11.01"/>
            </svg>
            <svg v-else-if="item.status === 'IN_PROGRESS'" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="12" cy="12" r="10"/>
              <polyline points="12 6 12 12 16 14"/>
            </svg>
            <svg v-else width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="12" cy="12" r="10"/>
              <line x1="12" y1="8" x2="12" y2="12"/>
              <line x1="12" y1="16" x2="12.01" y2="16"/>
            </svg>
          </div>
          <div class="card-info">
            <p class="card-position">{{ item.position || '综合面试' }}</p>
            <div class="card-meta">
              <span>{{ formatDateTime(item.createdAt) }}</span>
              <span class="meta-dot">·</span>
              <span>简历 #{{ item.resumeId }}</span>
            </div>
          </div>
        </div>
        <div class="card-right">
          <span class="card-status" :class="`status-${item.status?.toLowerCase()}`">
            {{ statusText(item.status) }}
          </span>
          <svg class="card-arrow" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="9 18 15 12 9 6"/>
          </svg>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getInterviewList } from '../../api/interview'
import type { Interview } from '../../types'

const router = useRouter()
const interviews = ref<Interview[]>([])
const loading = ref(true)

onMounted(async () => {
  try {
    const list = await getInterviewList()
    interviews.value = [...list].sort(
      (a: Interview, b: Interview) =>
        new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
    )
  } catch {
    // silently handle
  } finally {
    loading.value = false
  }
})

function goToInterview(item: Interview) {
  router.push(`/interview/${item.resumeId}?interviewId=${item.id}`)
}

function formatDateTime(dateStr: string): string {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`
}

function statusText(status: string): string {
  const map: Record<string, string> = {
    PENDING: '待开始',
    IN_PROGRESS: '进行中',
    COMPLETED: '已完成',
    CANCELLED: '已取消',
  }
  return map[status] || status || '未知'
}
</script>

<style scoped>
.history-page {
  padding: 32px 40px;
  max-width: 900px;
  margin: 0 auto;
}

.page-header {
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

.empty-icon { margin-bottom: 8px; }
.empty-title { font-size: 16px; font-weight: 600; color: #1A1A2E; }
.empty-desc { font-size: 13px; color: #9CA3AF; }

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
}

.btn-primary:hover { background: #4949C4; }

/* Grid */
.interview-grid {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.interview-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  background: #fff;
  border-radius: 12px;
  cursor: pointer;
  transition: box-shadow 0.2s, transform 0.15s;
  box-shadow: 0 1px 4px rgba(91, 91, 214, 0.06);
}

.interview-card:hover {
  box-shadow: 0 4px 16px rgba(91, 91, 214, 0.12);
  transform: translateY(-1px);
}

.card-left {
  display: flex;
  align-items: center;
  gap: 14px;
  min-width: 0;
}

.card-icon {
  width: 44px;
  height: 44px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.icon-completed { background: #D1FAE5; color: #059669; }
.icon-in_progress { background: #DBEAFE; color: #2563EB; }
.icon-pending { background: #F3F4F6; color: #6B7280; }
.icon-cancelled { background: #FEE2E2; color: #DC2626; }

.card-info {
  min-width: 0;
}

.card-position {
  font-size: 14px;
  font-weight: 500;
  color: #1A1A2E;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.card-meta {
  display: flex;
  align-items: center;
  gap: 4px;
  margin-top: 3px;
  font-size: 12px;
  color: #9CA3AF;
}

.meta-dot { color: #D1D5DB; }

.card-right {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-shrink: 0;
}

.card-status {
  font-size: 12px;
  font-weight: 500;
  padding: 3px 10px;
  border-radius: 20px;
}

.status-completed { background: #D1FAE5; color: #059669; }
.status-in_progress { background: #DBEAFE; color: #2563EB; }
.status-pending { background: #F3F4F6; color: #6B7280; }
.status-cancelled { background: #FEE2E2; color: #DC2626; }

.card-arrow {
  color: #D1D5DB;
  transition: color 0.15s;
}

.interview-card:hover .card-arrow {
  color: #5B5BD6;
}
</style>
