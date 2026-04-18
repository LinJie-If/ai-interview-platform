import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', redirect: '/upload' },
  {
    path: '/upload',
    component: () => import('../views/resume/UploadView.vue'),
    meta: { title: '上传简历' },
  },
  {
    path: '/resumes',
    component: () => import('../views/resume/ResumeListView.vue'),
    meta: { title: '简历库' },
  },
  {
    path: '/resumes/:id',
    component: () => import('../views/resume/ResumeDetailView.vue'),
    meta: { title: '简历详情' },
  },
  {
    path: '/interview/:resumeId',
    component: () => import('../views/interview/InterviewView.vue'),
    meta: { title: '模拟面试' },
  },
  {
    path: '/interviews',
    component: () => import('../views/interview/InterviewHistoryView.vue'),
    meta: { title: '面试记录' },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.afterEach((to) => {
  const pageTitle = (to.meta.title as string) || 'AI Interview'
  document.title = `${pageTitle} | AI Interview`
})

export default router
