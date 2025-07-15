<template>
  <div class="my-answers-page">
    <h2>我的回答</h2>
    <div v-if="answers.length" class="answer-list">
      <el-card v-for="answer in answers" :key="answer.id" class="answer-card">
        <div slot="header" class="clearfix">
          <span>对问题 "<span class="question-title-link" @click="viewQuestionDetail(answer.courseId, answer.questionId)">{{ answer.questionTitle || '未知问题' }}</span>" 的回答</span>
          <el-button
            style="float: right; padding: 3px 0"
            type="text"
            @click="viewQuestionDetail(answer.courseId, answer.questionId)"
          >
            查看问题
          </el-button>
        </div>
        <p>{{ answer.content ? answer.content.substring(0, 100) + '...' : '无内容' }}</p>
        <div class="answer-meta">
          <span>回答课程: {{ answer.courseName || '未知课程' }}</span>
          <span>回答时间: {{ formatTime(answer.createTime) }}</span>
          <span>点赞数: {{ answer.likes || 0 }}</span>
          <el-button type="danger" size="mini" @click="deleteAnswer(answer.courseId, answer.questionId, answer.id)">删除</el-button>
        </div>
      </el-card>
    </div>
    <p v-else class="no-data">您还没有回答过问题。</p>
  </div>
</template>

<script>
import axios from 'axios';
// 替换为标准 ES6 导入语法
import { format } from 'date-fns';
export default {
  props: ['userId'], // 接收用户ID
  data() {
    return {
      answers: [],
    };
  },
  created() {
    if (this.userId) {
      this.fetchMyAnswers();
    } else {
      this.$message.error('未提供用户ID，无法加载我的回答。');
    }
  },
  methods: {
    async fetchMyAnswers() {
      try {
        const response = await axios.get(`/api/${this.userId}/answers`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        if (response.data.code === 200 && response.data.data) {
          this.answers = response.data.data;
        } else {
          this.$message.error(response.data.msg || '获取我的回答失败');
        }
      } catch (error) {
        console.error('获取我的回答失败:', error);
        this.$message.error('获取我的回答失败，请检查网络或服务器');
      }
    },
    viewQuestionDetail(courseId, questionId) {
      this.$router.push(`/course/${courseId}/question/${questionId}`);
    },
    async deleteAnswer(courseId, questionId, answerId) {
      this.$confirm('确定要删除这个回答吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const response = await axios.delete(`/api/${courseId}/question/${questionId}/answer/${answerId}`, {
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
          });
          if (response.data.code === 200) {
            this.$message.success('回答删除成功！');
            this.fetchMyAnswers(); // 刷新列表
          } else {
            this.$message.error(response.data.msg || '删除回答失败');
          }
        } catch (error) {
          console.error('删除回答失败:', error);
          this.$message.error('删除回答失败，请检查网络或权限');
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
.my-answers-page {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}
.answer-list {
  margin-top: 20px;
}
.answer-card {
  margin-bottom: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}
.answer-card .clearfix:before,
.answer-card .clearfix:after {
  display: table;
  content: "";
}
.answer-card .clearfix:after {
  clear: both
}
.answer-meta {
  margin-top: 15px;
  font-size: 0.85em;
  color: #909399;
  display: flex;
  align-items: center;
  gap: 15px;
}
.question-title-link {
  color: #409EFF;
  cursor: pointer;
  text-decoration: underline;
}
.question-title-link:hover {
  color: #66b1ff;
}
.no-data {
  text-align: center;
  color: #909399;
  margin-top: 50px;
}
</style>