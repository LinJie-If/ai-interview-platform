# 面试历史记录视图

# 功能说明

记录面试历史记录页面的相关笔记。

# 相关代码

- 文件位置：`frontend/src/views/interview/InterviewHistoryView.vue`

# 待记录内容

<!-- 在此记录面试历史页面的学习笔记 -->

## q1

### 1.我不理解这一段的作用

  try {
      const list = await getInterviewList()
      interviews.value = [...list].sort(
        (a: Interview, b: Interview) =>
          new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
      )
  }

### 2.sort在这段代码的作用是什么
sort() 是 JavaScript 数组的排序方法。
如果不排序，显示顺序可能就是后端返回什么就显示什么。
但我们通常希望“最新的记录在前面”，所以就要排序。

### 3.a: Interview, b: Interview的作用是什么
这是 TypeScript 的参数类型标注。

意思是告诉 TS：

  a 是一个 Interview
  b 也是一个 Interview
  因为 list 是 Interview[]，数组里每一项当然就是 Interview。


### 4.[...list]
复制出一个新的数组

因为 sort() 会直接修改原数组。
如果你写：
list.sort(...)
那 list 本身就被改了。
如果你写：

[...list].sort(...)
就是：

先复制一个新数组
再对新数组排序
原来的 list 不动


---

这三个问题问得都很好，这一段其实就是“拿到面试列表后，按时间倒序排一下，再放到页面里”。

先看整段：

```ts
try {
  const list = await getInterviewList()
  interviews.value = [...list].sort(
    (a: Interview, b: Interview) =>
      new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
  )
}
```

**1. 这一段整体的作用是什么**

可以拆成 3 步理解：

1. 调接口拿到面试列表
```ts
const list = await getInterviewList()
```

这里的 `list` 是一个数组，类型是：

```ts
Interview[]
```

2. 把这个数组按创建时间排序
```ts
[...list].sort(...)
```

3. 把排好序的结果赋值给页面响应式变量
```ts
interviews.value = ...
```

所以一句话总结就是：

- 从后端拿到面试记录
- 按最新时间排在最前面
- 页面显示这个排序后的列表


**2. `sort` 在这里的作用是什么**

`sort()` 是 JavaScript 数组的排序方法。

比如一个数组原来是：

```ts
[
  { createdAt: '2026-04-15T10:00:00' },
  { createdAt: '2026-04-17T09:00:00' },
  { createdAt: '2026-04-16T14:00:00' }
]
```

如果不排序，显示顺序可能就是后端返回什么就显示什么。  
但我们通常希望“最新的记录在前面”，所以就要排序。

这里的排序规则是：

```ts
new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
```

意思是：
- 比较 `b` 的时间戳和 `a` 的时间戳
- 如果 `b` 比 `a` 更新，就排在前面

所以这是**按创建时间倒序排序**：
- 最新的在最上面
- 最旧的在最下面

---

**3. `a: Interview, b: Interview` 的作用是什么**

这是 TypeScript 的参数类型标注。

意思是告诉 TS：

- `a` 是一个 `Interview`
- `b` 也是一个 `Interview`

因为 `list` 是 `Interview[]`，数组里每一项当然就是 `Interview`。

写上这个之后，TS 就知道：

```ts
a.createdAt
b.createdAt
```

这些字段是合法的。

如果不写，有时候 TS 会报：

- Parameter 'a' implicitly has an 'any' type
- Parameter 'b' implicitly has an 'any' type

也就是它不知道 `a`、`b` 是什么类型。

所以这行的作用就是：

- 给排序函数的两个参数补上类型
- 让 TS 能理解 `createdAt` 是存在的字段
- 顺手消除报错

---

**再讲一下 `[...list]` 是干什么的**

这也是你后面经常会见到的写法。

```ts
[...list]
```

意思是“复制出一个新的数组”。

因为 `sort()` 会**直接修改原数组**。  
如果你写：

```ts
list.sort(...)
```

那 `list` 本身就被改了。

如果你写：

```ts
[...list].sort(...)
```

就是：
- 先复制一个新数组
- 再对新数组排序
- 原来的 `list` 不动

这是一个更稳的习惯。

---

**你可以把这段翻译成人话**

```ts
try {
  const list = await getInterviewList()
  interviews.value = [...list].sort(
    (a: Interview, b: Interview) =>
      new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
  )
}
```

等于：

“先拿到所有面试记录，然后按创建时间从新到旧排序，最后把排序后的结果放到页面上显示。”

---

**如果你想看一个更容易懂的版本，可以临时写成这样：**

```ts
try {
  const list = await getInterviewList()

  const sortedList = [...list].sort((a: Interview, b: Interview) => {
    const timeA = new Date(a.createdAt).getTime()
    const timeB = new Date(b.createdAt).getTime()
    return timeB - timeA
  })

  interviews.value = sortedList
}
```

这版更啰嗦，但更适合理解。

如果你愿意，我下一条可以把这段再用“JS 初学者视角”给你讲一遍：
- 为什么 `sort` 回调返回正数/负数就能决定顺序
- 为什么 `timeB - timeA` 会变成“倒序”


# 备注

