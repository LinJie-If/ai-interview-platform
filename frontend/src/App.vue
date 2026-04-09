<template>
  <div class="app-layout">
    <!-- 侧边栏 -->
    <aside class="sidebar">
      <!-- Logo -->
      <div class="sidebar-logo">
        <div class="logo-icon">
          <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
            <path d="M10 2L3 6V10C3 14.418 6.134 18.116 10 19C13.866 18.116 17 14.418 17 10V6L10 2Z" fill="white" opacity="0.9"/>
            <circle cx="10" cy="10" r="3" fill="white"/>
          </svg>
        </div>
        <div>
          <div class="logo-title">AI Interview</div>
          <div class="logo-subtitle">智能面试助手</div>
        </div>
      </div>

      <!-- 简历与面试 -->
      <div class="nav-section">
        <div class="nav-section-label">简历与面试</div>
        <RouterLink to="/upload" class="nav-item" active-class="nav-item--active">
          <div class="nav-item-icon">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M21 15v4a2 2 0 01-2 2H5a2 2 0 01-2-2v-4"/>
              <polyline points="17 8 12 3 7 8"/>
              <line x1="12" y1="3" x2="12" y2="15"/>
            </svg>
          </div>
          <div class="nav-item-text">
            <span class="nav-item-title">上传简历</span>
            <span class="nav-item-desc">AI 分析简历</span>
          </div>
          <svg class="nav-item-arrow" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="9 18 15 12 9 6"/>
          </svg>
        </RouterLink>

        <RouterLink to="/resumes" class="nav-item" active-class="nav-item--active">
          <div class="nav-item-icon">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="2" y="3" width="20" height="14" rx="2" ry="2"/>
              <line x1="8" y1="21" x2="16" y2="21"/>
              <line x1="12" y1="17" x2="12" y2="21"/>
            </svg>
          </div>
          <div class="nav-item-text">
            <span class="nav-item-title">简历库</span>
            <span class="nav-item-desc">管理所有简历</span>
          </div>
          <svg class="nav-item-arrow" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="9 18 15 12 9 6"/>
          </svg>
        </RouterLink>

        <RouterLink to="/interviews" class="nav-item" active-class="nav-item--active">
          <div class="nav-item-icon">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M17 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2"/>
              <circle cx="9" cy="7" r="4"/>
              <path d="M23 21v-2a4 4 0 00-3-3.87"/>
              <path d="M16 3.13a4 4 0 010 7.75"/>
            </svg>
          </div>
          <div class="nav-item-text">
            <span class="nav-item-title">面试记录</span>
            <span class="nav-item-desc">查看面试历史</span>
          </div>
          <svg class="nav-item-arrow" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="9 18 15 12 9 6"/>
          </svg>
        </RouterLink>
      </div>

      <!-- 知识库 -->
      <div class="nav-section">
        <div class="nav-section-label">知识库</div>
        <RouterLink to="/knowledge" class="nav-item" active-class="nav-item--active">
          <div class="nav-item-icon">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M4 19.5A2.5 2.5 0 016.5 17H20"/>
              <path d="M6.5 2H20v20H6.5A2.5 2.5 0 014 19.5v-15A2.5 2.5 0 016.5 2z"/>
            </svg>
          </div>
          <div class="nav-item-text">
            <span class="nav-item-title">知识库管理</span>
            <span class="nav-item-desc">管理知识文档</span>
          </div>
          <svg class="nav-item-arrow" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="9 18 15 12 9 6"/>
          </svg>
        </RouterLink>

        <RouterLink to="/qa" class="nav-item" active-class="nav-item--active">
          <div class="nav-item-icon">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z"/>
            </svg>
          </div>
          <div class="nav-item-text">
            <span class="nav-item-title">问答助手</span>
            <span class="nav-item-desc">基于知识库问答</span>
          </div>
          <svg class="nav-item-arrow" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="9 18 15 12 9 6"/>
          </svg>
        </RouterLink>
      </div>
    </aside>

    <!-- 主内容区 -->
    <main class="main-content">
      <RouterView />
    </main>
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', sans-serif;
  background-color: #F0F2FA;
}

a {
  text-decoration: none;
  color: inherit;
}
</style>

<style scoped>
.app-layout {
  display: flex;
  min-height: 100vh;
  background-color: #F0F2FA;
}

/* 侧边栏 */
.sidebar {
  width: 200px;
  min-width: 200px;
  background: #fff;
  display: flex;
  flex-direction: column;
  padding: 20px 12px;
  gap: 8px;
}

/* Logo 区域 */
.sidebar-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 4px 8px 16px;
  border-bottom: 1px solid #F0F0F0;
  margin-bottom: 8px;
}

.logo-icon {
  width: 36px;
  height: 36px;
  background: linear-gradient(135deg, #6C63FF 0%, #5B5BD6 100%);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.logo-title {
  font-size: 14px;
  font-weight: 700;
  color: #1A1A2E;
  line-height: 1.3;
}

.logo-subtitle {
  font-size: 11px;
  color: #9CA3AF;
  margin-top: 1px;
}

/* 导航分组 */
.nav-section {
  display: flex;
  flex-direction: column;
  gap: 2px;
  margin-bottom: 8px;
}

.nav-section-label {
  font-size: 11px;
  color: #9CA3AF;
  font-weight: 500;
  padding: 4px 8px;
  letter-spacing: 0.02em;
  margin-bottom: 2px;
}

/* 导航项 */
.nav-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 9px 10px;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.15s ease;
  color: #6B7280;
}

.nav-item:hover {
  background: #F5F5FF;
}

.nav-item--active {
  background: #EEEFFF;
  color: #5B5BD6;
}

.nav-item-icon {
  width: 28px;
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  border-radius: 6px;
  background: transparent;
}

.nav-item--active .nav-item-icon {
  background: #E8E9FF;
}

.nav-item-text {
  display: flex;
  flex-direction: column;
  flex: 1;
  min-width: 0;
}

.nav-item-title {
  font-size: 13px;
  font-weight: 500;
  color: inherit;
  line-height: 1.4;
}

.nav-item-desc {
  font-size: 11px;
  color: #B0B7C3;
  line-height: 1.3;
  margin-top: 1px;
}

.nav-item--active .nav-item-desc {
  color: #9B9FE0;
}

.nav-item-arrow {
  flex-shrink: 0;
  opacity: 0;
  transition: opacity 0.15s;
}

.nav-item--active .nav-item-arrow,
.nav-item:hover .nav-item-arrow {
  opacity: 1;
}

/* 主内容区 */
.main-content {
  flex: 1;
  overflow: auto;
}
</style>
