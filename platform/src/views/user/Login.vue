<template>
  <div class="login-container">
    <el-card class="form-card">
      <template #header>
        <div class="card-header">
          <el-tabs v-model="activeTab" class="tabs-header">
            <el-tab-pane label="登录" name="login"></el-tab-pane>
            <el-tab-pane label="注册" name="register"></el-tab-pane>
          </el-tabs>
        </div>
      </template>

      <div v-if="activeTab === 'login'">
        <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="loginRules"
            label-width="0px"
            size="large"
            class="login-form"
        >
          <el-form-item prop="username">
            <el-input
                v-model="loginForm.username"
                placeholder="请输入用户名"
                prefix-icon="el-icon-user"
            ></el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                prefix-icon="el-icon-lock"
                show-password
                @keyup.enter.native="handleSubmit"
            ></el-input>
          </el-form-item>

          <el-form-item>
            <el-button
                type="primary"
                style="width: 100%"
                :loading="loading"
                @click="handleSubmit"
            >
              登录
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <div v-else>
        <el-form
            ref="registerFormRef"
            :model="registerForm"
            :rules="registerRules"
            label-width="80px"
            size="large"
            class="register-form"
        >
          <el-form-item label="用户名" prop="username">
            <el-input v-model="registerForm.username" placeholder="请输入用户名" />
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input
                v-model="registerForm.password"
                type="password"
                placeholder="请输入密码"
                show-password
            />
          </el-form-item>

          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
                v-model="registerForm.confirmPassword"
                type="password"
                placeholder="请确认密码"
                show-password
            />
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
            <el-input v-model="registerForm.email" placeholder="请输入邮箱" />
          </el-form-item>

          <el-form-item label="手机号" prop="phone">
            <el-input v-model="registerForm.phone" placeholder="请输入手机号" />
          </el-form-item>

          <el-form-item label="角色" prop="role">
            <el-radio-group v-model="registerForm.role">
              <el-radio :label="0">学生</el-radio>
              <el-radio :label="1">教师</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item>
            <el-button
                type="success"
                style="width: 100%"
                :loading="loading"
                @click="handleSubmit"
            >
              注册
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import axios from 'axios';

const router = useRouter();
const loginFormRef = ref(null);
const registerFormRef = ref(null);
const loading = ref(false);
const activeTab = ref('login');

// 登录表单数据
const loginForm = reactive({
  username: '',
  password: ''
});

// 注册表单数据
const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  phone: '',
  role: 0
});

// 表单验证规则
const loginRules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
});

const registerRules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码不能少于 6 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== registerForm.password) {
          callback(new Error('两次输入的密码不一致!'));
        } else {
          callback();
        }
      },
      trigger: 'blur'
    }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
});

// 根据当前活跃的表单引用
const formRef = computed(() => {
  return activeTab.value === 'login' ? loginFormRef.value : registerFormRef.value;
});

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return;

  try {
    await formRef.value.validate();
    loading.value = true;

    if (activeTab.value === 'login') {
      // 登录
      const response = await axios.post('/api/login', loginForm);
      // 核心修改点：根据您的确认，后端返回的code为1表示成功
      if (response.data.code === 1) { //
        const user = response.data.data;
        // 将 token 和用户信息存储到本地
        localStorage.setItem('token', user.token);
        localStorage.setItem('user', JSON.stringify(user));

        ElMessage.success('登录成功！');

        // 根据用户的角色进行跳转
        if (user.role === 0) {
          router.push('/student-dashboard');
        } else if (user.role === 1) {
          router.push('/teacher-dashboard');
        } else if (user.role === 2) {
          router.push('/admin-dashboard');
        } else {
          router.push('/home'); // 默认跳转
        }
      } else {
        ElMessage.error(response.data.msg || '登录失败');
      }
    } else {
      // 注册
      const { confirmPassword, ...registerData } = registerForm;
      const registerPayload = {
        ...registerData,
        passwordHash: registerData.password
      };
      const response = await axios.post('/api/register', registerPayload);
      if (response.data.code === 1) {
        ElMessage.success('注册成功！请登录。');
        activeTab.value = 'login'; // 注册成功后自动切换回登录界面
        formRef.value.resetFields(); // 清空表单
      } else {
        ElMessage.error(response.data.msg || '注册失败');
      }
    }
  } catch (error) {
    ElMessage.error(error.message || '请求失败，请检查网络或联系管理员。');
  } finally {
    loading.value = false;
  }
};

// 切换Tab时重置表单
watch(activeTab, () => {
  if (formRef.value) {
    formRef.value.resetFields();
  }
});
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}

.form-card {
  width: 480px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

.card-header {
  text-align: center;
}

.tabs-header {
  font-size: 20px;
}

.login-form,
.register-form {
  padding: 20px;
}
</style>