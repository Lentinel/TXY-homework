<template>
  <div class="discussion-detail-page">
    <el-button type="text" @click="$router.go(-1)">返回讨论列表</el-button>
    <el-card v-if="discussion" class="discussion-card">
      <h2>{{ discussion.title }}</h2>
      <p class="discussion-meta">
        <span>作者: {{ discussion.authorName }}</span>
        <span>发布时间: {{ formatTime(discussion.createTime) }}</span>
      </p>
      <div class="discussion-content">
        <p>{{ discussion.content }}</p>
      </div>

      <el-divider></el-divider>
      
      <h3>回复 ({{ replies.length }})</h3>
      <div class="replies-list">
        <DiscussionReplyItem
          v-for="reply in replies"
          :key="reply.id"
          :reply="reply"
          @like-reply="handleLikeReply"
          @mark-best="handleMarkBestReply"
        />
        <p v-if="!replies.length">暂无回复。</p>
      </div>
      
      <div class="reply-input-area">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="发表你的回复..."
          v-model="newReplyContent"
        ></el-input>
        <el-button type="primary" @click="submitReply" :disabled="!newReplyContent.trim()">发表回复</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { format } from 'date-fns';
import DiscussionReplyItem from '../components/DiscussionReplyItem.vue';

export default {
  components: { DiscussionReplyItem },
  setup() {
    const discussion = ref(null);
    const replies = ref([]);
    const newReplyContent = ref('');
    const route = useRoute();
    const { discussionId } = route.params;
    const userId = ref(1); // 假设当前用户ID为1

    const fetchDiscussionDetail = async () => {
      // 获取讨论详情 API: GET /api/discussions/{id}
      const response = await axios.get(`/api/discussions/${discussionId}`);
      if (response.data.code === 200) {
        discussion.value = response.data.data;
      }
    };
    
    const fetchReplies = async () => {
      // 获取讨论回复列表 API: GET /api/replies/discussion/{discussionId}
      const response = await axios.get(`/api/replies/discussion/${discussionId}`);
      if (response.data.code === 200) {
        replies.value = response.data.data || [];
      }
    };

    const submitReply = async () => {
      try {
        // 创建回复 API: POST /api/replies
        await axios.post('/api/replies', {
          discussionId: discussionId,
          authorId: userId.value,
          content: newReplyContent.value,
        });
        alert('回复成功！');
        newReplyContent.value = '';
        fetchReplies();
      } catch (error) {
        console.error('发表回复失败:', error);
      }
    };

    const handleLikeReply = async (replyId) => {
      // 点赞回复 API: POST /api/replies/{replyId}/like
      try {
        await axios.post(`/api/replies/${replyId}/like`, null, { params: { userId: userId.value } });
        alert('点赞成功！');
        fetchReplies();
      } catch (error) {
        console.error('点赞失败:', error);
      }
    };

    const handleMarkBestReply = async (replyId) => {
      // 标记最佳回复 API: POST /api/replies/{replyId}/best
      try {
        await axios.post(`/api/replies/${replyId}/best`, null, { params: { discussionId: discussionId, userId: userId.value } });
        alert('标记最佳回复成功！');
        fetchReplies();
      } catch (error) {
        console.error('标记最佳回复失败:', error);
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
      fetchDiscussionDetail();
      fetchReplies();
    });

    return {
      discussion,
      replies,
      newReplyContent,
      submitReply,
      handleLikeReply,
      handleMarkBestReply,
      formatTime,
    };
  },
};
</script>