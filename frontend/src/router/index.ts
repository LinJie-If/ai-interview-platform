import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', redirect: '/upload' },
  { path: '/upload', component: () => import('../views/resume/UploadView.vue') },
  { path: '/resumes', component: () => import('../views/resume/ResumeListView.vue') },
  { path: '/resumes/:id', component: () => import('../views/resume/ResumeDetailView.vue') },
  { path: '/interview/:resumeId', component: () => import('../views/interview/InterviewView.vue') },
  { path: '/interviews', component: () => import('../views/interview/InterviewHistoryView.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
