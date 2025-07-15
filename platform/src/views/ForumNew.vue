<template>
  <el-card style="margin:20px">
    <h2>新建帖子</h2>
    <el-form :model="form" ref="formRef" label-width="80px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input type="textarea" v-model="form.content" rows="6" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">提交</el-button>
        <el-button @click="$router.back()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: 'ForumNew',
  data() {
    return {
      form: {
        title: '',
        content: ''
      }
    }
  },
  methods: {
    submit() {
      // 这里以后换成真正的 API 调用
      this.$message.success('发布成功')
      this.$router.push('/forum')
    }
  }
}
</script>

<style scoped>
/* 如需样式，可在这里写 */
</style>
