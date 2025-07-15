<template>
  <div class="forum-page">
    <h2>论坛</h2>
    <el-button type="primary" @click="goToCreatePost">发帖</el-button>

    <div v-if="posts.length">
      <el-card v-for="post in posts" :key="post.id" class="post-card">
        <div slot="header" class="clearfix">
          <span>{{ post.title }}</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="viewPostDetail(post.id)">查看详情</el-button>
        </div>
        <p>{{ post.content.substring(0, 100) }}...</p>
        <div class="post-meta">
          <span>作者: {{ post.authorName }}</span>
          <span>发布时间: {{ post.createTime }}</span>
          <span>评论: {{ post.commentCount }}</span>
          <el-button v-if="isMyPost(post.authorId)" type="danger" size="mini" @click="deletePost(post.id)">删除</el-button>
        </div>
      </el-card>
      <el-pagination
        @current-change="handlePageChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="totalPosts"
        layout="prev, pager, next">
      </el-pagination>
    </div>
    <p v-else>暂无帖子，快来发表你的第一篇帖子吧！</p>
  </div>
</template>

<script>
import axios from 'axios'; // 假设您使用 axios

export default {
  data() {
    return {
      posts: [],
      currentPage: 1,
      pageSize: 10, // 每页显示数量
      totalPosts: 0,
      // 假设从用户信息中获取当前用户ID和角色
      currentUserId: 'YOUR_CURRENT_USER_ID', // 替换为实际的用户ID
      currentUserRole: 'student', // student 或 teacher
    };
  },
  created() {
    this.fetchPosts();
  },
  methods: {
    async fetchPosts() {
      try {
        const response = await axios.get('/api/forum/page', {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize
          }
        });
        this.posts = response.data.data.records; // 假设数据结构是 {data: {records: [], total: 0}}
        this.totalPosts = response.data.data.total;
      } catch (error) {
        console.error('获取帖子失败:', error);
        this.$message.error('获取帖子失败');
      }
    },
    handlePageChange(newPage) {
      this.currentPage = newPage;
      this.fetchPosts();
    },
    goToCreatePost() {
      this.$router.push('/forum/create'); // 假设创建帖子路由是 /forum/create
    },
    viewPostDetail(postId) {
      this.$router.push(`/forum/${postId}`); // 假设帖子详情路由是 /forum/:id
    },
    isMyPost(authorId) {
      // 只有管理员或帖子作者可以删除
      return this.currentUserRole === 'admin' || authorId === this.currentUserId;
    },
    async deletePost(postId) {
      this.$confirm('确定要删除这篇帖子吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await axios.delete(`/api/forum/${postId}`, {
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } // 假设需要token
          });
          this.$message.success('帖子删除成功！');
          this.fetchPosts(); // 刷新列表
        } catch (error) {
          console.error('删除帖子失败:', error);
          this.$message.error('删除帖子失败');
        }
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    }
  }
};
</script>

<style scoped>
.forum-page {
  padding: 20px;
}
.post-card {
  margin-bottom: 20px;
}
.post-meta {
  margin-top: 10px;
  font-size: 0.9em;
  color: #666;
}
.post-meta span {
  margin-right: 15px;
}
</style>