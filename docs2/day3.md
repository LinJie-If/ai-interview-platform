你现在协助我推进这个项目的 Day 3，但这次请严格只做 Day 3 范围内的事情，不要越界到 Day 4 及以后。

## 项目背景
这是一个 AI 智能面试平台复刻项目：
- 前端：Vue3 + TypeScript
- 后端：Spring Boot + JPA + PostgreSQL
- 当前进度：Day 1、Day 2 已完成基础骨架、统一返回体、异常处理、基础页面结构
- 今天要做的是 Day 3：简历模块数据层和后端骨架

## Day 3 目标
本次只做以下内容：
1. 明确 resumes 表结构
2. 明确 resume_analyses 表结构
3. 创建 ResumeEntity
4. 创建 ResumeAnalysisEntity
5. 创建 ResumeRepository
6. 创建 ResumeAnalysisRepository
7. 创建 ResumeController
8. 创建 ResumeService
9. 创建 ResumeServiceImpl
10. 创建 ResumeListItemDTO 和 ResumeDetailDTO
11. 前端补齐 3 个页面的业务骨架（上传页、列表页、详情页）
12. 保持代码结构清晰，方便 Day 4 继续接上传逻辑

## 这次不要做的事情
请不要做以下内容：
- 不要实现真实文件上传存储
- 不要接入 MinIO / S3
- 不要接入 Tika
- 不要接入 Spring AI
- 不要接 Redis Stream
- 不要接知识库
- 不要写复杂 UI 美化
- 不要改与简历模块无关的代码

## 后端约束
请按以下思路设计：
### resumes 表建议字段
- id
- fileHash
- originalFilename
- fileSize
- contentType
- storageKey
- resumeText
- analyzeStatus
- analyzeError
- uploadedAt
- updatedAt

说明：
- fileHash 尽量唯一
- resumeText 用适合长文本的类型
- analyzeStatus 请先设计为明确的枚举状态，不要只用随意字符串

### resume_analyses 表建议字段
- id
- resume
- overallScore
- summary
- scoreBreakdownJson
- strengthsJson
- suggestionsJson
- analyzedAt

说明：
- 一份简历允许存在多条分析记录，因此请按 Resume -> ResumeAnalysis 一对多关系设计
- 当前只做结构，不实现真实 AI 分析逻辑

### Repository
请创建：
- ResumeRepository
- ResumeAnalysisRepository

如有必要，可补充最基础的查询方法，例如：
- findByFileHash
- findTopByResumeIdOrderByAnalyzedAtDesc 或等价方案

### Service / Controller
请创建骨架：
- ResumeController
- ResumeService
- ResumeServiceImpl

接口先保留这些：
- POST /api/resumes/upload
- GET /api/resumes
- GET /api/resumes/{id}
- DELETE /api/resumes/{id}

要求：
- 先把分层和方法签名建好
- 返回结构遵循统一 Result<T>
- 方法体可以先写基础占位逻辑，但不要乱写未来功能

### DTO
请至少拆分为：
- ResumeListItemDTO
- ResumeDetailDTO

不要让前端直接返回 Entity

## 前端约束
请补齐简历模块 3 个页面的业务骨架：
1. 上传页
2. 列表页
3. 详情页

要求：
- 页面不是空白页，要有业务占位感
- 先不接真实接口
- 可以使用假数据占位
- 最好补充 resume 相关 TS 类型定义，方便后续接接口

## 执行要求
请按以下顺序执行：
1. 先阅读当前项目结构和相关文件
2. 输出你准备新增/修改的文件清单
3. 输出你对 Day 3 的简短实现方案
4. 再开始修改代码
5. 修改完成后，给我一份改动总结，包括：
   - 修改了哪些文件
   - 每个文件的作用
   - 哪些地方只是骨架
   - 哪些内容留到 Day 4 再做

注意：
- 这次的目标是“少做但做稳”
- 优先保证结构清晰、命名统一、方便后续扩展
- 如果发现我当前文稿里某些字段设计不合理，请先按更稳妥、更方便 Day 4~6 推进的方式处理，但不要擅自扩展到其他模块