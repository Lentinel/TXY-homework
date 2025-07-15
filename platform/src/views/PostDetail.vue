<template>
  <div class="post-detail-page">
    <el-button type="text" @click="$router.go(-1)">返回论坛</el-button>
    <el-card v-if="post">
      <h2>{{ post.title }}</h2>
      <p class="post-meta">
        <span>作者: {{ post.authorName }}</span>
        <span>发布时间: {{ post.createTime }}</span>
      </p>
      <div class="post-content">{{ post.content }}</div>

      <el-divider></el-divider>

      <h3>评论</h3>
      <div class="comment-list">
        <CommentItem
          v-for="comment in comments"
          :key="comment.id"
          :comment="comment"
          @reply-comment="handleReplyComment"
        />
        <p v-if="!comments.length">暂无评论。</p>
      </div>

      <el-input
        type="textarea"
        :rows="3"
        placeholder="发表你的评论..."
        v-model="newCommentContent"
        class="comment-input"
      ></el-input>
      <el-button type="primary" @click="submitComment" :disabled="!newCommentContent.trim()">发表评论</el-button>
    </el-card>
    <p v-else>帖子加载中或不存在...</p>
  </div>
</template>

<script>
import axios from 'axios';
// 假设 CommentItem 是一个单独的组件，用于显示评论和处理回复
import CommentItem from '@/components/CommentItem.vue';

export default {
  components: {
    CommentItem
  },
  data() {
    return {
      postId: null,
      post: null,
      comments: [],
      newCommentContent: '',
    };
  },
  created() {
    this.postId = this.$route.params.id; // 从路由获取帖子ID
    this.fetchPostDetail();
    this.fetchComments();
  },
  methods: {
    async fetchPostDetail() {
      try {
        // 假设获取帖子详情的API和获取分页帖子用的是同一个，需要调整后端提供单个帖子详情API
        // 或者在分页API中直接获取所有信息，然后找到对应的帖子
        const response = await axios.get('/api/forum/page', {
          params: { id: this.postId } // 后端可能需要支持按ID查询
        });
        this.post = response.data.data.records.find(p => p.id == this.postId); // 临时查找
        // 理想情况应有 GET /api/forum/{id}
      } catch (error) {
        console.error('获取帖子详情失败:', error);
        this.$message.error('获取帖子详情失败');
      }
    },
    async fetchComments() {
      try {
        const response = await axios.get(`/api/forum/${this.postId}/comment`); // 假设这是获取评论的API
        // 您的API文档中获取评论是 GET /api/forum/{forumId}/comment/{commentId}
        // 如果是获取某个帖子的所有评论，后端可能需要一个GET /api/forum/{forumId}/comments 这样的API
        // 暂时假设 `/api/forum/${this.postId}/comment` 返回所有评论
        this.comments = response.data.data; // 假设返回的是评论列表
      } catch (error) {
        console.error('获取评论失败:', error);
        this.$message.error('获取评论失败');
      }
    },
    async submitComment() {
      if (!this.newCommentContent.trim()) {
        this.$message.warning('评论内容不能为空！');
        return;
      }
      try {
        await axios.post(`/api/forum/${this.postId}/comment`, {
          content: this.newCommentContent
        }, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        this.$message.success('评论成功！');
        this.newCommentContent = '';
        this.fetchComments(); // 刷新评论列表
      } catch (error) {
        console.error('发表评论失败:', error);
        this.$message.error('发表评论失败');
      }
    },
    async handleReplyComment(commentId, replyContent) {
      if (!replyContent.trim()) {
        this.$message.warning('回复内容不能为空！');
        return;
      }
      try {
        await axios.post(`/api/forum/${this.postId}/comment/${commentId}`, {
          content: replyContent
        }, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        this.$message.success('回复成功！');
        this.fetchComments(); // 刷新评论列表
      } catch (error) {
        console.error('回复评论失败:', error);
        this.$message.error('回复评论失败');
      }
    }
  }
};
</script>

<style scoped>
.post-detail-page {
  padding: 20px;
}
.post-meta {
  font-size: 0.9em;
  color: #666;
  margin-bottom: 15px;
}
.post-content {
  line-height: 1.8;
  margin-bottom: 20px;
}
.comment-input {
  margin-top: 20px;
  margin-bottom: 10px;
}
</style>