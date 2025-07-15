<template>
  <el-card style="max-width:400px;margin:40px auto">
    <h2>用户登录</h2>
    <el-form ref="formRef" :model="form" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="form.password" autocomplete="off"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onLogin">登录</el-button>
        <el-button @click="$router.push('/register')">注册</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { ref } from 'vue'
import { useUserStore } from '@/store/user'

export default {
  name: 'LoginView',
  setup() {
    const formRef = ref(null)
    const form = ref({ username: '', password: '' })
    const userStore = useUserStore()

    const onLogin = async () => {
      try {
        await userStore.login(form.value)
      } catch (e) {
        formRef.value.$message.error(e.message)
      }
    }
    return { form, formRef, onLogin }
  }
}
</script>
