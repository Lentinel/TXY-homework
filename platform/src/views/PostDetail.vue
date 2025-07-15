<template>
  <div class="post-detail-page">
    <el-button type="text" @click="$router.go(-1)" class="back-button">返回板块</el-button>
    <el-card v-if="post" class="post-card">
      <h2>{{ post.title }}</h2>
      <p class="post-meta">
        <span>作者: {{ post.authorName || '未知' }}</span>
        <span>发布时间: {{ formatTime(post.createTime) }}</span>
      </p>
      <div class="post-content">
        <p>{{ post.content }}</p>
      </div>
      
      <div class="post-actions">
        <el-button 
          type="primary" 
          @click="likePost"
          :disabled="post.isLiked"
        >
          点赞 ({{ post.likeCount || 0 }})
        </el-button>
        </div>

      <el-divider></el-divider>
      
      <h3>评论 ({{ comments.length }})</h3>
      <div class="comment-list">
        <CommentItem
          v-for="comment in comments"
          :key="comment.id"
          :comment="comment"
          @reply-comment="handleReplyComment"
        />
        <p v-if="!comments.length" class="no-comments">暂无评论。</p>
      </div>
      
      <div class="comment-input-area">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="发表你的评论..."
          v-model="newCommentContent"
          class="comment-textarea"
        ></el-input>
        <el-button type="primary" @click="submitComment" :disabled="!newCommentContent.trim()">发表评论</el-button>
      </div>
    </el-card>
    <p v-else class="loading-text">帖子加载中或不存在...</p>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { format } from 'date-fns';
import CommentItem from '../components/CommentItem.vue';

export default {
  components: { CommentItem },
  setup() {
    const post = ref(null);
    const comments = ref([]);
    const newCommentContent = ref('');
    const route = useRoute();
    const { sectionId, postId } = route.params;

    const fetchPostDetail = async () => {
      try {
        const response = await axios.get(`/api/sections/${sectionId}/posts/${postId}`);
        if (response.data.code === 200) {
          post.value = response.data.data;
          comments.value = post.value.comments || []; // 假设评论是嵌套在帖子详情里的
        }
      } catch (error) {
        console.error('获取帖子详情失败:', error);
      }
    };
    
    const likePost = async () => {
      if (post.value.isLiked) return;
      try {
        const userId = 'testUser123'; // 从登录状态获取
        const response = await axios.post(`/api/sections/${sectionId}/posts/${postId}/like?userId=${userId}`);
        if (response.data.code === 200) {
          post.value.likeCount = (post.value.likeCount || 0) + 1;
          post.value.isLiked = true;
          this.$message.success('点赞成功！');
        } else {
          this.$message.error(response.data.msg || '点赞失败');
        }
      } catch (error) {
        console.error('点赞失败:', error);
        this.$message.error('点赞失败，请检查网络');
      }
    };

    const submitComment = async () => { /* 沿用之前的逻辑，这里不需要修改 */ };
    const handleReplyComment = async () => { /* 沿用之前的逻辑，这里不需要修改 */ };
    const formatTime = (timeString) => { /* 沿用之前的逻辑 */ };
    
    onMounted(() => {
      fetchPostDetail();
    });

    return {
      post,
      comments,
      newCommentContent,
      likePost,
      submitComment,
      handleReplyComment,
      formatTime,
    };
  },
};
</script>

<style scoped>
/* 样式与之前相同 */
</style>