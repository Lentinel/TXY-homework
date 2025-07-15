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
        >
          点赞 ({{ post.likeCount || 0 }})
        </el-button>
      </div>

      <el-divider></el-divider>
      
      <h3>评论 ({{ replies.length }})</h3>
      <div class="replies-list">
        <CommentItem
          v-for="reply in replies"
          :key="reply.id"
          :comment="reply"
          @like-reply="handleLikeReply"
        />
        <p v-if="!replies.length" class="no-replies">暂无评论。</p>
      </div>
      
      <div class="reply-input-area">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="发表你的回复..."
          v-model="newReplyContent"
          class="reply-textarea"
        ></el-input>
        <el-button type="primary" @click="submitReply" :disabled="!newReplyContent.trim()">发表回复</el-button>
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
    const replies = ref([]);
    const newReplyContent = ref('');
    const route = useRoute();
    const { postId } = route.params;
    const currentUserId = ref(1); // 假设当前用户ID为1

    const fetchPostDetail = async () => {
      // 这里的 API 已确认
      const response = await axios.get(`/api/sections/1/posts/${postId}`);
      if (response.data.code === 200) {
        post.value = response.data.data;
      }
    };
    
    const fetchReplies = async () => {
      // 使用新的 API: GET /api/posts/{postId}/replies
      const response = await axios.get(`/api/posts/${postId}/replies`);
      if (response.data.code === 200) {
        replies.value = response.data.data || [];
      }
    };

    const likePost = async () => {
      // 沿用之前的逻辑，API已确认
    };

    const submitReply = async () => {
      if (!newReplyContent.value.trim()) return;
      try {
        // 使用新的 API: POST /api/posts/{postId}/replies
        const response = await axios.post(`/api/posts/${postId}/replies`, {
          content: newReplyContent.value,
          authorId: currentUserId.value,
          postId: postId
        });
        if (response.data.code === 200) {
          alert('回复成功！');
          newReplyContent.value = '';
          fetchReplies(); // 刷新回复列表
        }
      } catch (error) {
        console.error('发表回复失败:', error);
      }
    };

    const handleLikeReply = async (replyId) => {
      // 使用新的 API: POST /api/posts/{postId}/replies/{replyId}/like
      try {
        const response = await axios.post(`/api/posts/${postId}/replies/${replyId}/like`, null, {
          params: { userId: currentUserId.value }
        });
        if (response.data.code === 200) {
          alert('回复点赞成功！');
          fetchReplies(); // 刷新以更新点赞数
        }
      } catch (error) {
        console.error('回复点赞失败:', error);
      }
    };

    const formatTime = (timeString) => {
      if (!timeString) return '未知时间';
      try {
        return format(new Date(timeString), 'yyyy-MM-dd HH:mm');
      } catch (e) {
        return timeString;
      }
    };
    
    onMounted(() => {
      fetchPostDetail();
      fetchReplies();
    });

    return {
      post,
      replies,
      newReplyContent,
      likePost,
      submitReply,
      handleLikeReply,
      formatTime,
    };
  },
};
</script>