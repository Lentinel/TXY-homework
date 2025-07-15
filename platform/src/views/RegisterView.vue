<template>
  <el-card style="max-width:400px;margin:40px auto">
    <h2>用户注册</h2>
    <el-form ref="formRef" :model="form" label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" v-model="form.password" autocomplete="off"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onRegister">注册</el-button>
        <el-button @click="$router.back()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: 'RegisterView',
  data() {
    return { form: { username: '', password: '' } }
  },
  methods: {
    onRegister() {
      this.$message.success('注册成功，请登录')
      this.$router.push('/login')
    }
  }
}
</script>
