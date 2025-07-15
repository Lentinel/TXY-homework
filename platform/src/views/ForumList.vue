<template>
  <el-card style="margin:20px">
    <h2>论坛板块</h2>
    <el-button type="primary" @click="$router.push('/forum/new')">新建帖子</el-button>
    <el-list :data="posts">
      <el-list-item v-for="post in posts" :key="post.id" @click="viewPost(post)">
        <el-list-item-meta :title="post.title" :description="post.author + ' · ' + post.date" />
      </el-list-item>
    </el-list>
  </el-card>
</template>

<script>
export default {
  name: 'ForumList',
  data() {
    return {
      posts: [
        { id: 1, title: 'Vue3 Composition API 讨论', author: 'Alice', date: '2025-06-25' },
        { id: 2, title: '在线教育最佳实践', author: 'Bob',   date: '2025-06-24' }
      ]
    }
  },
  methods: {
    viewPost(post) {
      this.$router.push(`/forum/${post.id}`)
    }
  }
}
</script>