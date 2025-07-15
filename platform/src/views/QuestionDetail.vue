<template>
  <div class="question-detail-page">
    <el-button type="text" @click="$router.go(-1)">返回问答列表</el-button>
    <el-card v-if="question">
      <h2>{{ question.title }}</h2>
      <p class="question-meta">
        <span>提问人: {{ question.askerName }}</span>
        <span>提问时间: {{ question.createTime }}</span>
      </p>
      <div class="question-content">{{ question.content }}</div>

      <el-divider></el-divider>

      <h3>回答</h3>
      <div class="answer-list">
        <AnswerItem
          v-for="answer in answers"
          :key="answer.id"
          :answer="answer"
          @like-answer="handleLikeAnswer"
          @delete-answer="handleDeleteAnswer"
          :current-user-id="currentUserId"
          :current-user-role="currentUserRole"
        />
        <p v-if="!answers.length">暂无回答，快来抢沙发！</p>
      </div>

      <el-input
        type="textarea"
        :rows="3"
        placeholder="发表你的回答..."
        v-model="newAnswerContent"
        class="answer-input"
      ></el-input>
      <el-button type="primary" @click="submitAnswer" :disabled="!newAnswerContent.trim()">发表回答</el-button>
    </el-card>
    <p v-else>问题加载中或不存在...</p>
  </div>
</template>

<script>
import axios from 'axios';
import AnswerItem from '@/components/AnswerItem.vue'; // 假设 AnswerItem 是一个单独的组件

export default {
  components: {
    AnswerItem
  },
  data() {
    return {
      courseId: null,
      questionId: null,
      question: null,
      answers: [],
      newAnswerContent: '',
      currentUserId: 'YOUR_CURRENT_USER_ID', // 替换为实际用户ID
      currentUserRole: 'student', // student 或 teacher
    };
  },
  created() {
    this.courseId = this.$route.params.courseId;
    this.questionId = this.$route.params.questionId;
    this.fetchQuestionDetail();
    this.fetchAnswers();
  },
  methods: {
    async fetchQuestionDetail() {
      try {
        // 您的API文档中没有获取单个问题详情的API，只有获取课程所有题目
        // 这里假设后端会在 /api/{courseId}/question 返回所有题目，然后前端筛选
        // 理想情况应有 GET /api/{courseId}/question/{questionId}
        const response = await axios.get(`/api/${this.courseId}/question`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        this.question = response.data.data.find(q => q.id == this.questionId);
      } catch (error) {
        console.error('获取问题详情失败:', error);
        this.$message.error('获取问题详情失败');
      }
    },
    async fetchAnswers() {
      try {
        const response = await axios.get(`/api/${this.courseId}/question/${this.questionId}/answer`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        this.answers = response.data.data; // 假设返回回答列表
      } catch (error) {
        console.error('获取回答失败:', error);
        this.$message.error('获取回答失败');
      }
    },
    async submitAnswer() {
      if (!this.newAnswerContent.trim()) {
        this.$message.warning('回答内容不能为空！');
        return;
      }
      try {
        // 您的API文档中没有“创建回答”的API，只有“获取回答”和“删除回答”
        // 这里假设会有一个POST /api/{courseId}/question/{questionId}/answer 用于创建回答
        // 如果没有，请与后端沟通添加此API
        await axios.post(`/api/${this.courseId}/question/${this.questionId}/answer`, {
          content: this.newAnswerContent
        }, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        this.$message.success('回答成功！');
        this.newAnswerContent = '';
        this.fetchAnswers(); // 刷新回答列表
      } catch (error) {
        console.error('发表回答失败:', error);
        this.$message.error('发表回答失败');
      }
    },
    async handleLikeAnswer(answerId) {
      try {
        await axios.post(`/api/${this.courseId}/question/${this.questionId}/answer/${answerId}/like`, {}, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        this.$message.success('点赞成功！');
        this.fetchAnswers(); // 刷新回答，更新点赞数
      } catch (error) {
        console.error('点赞失败:', error);
        this.$message.error('点赞失败');
      }
    },
    async handleDeleteAnswer(answerId) {
      this.$confirm('确定要删除这个回答吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await axios.delete(`/api/${this.courseId}/question/${this.questionId}/answer/${answerId}`, {
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
          });
          this.$message.success('回答删除成功！');
          this.fetchAnswers(); // 刷新列表
        } catch (error) {
          console.error('删除回答失败:', error);
          this.$message.error('删除回答失败');
        }
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    }
  }
};
</script>

<style scoped>
.question-detail-page {
  padding: 20px;
}
.question-meta {
  font-size: 0.9em;
  color: #666;
  margin-bottom: 15px;
}
.question-content {
  line-height: 1.8;
  margin-bottom: 20px;
}
.answer-input {
  margin-top: 20px;
  margin-bottom: 10px;
}
</style>