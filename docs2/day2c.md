因为现在 `getResumeList()` 的返回值已经变了。

以前你的链路可能是这样：

- `resume.ts` 返回完整响应对象
- 页面拿到的是：
```ts
{
  code,
  message,
  data
}
```

所以页面里要写：

```ts
const res = await getResumeList()
resumes.value = res.data
```

但你现在已经把 [resume.ts](/Users/liuwenjun/Code/ai-interview-platform/frontend/src/api/resume.ts) 改成了这种风格：

```ts
export async function getResumeList(): Promise<Resume[]> {
  const res = await get<Resume[]>('/resume/list')
  return res.data
}
```

注意最后一行：

```ts
return res.data
```

这意味着 `getResumeList()` 已经把响应对象里的 `data` 提前拆出来了。  
所以页面拿到的就不再是：

```ts
{ code, message, data }
```

而是直接拿到：

```ts
Resume[]
```

所以页面层就应该写成：

```ts
const list = await getResumeList()
resumes.value = list
```

**那为什么我写成 `list ?? []`？**
这是一个小的兜底写法，意思是：

- 如果 `list` 是 `null` 或 `undefined`
- 那就给 `resumes.value` 赋一个空数组 `[]`

也就是：

```ts
resumes.value = list ?? []
```

等价于：

```ts
if (list == null) {
  resumes.value = []
} else {
  resumes.value = list
}
```

**不过这里要不要 `?? []`，取决于你的 API 约定**
如果你已经明确要求：

- `getResumeList(): Promise<Resume[]>`

那从类型上讲，它就应该始终返回数组。  
这种情况下，其实可以直接写：

```ts
const list = await getResumeList()
resumes.value = list
```

这更干净。

**所以更准确地说，我现在推荐你写这个：**

```ts
onMounted(async () => {
  try {
    const list = await getResumeList()
    resumes.value = list
  } catch {
    // silently handle
  } finally {
    loading.value = false
  }
})
```

**一句话总结**
这么改不是因为语法变化，而是因为职责变化了：

- 以前：页面自己取 `res.data`
- 现在：`resume.ts` 已经替页面取好了
- 所以页面直接用结果本身

如果你愿意，我下一步就继续带你改 [frontend/src/views/interview/InterviewView.vue](/Users/liuwenjun/Code/ai-interview-platform/frontend/src/views/interview/InterviewView.vue)，我会先告诉你它为什么也要同样改。