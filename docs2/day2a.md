d2

可以，我们继续按同样风格把 Day 2 具体化。

Day 2 的核心目标也只有一句话：

把“前后端基础框架”搭稳，为 Day 3 的简历模块正式开发做准备。

今天依然不做 AI、不做异步、不做 RAG。今天做的是：

前端项目结构规范化

后端基础设施代码补齐

数据库连接确认

统一响应与异常机制固定下来

Day 2 目标

今天结束时，你必须拿到这 6 个结果：

前端不再是临时页面，而是有清晰目录结构

前端有统一请求封装 request.ts

后端有统一返回体 Result

后端有全局异常处理

后端有 CORS 配置

后端数据库连接和项目启动稳定

Day 2 要做的事情

一、整理前端目录结构

你要做什么

把 Vue3 项目从“能跑”升级成“能开发”。

今天前端目录至少整理成这样

src/
  api/
  router/
  stores/
  views/
    resume/
    interview/
  components/
  assets/

你今天具体要做什么

建 api

建 stores

建 components

确认 views/resume

确认 views/interview

保持 router 已存在

完成标准

src 目录层次清楚，后面不会乱堆文件。

二、补前端 API 基础层

你要做什么

建立后面所有接口都会复用的请求封装。

今天要创建的文件

src/api/request.ts

src/api/resume.ts

src/api/interview.ts

今天先做什么程度

不用写具体业务接口，只要把框架搭好。

request.ts 要具备的能力

baseURL

超时时间

统一响应拦截

统一错误处理

resume.ts

先留空或写个占位对象

interview.ts

先留空或写个占位对象

完成标准

以后你写接口时，只需要在模块文件里加方法，不需要每次重新封装 axios。

三、把前端页面再稳定一点

你要做什么

确认今天的页面骨架已经符合后面开发习惯。

今天建议补的细节

菜单命名统一

默认首页重定向

404 暂时不用处理

页面标题统一

每个页面写上自己的功能占位说明

完成标准

你打开每个页面时，知道这个页面未来是干什么的。

四、整理后端包结构

你要做什么

把后端从“只有启动类”升级成“可持续写业务”的结构。

今天后端包至少整理成这样

common/
  result/
  exception/
  config/
modules/
  resume/
    controller/
    service/
    repository/
    entity/
    dto/
  interview/
    controller/
    service/
    repository/
    entity/
    dto/
infrastructure/

今天具体要做什么

把已有代码放到合理目录

检查启动类包路径

确保 Spring 能扫到这些包

注意

今天最重要的是：
启动类的包路径要能扫描到 common 和 modules

完成标准

后端结构不会阻碍后续开发。

五、写统一返回体 Result

你要做什么

固定后端所有接口的统一返回格式。

今天要做的内容

定义一个简单的统一响应对象，至少包含：

code

message

data

标准

成功时统一返回成功结构，失败时统一返回失败结构。

完成标准

后面你所有接口都能复用这套返回格式。

六、写全局异常处理

你要做什么

避免 Controller 里到处手写 try-catch。

今天要处理的异常类型

先只处理两类：

业务异常

系统异常

目标

无论出什么错，前端拿到的响应结构都统一。

完成标准

你的后端错误返回是可控的，不会一会儿 JSON 一会儿白页。

七、补 CORS 配置

你要做什么

确保前端 5173 能访问后端 8080。

今天要配置什么

允许本地开发域名

允许常见请求方法

允许常见请求头

完成标准

前端后续调接口不会被跨域拦住。

八、完善后端配置文件

你要做什么

把 application.properties 或 application.yml 调整成最小可用版本。

今天需要包含的配置

server.port=8080

spring.datasource.url

spring.datasource.username

spring.datasource.password

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

文件上传大小限制

今天不需要加的配置

Spring AI

Redis

pgvector

对象存储

Prompt 相关配置

完成标准

后端启动信息足够清楚，数据库能稳定连上。

九、确认数据库连接和项目能重复启动

你要做什么

不要只启动一次成功就算结束，要验证可以反复启动。

你今天要做的检查

停掉后端，再重新启动一次

确认数据库连接没有随机报错

确认 JPA 不会报建表错误

确认 /api/health 依然可访问

完成标准

后端启动是稳定的，不是“碰巧成功一次”。

十、写 Day 2 记录

你要做什么

今天写一份比 Day 1 更清晰的记录。

建议记录内容

前端目录结构已经整理完

后端基础设施代码已经建立

数据库连接正常

明天开始正式做 resume 模块数据层

完成标准

Day 3 你打开项目时，不需要重新回忆“今天做到哪了”。

Day 2 时间安排建议

第 1 段：40 分钟

整理前端目录

建 api 文件

第 2 段：40 分钟

整理前端页面说明

确认路由和布局

第 3 段：1 小时

整理后端包结构

检查启动类扫描范围

第 4 段：1 小时

写 Result

写异常处理

写 CORS

第 5 段：30 分钟

调整配置文件

重启后端验证

第 6 段：10 分钟

写 Day 2 日志

Day 2 不要做的事

今天这些还不要碰：

简历上传接口逻辑

文件解析

AI 分析

Redis Stream

知识库

面试题生成

页面精修

Day 2 最终验收清单

今天结束前，只看这 8 条：

前端目录结构整理完成

request.ts 已建立

resume.ts 和 interview.ts 已建立

后端包结构整理完成

Result 已建立

全局异常处理已建立

CORS 已配置

后端可以稳定连接数据库并启动

Day 2 结束后你会得到什么

到 Day 2 结束时，你的项目虽然还没有业务功能，但已经具备：

一个规范的 Vue 前端工程

一个规范的 Spring Boot 后端工程

后续写业务时不会再反复返工基础设施

这一步做稳，Day 3 才能真正开始简历模块。

如果你愿意，我下一条可以继续按这个风格给你：

Day 3 的具体内容
也就是：

后端要建哪些实体

前端先不用接接口，要做哪些静态页

数据库字段具体要怎么定