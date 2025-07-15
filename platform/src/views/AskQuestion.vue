<template>
  <div class="ask-question-page">
    <h2>提问新问题</h2>
    <el-form :model="questionForm" ref="questionForm" label-width="100px" class="question-form">
      <el-form-item label="课程ID">
        <el-input v-model="questionForm.courseId" disabled></el-input>
      </el-form-item>
      <el-form-item label="问题标题" prop="title" :rules="{ required: true, message: '请输入问题标题', trigger: 'blur' }">
        <el-input v-model="questionForm.title" placeholder="例如：这节课的某个知识点我没听懂..."></el-input>
      </el-form-item>
      <el-form-item label="问题内容" prop="content" :rules="{ required: true, message: '请输入问题详细内容', trigger: 'blur' }">
        <el-input type="textarea" :rows="10" v-model="questionForm.content" placeholder="请详细描述您的问题，以便老师或同学更好地理解并解答。"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('questionForm')">提交问题</el-button>
        <el-button @click="resetForm('questionForm')">重置</el-button>
        <el-button @click="$router.back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios'; // 确保你已经安装了 axios (npm install axios)

export default {
  // 从路由中接收 courseId 参数，例如 /course/123/ask，123 就是 courseId
  props: ['courseId'], 
  data() {
    return {
      questionForm: {
        courseId: this.courseId, // 将从路由获取的 courseId 绑定到表单数据
        title: '',
        content: '',
      }
    };
  },
  methods: {
    submitForm(formName) {
      // 触发表单验证
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          // 表单验证通过
          try {
            // 发送 POST 请求到后端 API
            // 假设创建问题的 API 是 POST /api/{courseId}/question
            const response = await axios.post(`/api/${this.courseId}/question`, this.questionForm, {
              headers: { 
                // 确保用户已登录，并从 localStorage 获取认证 token
                Authorization: `Bearer ${localStorage.getItem('token')}` 
              }
            });

            // 根据后端返回的状态码判断操作是否成功
            if (response.data.code === 200) { // 假设后端成功返回 code: 200
              this.$message.success('问题发布成功！'); // Element UI 的成功提示
              // 发布成功后，跳转回该课程的问答列表页
              this.$router.push(`/course/${this.courseId}/questions`); 
            } else {
              // 后端返回错误信息
              this.$message.error(response.data.msg || '发布问题失败，请稍后再试！');
            }
          } catch (error) {
            // 捕获网络请求或服务器错误
            console.error('发布问题失败:', error);
            this.$message.error('发布问题失败，请检查网络连接或联系管理员。');
          }
        } else {
          // 表单验证失败
          this.$message.warning('请检查并填写所有必填项！');
          return false;
        }
      });
    },
    resetForm(formName) {
      // 重置表单字段
      this.$refs[formName].resetFields();
      // 重新设置 courseId，因为 resetFields 会清空所有
      this.questionForm.courseId = this.courseId; 
    }
  }
};
</script>

<style scoped>
.ask-question-page {
  padding: 20px;
  max-width: 800px; /* 限制页面宽度，使其居中更美观 */
  margin: 0 auto; /* 居中显示 */
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05); /* 添加一些阴影效果 */
}

h2 {
  text-align: center;
  color: #303133;
  margin-bottom: 30px;
}

.question-form {
  margin-top: 20px;
}

/* 调整表单项的间距 */
.el-form-item {
  margin-bottom: 22px;
}

/* 按钮组样式 */
.el-form-item button {
  margin-right: 10px;
}
</style>

