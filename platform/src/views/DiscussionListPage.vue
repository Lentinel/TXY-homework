<template>
  <div class="discussion-list-page">
    <h2>课程讨论</h2>
    <el-button type="primary" @click="goToCreateDiscussion">发布新讨论</el-button>

    <div v-if="discussions.length" class="discussion-list">
      <el-card v-for="discussion in discussions" :key="discussion.id" class="discussion-card">
        <div slot="header">{{ discussion.title }}</div>
        <p>{{ discussion.content.substring(0, 100) }}...</p>
        <el-button type="text" @click="viewDiscussionDetail(discussion.id)">查看详情</el-button>
      </el-card>
    </div>
    <p v-else class="empty-text">暂无讨论。</p>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';

export default {
  props: ['courseId'],
  setup(props) {
    const discussions = ref([]);
    const router = useRouter();
    
    // 假设存在一个 API: GET /api/discussions?courseId=xxx
    const fetchDiscussions = async () => {
      try {
        const response = await axios.get(`/api/discussions?courseId=${props.courseId}`);
        if (response.data.code === 200) {
          discussions.value = response.data.data;
        }
      } catch (error) {
        console.error('获取讨论失败:', error);
      }
    };

    const goToCreateDiscussion = () => {
      router.push(`/course/${props.courseId}/discussions/create`);
    };

    const viewDiscussionDetail = (discussionId) => {
      router.push(`/course/${props.courseId}/discussions/${discussionId}`);
    };

    onMounted(() => {
      fetchDiscussions();
    });

    return {
      discussions,
      goToCreateDiscussion,
      viewDiscussionDetail,
    };
  },
};
</script>

<style scoped>
/* 样式 */
</style>