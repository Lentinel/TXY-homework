<template>
  <div class="create-post-page">
    <h2>发表新帖</h2>
    <el-form :model="postForm" ref="postForm" label-width="80px">
      <el-form-item label="标题" prop="title" :rules="{ required: true, message: '请输入标题', trigger: 'blur' }">
        <el-input v-model="postForm.title"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content" :rules="{ required: true, message: '请输入内容', trigger: 'blur' }">
        <el-input type="textarea" :rows="8" v-model="postForm.content"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('postForm')">发布</el-button>
        <el-button @click="resetForm('postForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      postForm: {
        title: '',
        content: '',
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            await axios.post('/api/forum', this.postForm, {
              headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } // 假设需要token
            });
            this.$message.success('帖子发布成功！');
            this.$router.push('/forum'); // 发布成功后跳转回论坛主页
          } catch (error) {
            console.error('发布帖子失败:', error);
            this.$message.error('发布帖子失败');
          }
        } else {
          console.log('表单验证失败');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>

<style scoped>
.create-post-page {
  padding: 20px;
}
</style>