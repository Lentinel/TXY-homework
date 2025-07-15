<template>
  <div class="my-questions-page">
    <h2>我的提问</h2>
    <div v-if="questions.length" class="question-list">
      <el-card v-for="question in questions" :key="question.id" class="question-card">
        <div slot="header" class="clearfix">
          <span>{{ question.title }}</span>
          <el-button
            style="float: right; padding: 3px 0"
            type="text"
            @click="viewQuestionDetail(question.courseId, question.id)"
          >
            查看详情
          </el-button>
        </div>
        <p>{{ question.content ? question.content.substring(0, 100) + '...' : '无内容' }}</p>
        <div class="question-meta">
          <span>提问课程: {{ question.courseName || '未知课程' }}</span>
          <span>提问时间: {{ formatTime(question.createTime) }}</span>
          <span>回答数: {{ question.answerCount || 0 }}</span>
          <el-button type="danger" size="mini" @click="deleteQuestion(question.courseId, question.id)">删除</el-button>
        </div>
      </el-card>
    </div>
    <p v-else class="no-data">您还没有提问过。</p>
  </div>
</template>

<script>
import axios from 'axios';
import { format } from 'date-fns';

export default {
  props: ['userId'], // 接收用户ID
  data() {
    return {
      questions: [],
    };
  },
  created() {
    if (this.userId) {
      this.fetchMyQuestions();
    } else {
      this.$message.error('未提供用户ID，无法加载我的提问。');
    }
  },
  methods: {
    async fetchMyQuestions() {
      try {
        const response = await axios.get(`/api/${this.userId}/questions`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        if (response.data.code === 200 && response.data.data) {
          this.questions = response.data.data;
        } else {
          this.$message.error(response.data.msg || '获取我的提问失败');
        }
      } catch (error) {
        console.error('获取我的提问失败:', error);
        this.$message.error('获取我的提问失败，请检查网络或服务器');
      }
    },
    viewQuestionDetail(courseId, questionId) {
      this.$router.push(`/course/${courseId}/question/${questionId}`);
    },
    async deleteQuestion(courseId, questionId) {
      this.$confirm('确定要删除这个问题吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const response = await axios.delete(`/api/${courseId}/question/${questionId}`, {
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
          });
          if (response.data.code === 200) {
            this.$message.success('问题删除成功！');
            this.fetchMyQuestions(); // 刷新列表
          } else {
            this.$message.error(response.data.msg || '删除问题失败');
          }
        } catch (error) {
          console.error('删除问题失败:', error);
          this.$message.error('删除问题失败，请检查网络或权限');
        }
      }).catch(() => {
        this.$message.info('已取消删除');
      });
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
.my-questions-page {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}
.question-list {
  margin-top: 20px;
}
.question-card {
  margin-bottom: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}
.question-card .clearfix:before,
.question-card .clearfix:after {
  display: table;
  content: "";
}
.question-card .clearfix:after {
  clear: both
}
.question-meta {
  margin-top: 15px;
  font-size: 0.85em;
  color: #909399;
  display: flex;
  align-items: center;
  gap: 15px;
}
.no-data {
  text-align: center;
  color: #909399;
  margin-top: 50px;
}
</style>