<template>
  <div class="answer-item">
    <div class="answer-content">
      <p><strong>{{ answer.answererName || '匿名用户' }}</strong>: {{ answer.content }}</p>
    </div>
    <div class="answer-meta">
      <span>{{ formatTime(answer.createTime) }}</span>
      <el-button type="text" size="mini" @click="$emit('like-answer', answer.id)">
        <i class="el-icon-thumb"></i> {{ answer.likes || 0 }}
      </el-button>
      <el-button
        v-if="checkPermission(answer.answererId, 'admin')"
        type="text"
        size="mini"
        style="color: #F56C6C;"
        @click="$emit('delete-answer', answer.id)"
      >
        删除
      </el-button>
    </div>
  </div>
</template>

<script>
import { format } from 'date-fns';

export default {
  name: 'AnswerItem',
  props: {
    answer: {
      type: Object,
      required: true
    },
    currentUserId: String,
    currentUserRole: String,
  },
  methods: {
    // 检查是否有删除回答的权限 (管理员或回答者)
    checkPermission(answererId, requiredRole) {
      return this.currentUserRole === requiredRole || answererId === this.currentUserId;
    },
    formatTime(timeString) {
      if (!timeString) return '未知时间';
      try {
        return format(new Date(timeString), 'yyyy-MM-dd HH:mm');
      } catch (e) {
        return timeString;
      }
    }
  }
};
</script>

<style scoped>
.answer-item {
  border: 1px solid #EBEEF5;
  border-radius: 6px;
  padding: 15px;
  margin-bottom: 15px;
  background-color: #fcfcfc;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.03);
}
.answer-content p {
  margin-bottom: 10px;
  line-height: 1.6;
  white-space: pre-wrap;
  word-break: break-word;
}
.answer-meta {
  font-size: 0.85em;
  color: #909399;
  display: flex;
  align-items: center;
  gap: 15px;
}
.el-icon-thumb {
  margin-right: 3px;
}
</style>