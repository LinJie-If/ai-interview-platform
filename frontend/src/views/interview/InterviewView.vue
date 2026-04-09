<template>
  <div class="interview-page">
    <!-- 顶部栏 -->
    <div class="interview-topbar">
      <button class="btn-back" @click="goBack">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <polyline points="15 18 9 12 15 6"/>
        </svg>
        返回
      </button>
      <div class="topbar-center">
        <span class="topbar-title">{{ interview?.position || '模拟面试' }}</span>
        <span class="topbar-badge" :class="`badge-${phase}`">{{ phaseText }}</span>
      </div>
      <div class="topbar-timer" v-if="phase === 'active'">
        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/>
        </svg>
        {{ formattedDuration }}
      </div>
      <div v-else style="width: 80px;"></div>
    </div>

    <!-- 面试准备阶段 -->
    <div v-if="phase === 'setup'" class="setup-panel">
      <div class="setup-card">
        <div class="setup-icon">
          <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#5B5BD6" stroke-width="1.5">
            <path d="M17 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2"/>
            <circle cx="9" cy="7" r="4"/>
            <path d="M23 21v-2a4 4 0 00-3-3.87"/>
            <path d="M16 3.13a4 4 0 010 7.75"/>
          </svg>
        </div>
        <h2 class="setup-title">准备开始面试</h2>
        <p class="setup-desc">AI 面试官将根据您的简历进行提问，请认真回答每个问题</p>

        <div class="setup-form">
          <label class="form-label">面试岗位</label>
          <input
            v-model="position"
            class="form-input"
            placeholder="例如：前端工程师、Java 后端开发..."
          />
        </div>

        <div class="setup-tips">
          <div class="tip-item">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="#5B5BD6" stroke-width="2">
              <polyline points="20 6 9 17 4 12"/>
            </svg>
            面试包含 5-8 道题目，涵盖技术和项目经验
          </div>
          <div class="tip-item">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="#5B5BD6" stroke-width="2">
              <polyline points="20 6 9 17 4 12"/>
            </svg>
            回答尽量详细，展示您的思路和解决方案
          </div>
          <div class="tip-item">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="#5B5BD6" stroke-width="2">
              <polyline points="20 6 9 17 4 12"/>
            </svg>
            面试结束后将生成评估报告
          </div>
        </div>

        <button class="btn-start" @click="beginInterview">
          开始面试
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="5" y1="12" x2="19" y2="12"/>
            <polyline points="12 5 19 12 12 19"/>
          </svg>
        </button>
      </div>
    </div>

    <!-- 面试进行中 -->
    <template v-if="phase === 'active' || phase === 'finished'">
      <div class="chat-area" ref="chatAreaRef">
        <div class="chat-messages">
          <div
            v-for="(msg, idx) in messages"
            :key="idx"
            class="message"
            :class="`message--${msg.role}`"
          >
            <div class="message-avatar" v-if="msg.role === 'interviewer'">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
                <path d="M12 2L3 7v5c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V7l-9-5z"/>
              </svg>
            </div>
            <div class="message-bubble">
              <div class="message-content" v-html="renderContent(msg.content)"></div>
            </div>
            <div class="message-avatar user-avatar" v-if="msg.role === 'user'">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
                <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"/>
                <circle cx="12" cy="7" r="4"/>
              </svg>
            </div>
          </div>

          <!-- AI 正在输入 -->
          <div v-if="aiTyping" class="message message--interviewer">
            <div class="message-avatar">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
                <path d="M12 2L3 7v5c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V7l-9-5z"/>
              </svg>
            </div>
            <div class="message-bubble">
              <div class="typing-indicator">
                <span></span><span></span><span></span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 输入区域 -->
      <div class="input-area" v-if="phase === 'active'">
        <div class="input-wrapper">
          <textarea
            ref="textareaRef"
            v-model="userInput"
            class="input-box"
            placeholder="输入你的回答..."
            rows="1"
            @keydown.enter.exact="handleSend"
            @input="autoResize"
          ></textarea>
          <button
            class="btn-send"
            :disabled="!userInput.trim() || aiTyping"
            @click="handleSend"
          >
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="22" y1="2" x2="11" y2="13"/>
              <polygon points="22 2 15 22 11 13 2 9 22 2"/>
            </svg>
          </button>
        </div>
        <p class="input-hint">Enter 发送 · Shift+Enter 换行</p>
      </div>

      <!-- 面试结束 -->
      <div class="finished-bar" v-if="phase === 'finished'">
        <div class="finished-info">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#059669" stroke-width="2">
            <path d="M22 11.08V12a10 10 0 11-5.93-9.14"/>
            <polyline points="22 4 12 14.01 9 11.01"/>
          </svg>
          面试已结束，共 {{ questionCount }} 道题目，用时 {{ formattedDuration }}
        </div>
        <button class="btn-primary" @click="goBack">返回简历详情</button>
      </div>
    </template>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick, computed, onUnmounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { createInterview, updateInterview } from '../../api/interview'
import { getResumeById } from '../../api/resume'
import type { Resume, Interview, ChatMessage } from '../../types'

const route = useRoute()
const router = useRouter()

const phase = ref<'setup' | 'active' | 'finished'>('setup')
const position = ref('')
const resume = ref<Resume | null>(null)
const interview = ref<Interview | null>(null)
const messages = ref<ChatMessage[]>([])
const userInput = ref('')
const aiTyping = ref(false)
const chatAreaRef = ref<HTMLElement | null>(null)
const textareaRef = ref<HTMLTextAreaElement | null>(null)
const startTimestamp = ref(0)
const elapsedSeconds = ref(0)
const questionCount = ref(0)
let timerHandle = 0

const MOCK_QUESTIONS = [
  '你好，感谢你参加今天的面试！请先做一个简短的自我介绍吧。',
  '可以聊一聊你最近参与的一个项目吗？你在其中承担了什么角色，遇到了哪些挑战？',
  '在前端/后端开发中，你是如何处理性能优化问题的？能举一个具体的例子吗？',
  '你对微服务架构和单体架构分别有什么理解？在什么场景下你会推荐使用微服务？',
  '请描述一下你是如何进行代码评审的？你认为好的代码应该具备哪些特质？',
  '你在团队协作中遇到过技术分歧吗？你是如何解决的？',
  '非常感谢你的回答！今天的面试到此结束。你还有什么想了解的吗？',
]

let currentQuestionIdx = 0

const formattedDuration = computed(() => {
  const m = Math.floor(elapsedSeconds.value / 60)
  const s = elapsedSeconds.value % 60
  return `${String(m).padStart(2, '0')}:${String(s).padStart(2, '0')}`
})

const phaseText = computed(() => {
  if (phase.value === 'setup') return '准备中'
  if (phase.value === 'active') return '进行中'
  return '已结束'
})

onMounted(async () => {
  const resumeId = Number(route.params.resumeId)
  if (resumeId) {
    try {
      const res = await getResumeById(resumeId)
      resume.value = res.data
    } catch { /* ignore */ }
  }
})

onUnmounted(() => {
  if (timerHandle) clearInterval(timerHandle)
})

async function beginInterview() {
  const resumeId = Number(route.params.resumeId)
  try {
    const res = await createInterview({
      resumeId,
      position: position.value || '综合面试',
      status: 'IN_PROGRESS',
    })
    interview.value = res.data
  } catch {
    // even if API fails, proceed with mock interview
    interview.value = {
      id: 0,
      resumeId,
      position: position.value || '综合面试',
      status: 'IN_PROGRESS',
      result: '',
      startTime: new Date().toISOString(),
      endTime: '',
      createdAt: new Date().toISOString(),
    }
  }

  phase.value = 'active'
  startTimestamp.value = Date.now()
  timerHandle = window.setInterval(() => {
    elapsedSeconds.value = Math.floor((Date.now() - startTimestamp.value) / 1000)
  }, 1000)

  currentQuestionIdx = 0
  await sendAiMessage(MOCK_QUESTIONS[currentQuestionIdx])
}

async function sendAiMessage(content: string) {
  aiTyping.value = true
  scrollToBottom()

  await new Promise((r) => setTimeout(r, 800 + Math.random() * 1200))

  messages.value.push({
    role: 'interviewer',
    content,
    timestamp: Date.now(),
  })
  aiTyping.value = false
  scrollToBottom()
}

function handleSend(e?: Event) {
  if (e && (e as KeyboardEvent).shiftKey) return
  e?.preventDefault()

  const text = userInput.value.trim()
  if (!text || aiTyping.value) return

  messages.value.push({
    role: 'user',
    content: text,
    timestamp: Date.now(),
  })
  userInput.value = ''
  resetTextarea()
  scrollToBottom()

  currentQuestionIdx++
  questionCount.value = currentQuestionIdx

  if (currentQuestionIdx < MOCK_QUESTIONS.length) {
    sendAiMessage(MOCK_QUESTIONS[currentQuestionIdx])
  } else {
    finishInterview()
  }
}

async function finishInterview() {
  aiTyping.value = true
  scrollToBottom()
  await new Promise((r) => setTimeout(r, 1000))

  messages.value.push({
    role: 'interviewer',
    content: '感谢你的耐心作答！本次面试到此结束，后续会生成详细的面试评估报告，请留意查看。祝你一切顺利！',
    timestamp: Date.now(),
  })
  aiTyping.value = false
  phase.value = 'finished'

  if (timerHandle) clearInterval(timerHandle)

  if (interview.value && interview.value.id) {
    try {
      await updateInterview(interview.value.id, {
        status: 'COMPLETED',
        endTime: new Date().toISOString(),
      })
    } catch { /* ignore */ }
  }

  scrollToBottom()
}

function goBack() {
  const resumeId = Number(route.params.resumeId)
  if (resumeId) {
    router.push(`/resumes/${resumeId}`)
  } else {
    router.push('/resumes')
  }
}

function scrollToBottom() {
  nextTick(() => {
    if (chatAreaRef.value) {
      chatAreaRef.value.scrollTop = chatAreaRef.value.scrollHeight
    }
  })
}

function autoResize() {
  const el = textareaRef.value
  if (!el) return
  el.style.height = 'auto'
  el.style.height = Math.min(el.scrollHeight, 120) + 'px'
}

function resetTextarea() {
  nextTick(() => {
    if (textareaRef.value) {
      textareaRef.value.style.height = 'auto'
    }
  })
}

function renderContent(text: string): string {
  return text.replace(/\n/g, '<br>')
}
</script>

<style scoped>
.interview-page {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background: #F0F2FA;
}

/* Topbar */
.interview-topbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 24px;
  background: #fff;
  border-bottom: 1px solid #F0F0F5;
  flex-shrink: 0;
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

.topbar-center {
  display: flex;
  align-items: center;
  gap: 8px;
}

.topbar-title {
  font-size: 15px;
  font-weight: 600;
  color: #1A1A2E;
}

.topbar-badge {
  font-size: 11px;
  font-weight: 500;
  padding: 2px 8px;
  border-radius: 20px;
}

.badge-setup { background: #F3F4F6; color: #6B7280; }
.badge-active { background: #DBEAFE; color: #2563EB; }
.badge-finished { background: #D1FAE5; color: #059669; }

.topbar-timer {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  font-weight: 600;
  color: #5B5BD6;
  font-variant-numeric: tabular-nums;
}

/* Setup */
.setup-panel {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 24px;
}

.setup-card {
  width: 100%;
  max-width: 480px;
  background: #fff;
  border-radius: 16px;
  padding: 40px 36px;
  text-align: center;
  box-shadow: 0 2px 16px rgba(91, 91, 214, 0.06);
}

.setup-icon {
  width: 72px;
  height: 72px;
  background: #F0F0FF;
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 16px;
}

.setup-title {
  font-size: 22px;
  font-weight: 700;
  color: #1A1A2E;
  margin: 0 0 8px;
}

.setup-desc {
  font-size: 14px;
  color: #9CA3AF;
  line-height: 1.6;
  margin-bottom: 24px;
}

.setup-form {
  text-align: left;
  margin-bottom: 20px;
}

.form-label {
  display: block;
  font-size: 13px;
  font-weight: 500;
  color: #374151;
  margin-bottom: 6px;
}

.form-input {
  width: 100%;
  padding: 10px 14px;
  border: 1px solid #E5E7EB;
  border-radius: 8px;
  font-size: 14px;
  color: #1A1A2E;
  outline: none;
  transition: border-color 0.15s;
  box-sizing: border-box;
}

.form-input:focus {
  border-color: #5B5BD6;
  box-shadow: 0 0 0 3px rgba(91, 91, 214, 0.1);
}

.setup-tips {
  display: flex;
  flex-direction: column;
  gap: 10px;
  text-align: left;
  margin-bottom: 28px;
  padding: 16px;
  background: #FAFAFE;
  border-radius: 10px;
}

.tip-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  color: #6B7280;
}

.btn-start {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 12px 32px;
  background: #5B5BD6;
  color: #fff;
  border: none;
  border-radius: 10px;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.15s, transform 0.1s;
}

.btn-start:hover {
  background: #4949C4;
  transform: translateY(-1px);
}

/* Chat Area */
.chat-area {
  flex: 1;
  overflow-y: auto;
  padding: 24px 24px 16px;
}

.chat-messages {
  max-width: 720px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

/* Messages */
.message {
  display: flex;
  gap: 10px;
  align-items: flex-start;
}

.message--user {
  justify-content: flex-end;
}

.message-avatar {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  background: linear-gradient(135deg, #5B5BD6, #4949C4);
}

.user-avatar {
  background: linear-gradient(135deg, #6366F1, #8B5CF6);
}

.message-bubble {
  max-width: 75%;
  padding: 12px 16px;
  border-radius: 14px;
  font-size: 14px;
  line-height: 1.65;
}

.message--interviewer .message-bubble {
  background: #fff;
  color: #1A1A2E;
  border-bottom-left-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
}

.message--user .message-bubble {
  background: #5B5BD6;
  color: #fff;
  border-bottom-right-radius: 4px;
}

.message-content {
  word-break: break-word;
}

/* Typing indicator */
.typing-indicator {
  display: flex;
  gap: 4px;
  padding: 4px 0;
}

.typing-indicator span {
  width: 7px;
  height: 7px;
  background: #C7C8FF;
  border-radius: 50%;
  animation: typingBounce 1.2s infinite;
}

.typing-indicator span:nth-child(2) { animation-delay: 0.2s; }
.typing-indicator span:nth-child(3) { animation-delay: 0.4s; }

@keyframes typingBounce {
  0%, 60%, 100% { transform: translateY(0); opacity: 0.4; }
  30% { transform: translateY(-6px); opacity: 1; }
}

/* Input Area */
.input-area {
  flex-shrink: 0;
  padding: 12px 24px 16px;
  background: #fff;
  border-top: 1px solid #F0F0F5;
}

.input-wrapper {
  max-width: 720px;
  margin: 0 auto;
  display: flex;
  gap: 10px;
  align-items: flex-end;
}

.input-box {
  flex: 1;
  padding: 10px 14px;
  border: 1px solid #E5E7EB;
  border-radius: 10px;
  font-size: 14px;
  color: #1A1A2E;
  resize: none;
  outline: none;
  line-height: 1.5;
  font-family: inherit;
  max-height: 120px;
  transition: border-color 0.15s;
}

.input-box:focus {
  border-color: #5B5BD6;
  box-shadow: 0 0 0 3px rgba(91, 91, 214, 0.1);
}

.btn-send {
  width: 40px;
  height: 40px;
  border: none;
  border-radius: 10px;
  background: #5B5BD6;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  flex-shrink: 0;
  transition: background 0.15s;
}

.btn-send:hover:not(:disabled) {
  background: #4949C4;
}

.btn-send:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.input-hint {
  max-width: 720px;
  margin: 6px auto 0;
  font-size: 11px;
  color: #C0C5D0;
  text-align: center;
}

/* Finished Bar */
.finished-bar {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 24px;
  background: #fff;
  border-top: 1px solid #F0F0F5;
}

.finished-info {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #374151;
  font-weight: 500;
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
  transition: background 0.15s;
  text-decoration: none;
}

.btn-primary:hover { background: #4949C4; }
</style>
