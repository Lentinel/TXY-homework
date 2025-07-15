<template>
  <el-card style="margin:20px">
    <h2>{{ post.title }}</h2>
    <p>作者：{{ post.author }} · 时间：{{ post.date }}</p>
    <div class="content">{{ post.content }}</div>

    <h3>评论区</h3>
    <el-comment v-for="(c, i) in post.comments" :key="i"
                :author="c.user" :datetime="c.date">
      <template #content>{{ c.text }}</template>
    </el-comment>

    <el-input type="textarea" v-model="newComment" rows="3" placeholder="写下你的评论..." />
    <el-button type="primary" @click="addComment">发布评论</el-button>
  </el-card>
</template>

<script>
export default {
  name: 'QAView',
  data() {
    return {
      post: {
        id: 1,
        title: 'Vue3 Composition API 讨论',
        author: 'Alice',
        date: '2025-06-25',
        content: '讨论 Vue3 新特性及使用场景。',
        comments: [
          { user: 'Charlie', date: '2025-06-26', text: '很有帮助！' }
        ]
      },
      newComment: ''
    }
  },
  methods: {
    addComment() {
      if (!this.newComment) return
      this.post.comments.push({ user: '当前用户', date: new Date().toLocaleString(), text: this.newComment })
      this.newComment = ''
    }
  }
}
</script>

<style scoped>
.content { margin: 16px 0; }
</style>