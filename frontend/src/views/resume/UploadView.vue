<template>
  <div class="upload-page">
    <div class="upload-container">
      <h1 class="page-title">开始您的 <span class="title-highlight">AI</span> 模拟面试</h1>
      <p class="page-subtitle">上传 PDF 或 Word 简历，AI 将为您定制专属面试方案</p>

      <!-- 上传卡片 -->
      <div
        class="upload-card"
        :class="{ 'upload-card--dragover': isDragging, 'upload-card--has-file': selectedFile }"
        @dragover.prevent="isDragging = true"
        @dragleave.prevent="isDragging = false"
        @drop.prevent="handleDrop"
        @click="!selectedFile && triggerFileInput()"
      >
        <input
          ref="fileInputRef"
          type="file"
          accept=".pdf,.docx,.doc,.txt"
          style="display: none"
          @change="handleFileChange"
        />

        <!-- 未选择文件状态 -->
        <template v-if="!selectedFile">
          <div class="upload-icon-wrapper">
            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M21 15v4a2 2 0 01-2 2H5a2 2 0 01-2-2v-4"/>
              <polyline points="17 8 12 3 7 8"/>
              <line x1="12" y1="3" x2="12" y2="15"/>
            </svg>
          </div>
          <p class="upload-primary-text">点击或拖拽文件至此处</p>
          <p class="upload-secondary-text">支持 PDF, DOCX, TXT（最大 10MB）</p>
          <button class="btn-select" @click.stop="triggerFileInput()">选择简历文件</button>
        </template>

        <!-- 已选择文件状态 -->
        <template v-else>
          <div class="file-preview">
            <div class="file-icon">
              <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="#5B5BD6" stroke-width="1.5">
                <path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z"/>
                <polyline points="14 2 14 8 20 8"/>
                <line x1="16" y1="13" x2="8" y2="13"/>
                <line x1="16" y1="17" x2="8" y2="17"/>
                <polyline points="10 9 9 9 8 9"/>
              </svg>
            </div>
            <div class="file-info">
              <p class="file-name">{{ selectedFile.name }}</p>
              <p class="file-size">{{ formatFileSize(selectedFile.size) }}</p>
            </div>
            <button class="btn-remove" @click.stop="removeFile()">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="18" y1="6" x2="6" y2="18"/>
                <line x1="6" y1="6" x2="18" y2="18"/>
              </svg>
            </button>
          </div>
          <div class="upload-actions">
            <button class="btn-reselect" @click.stop="triggerFileInput()">重新选择</button>
            <button class="btn-upload" :disabled="isUploading" @click.stop="handleUpload()">
              <template v-if="isUploading">
                <span class="loading-spinner"></span>
                分析中...
              </template>
              <template v-else>
                开始 AI 分析
              </template>
            </button>
          </div>
        </template>
      </div>

      <!-- 提示信息 -->
      <div v-if="errorMsg" class="error-msg">
        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="12" cy="12" r="10"/>
          <line x1="12" y1="8" x2="12" y2="12"/>
          <line x1="12" y1="16" x2="12.01" y2="16"/>
        </svg>
        {{ errorMsg }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { uploadResume } from '../../api/resume'
//test
import { getHealth } from '../../api/common'

const router = useRouter()
const fileInputRef = ref<HTMLInputElement | null>(null)
const selectedFile = ref<File | null>(null)
const isDragging = ref(false)
const isUploading = ref(false)
const errorMsg = ref('')

const ALLOWED_TYPES = ['application/pdf', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'application/msword', 'text/plain']
const MAX_SIZE = 10 * 1024 * 1024

//test
// onMounted(async () => {
//   try {
//     const health = await getHealth()
//     console.log('health check:', health)
//   } catch (error) {
//     console.error('health check failed:', error)
//   }
// })

function triggerFileInput() {
  fileInputRef.value?.click()
}

function validateFile(file: File): string {
  if (!ALLOWED_TYPES.includes(file.type) && !file.name.match(/\.(pdf|docx|doc|txt)$/i)) {
    return '仅支持 PDF、DOCX、TXT 格式的文件'
  }
  if (file.size > MAX_SIZE) {
    return '文件大小不能超过 10MB'
  }
  return ''
}

function handleFileChange(e: Event) {
  const input = e.target as HTMLInputElement
  const file = input.files?.[0]
  if (file) {
    const err = validateFile(file)
    if (err) {
      errorMsg.value = err
      return
    }
    errorMsg.value = ''
    selectedFile.value = file
  }
  input.value = ''
}

function handleDrop(e: DragEvent) {
  isDragging.value = false
  const file = e.dataTransfer?.files[0]
  if (file) {
    const err = validateFile(file)
    if (err) {
      errorMsg.value = err
      return
    }
    errorMsg.value = ''
    selectedFile.value = file
  }
}

function removeFile() {
  selectedFile.value = null
  errorMsg.value = ''
}

function formatFileSize(bytes: number): string {
  if (bytes < 1024) return bytes + ' B'
  if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB'
  return (bytes / (1024 * 1024)).toFixed(1) + ' MB'
}

async function handleUpload() {
  if (!selectedFile.value) return
  isUploading.value = true
  errorMsg.value = ''
  try {
    const resume = await uploadResume(selectedFile.value)
    if (resume.id) {
      router.push(`/resumes/${resume.id}`)
    } else {
      router.push('/resumes')
    }
  } catch (e: unknown) {
    if (e instanceof Error) {
      errorMsg.value = e.message
    } else {
      errorMsg.value = '上传失败，请稍后重试'
    }
  } finally {
    isUploading.value = false
  }
}
</script>

<style scoped>
.upload-page {
  min-height: 100vh;
  background: #F0F2FA;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 24px;
}

.upload-container {
  width: 100%;
  max-width: 560px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

.page-title {
  font-size: 32px;
  font-weight: 700;
  color: #1A1A2E;
  text-align: center;
  line-height: 1.3;
  letter-spacing: -0.02em;
}

.title-highlight {
  color: #5B5BD6;
}

.page-subtitle {
  font-size: 14px;
  color: #6B7280;
  text-align: center;
  line-height: 1.6;
  margin-top: -4px;
}

/* 上传卡片 */
.upload-card {
  width: 100%;
  background: #fff;
  border-radius: 16px;
  padding: 48px 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  cursor: pointer;
  transition: box-shadow 0.2s, border-color 0.2s;
  box-shadow: 0 2px 16px rgba(91, 91, 214, 0.06);
  border: 2px dashed transparent;
  margin-top: 8px;
}

.upload-card:hover {
  box-shadow: 0 4px 24px rgba(91, 91, 214, 0.12);
  border-color: #C7C8FF;
}

.upload-card--dragover {
  border-color: #5B5BD6;
  background: #F5F5FF;
  box-shadow: 0 4px 24px rgba(91, 91, 214, 0.16);
}

.upload-card--has-file {
  cursor: default;
  border-style: solid;
  border-color: #E8E9FF;
}

/* 上传图标 */
.upload-icon-wrapper {
  width: 64px;
  height: 64px;
  background: #F0F0FF;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #5B5BD6;
  margin-bottom: 4px;
}

.upload-primary-text {
  font-size: 15px;
  font-weight: 600;
  color: #1A1A2E;
}

.upload-secondary-text {
  font-size: 13px;
  color: #9CA3AF;
}

.btn-select {
  margin-top: 8px;
  padding: 10px 28px;
  background: #5B5BD6;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.15s, transform 0.1s;
}

.btn-select:hover {
  background: #4949C4;
  transform: translateY(-1px);
}

.btn-select:active {
  transform: translateY(0);
}

/* 已选文件 */
.file-preview {
  display: flex;
  align-items: center;
  gap: 14px;
  width: 100%;
  padding: 16px 18px;
  background: #F8F8FF;
  border-radius: 10px;
  border: 1px solid #E8E9FF;
}

.file-icon {
  flex-shrink: 0;
}

.file-info {
  flex: 1;
  min-width: 0;
}

.file-name {
  font-size: 14px;
  font-weight: 500;
  color: #1A1A2E;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.file-size {
  font-size: 12px;
  color: #9CA3AF;
  margin-top: 2px;
}

.btn-remove {
  flex-shrink: 0;
  width: 28px;
  height: 28px;
  border: none;
  background: #EDEDFF;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #5B5BD6;
  transition: background 0.15s;
}

.btn-remove:hover {
  background: #E0E0FF;
}

.upload-actions {
  display: flex;
  gap: 10px;
  width: 100%;
  margin-top: 4px;
}

.btn-reselect {
  flex: 1;
  padding: 10px 0;
  background: #F0F0FF;
  color: #5B5BD6;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.15s;
}

.btn-reselect:hover {
  background: #E4E4FF;
}

.btn-upload {
  flex: 2;
  padding: 10px 0;
  background: #5B5BD6;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  transition: background 0.15s;
}

.btn-upload:hover:not(:disabled) {
  background: #4949C4;
}

.btn-upload:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

/* Loading spinner */
.loading-spinner {
  width: 14px;
  height: 14px;
  border: 2px solid rgba(255,255,255,0.4);
  border-top-color: #fff;
  border-radius: 50%;
  animation: spin 0.6s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* 错误提示 */
.error-msg {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #EF4444;
  background: #FEF2F2;
  border: 1px solid #FECACA;
  padding: 8px 14px;
  border-radius: 8px;
  width: 100%;
}
</style>
