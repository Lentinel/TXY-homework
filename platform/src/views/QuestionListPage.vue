<template>
  <div class="question-list-page">
    <h2>{{ courseName }} - 课程问答</h2>
    <el-button type="primary" @click="goToAskQuestion">我要提问</el-button>
    <el-button @click="showMyQuestions">我的提问</el-button>
    <el-button @click="showMyAnswers">我的回答</el-button>

    <div v-if="questions.length">
      <el-card v-for="question in questions" :key="question.id" class="question-card">
        <div slot="header" class="clearfix">
          <span>{{ question.title }}</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="viewQuestionDetail(question.courseId, question.id)">查看详情</el-button>
        </div>
        <p>{{ question.content.substring(0, 100) }}...</p>
        <div class="question-meta">
          <span>提问人: {{ question.askerName }}</span>
          <span>提问时间: {{ question.createTime }}</span>
          <span>回答数: {{ question.answerCount }}</span>
          <el-button v-if="isMyQuestion(question.askerId)" type="danger" size="mini" @click="deleteQuestion(question.courseId, question.id)">删除</el-button>
        </div>
      </el-card>
      </div>
    <p v-else>暂无问题，快来提问吧！</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      courseId: 'YOUR_CURRENT_COURSE_ID', // 替换为当前课程ID
      courseName: '示例课程', // 替换为当前课程名称
      questions: [],
      currentUserId: 'YOUR_CURRENT_USER_ID', // 替换为实际用户ID
      currentUserRole: 'student', // student 或 teacher
    };
  },
  created() {
    this.fetchQuestions();
  },
  methods: {
    async fetchQuestions() {
      try {
        const response = await axios.get(`/api/${this.courseId}/question`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        this.questions = response.data.data; // 假设返回问题列表
      } catch (error) {
        console.error('获取问题失败:', error);
        this.$message.error('获取问题失败');
      }
    },
    goToAskQuestion() {
      this.$router.push(`/course/${this.courseId}/ask`); // 假设提问路由是 /course/:courseId/ask
    },
    viewQuestionDetail(courseId, questionId) {
      this.$router.push(`/course/${courseId}/question/${questionId}`); // 假设详情路由是 /course/:courseId/question/:questionId
    },
    showMyQuestions() {
      this.$router.push(`/user/${this.currentUserId}/questions`); // 假设我的提问路由
    },
    showMyAnswers() {
      this.$router.push(`/user/${this.currentUserId}/answers`); // 假设我的回答路由
    },
    isMyQuestion(askerId) {
      // 只有提问者或管理员可以删除
      return this.currentUserRole === 'admin' || askerId === this.currentUserId;
    },
    async deleteQuestion(courseId, questionId) {
      this.$confirm('确定要删除这个问题吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await axios.delete(`/api/${courseId}/question/${questionId}`, {
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
          });
          this.$message.success('问题删除成功！');
          this.fetchQuestions(); // 刷新列表
        } catch (error) {
          console.error('删除问题失败:', error);
          this.$message.error('删除问题失败');
        }
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    }
  }
};
</script>

<style scoped>
.question-list-page {
  padding: 20px;
}
.question-card {
  margin-bottom: 20px;
}
.question-meta {
  margin-top: 10px;
  font-size: 0.9em;
  color: #666;
}
.question-meta span {
  margin-right: 15px;
}
</style>