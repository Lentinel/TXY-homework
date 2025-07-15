<template>
  <div class="discussion-reply-item">
    <div class="reply-content">
      <p><strong>{{ reply.authorName }}</strong>: {{ reply.content }}</p>
    </div>
    <div class="reply-meta">
      <span>{{ formatTime(reply.createTime) }}</span>
      <el-button type="text" size="mini" @click="$emit('like-reply', reply.id)">点赞 ({{ reply.likeCount || 0 }})</el-button>
      <el-button v-if="isAuthor" type="text" size="mini" @click="$emit('mark-best', reply.id)">标记为最佳</el-button>
      <span v-if="reply.isBest" class="best-reply-tag">最佳回复</span>
    </div>
  </div>
</template>

<script>
import { format } from 'date-fns';
import { computed } from 'vue';

export default {
  props: {
    reply: {
      type: Object,
      required: true
    },
    discussionAuthorId: Number // 假设从父组件传入讨论帖的作者ID
  },
  setup(props) {
    const currentUserId = 1; // 假设当前用户ID为1
    const isAuthor = computed(() => props.discussionAuthorId === currentUserId);

    const formatTime = (timeString) => {
      if (!timeString) return '未知时间';
      try {
        return format(new Date(timeString), 'yyyy-MM-dd HH:mm');
      } catch (e) {
        return timeString;
      }
    };

    return {
      formatTime,
      isAuthor,
    };
  },
};
</script>