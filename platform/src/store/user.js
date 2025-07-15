import axios from 'axios';
import { useRouter } from 'vue-router';
import {defineStore} from "pinia";

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,  // 存放已登录用户信息
    token: null  // 存放认证令牌
  }),
  actions: {
    async login({ username, password }) {
      try {
        // 调用登录 API
        const response = await axios.post('/api/login', {
          username,
          password
        });

        // 存储用户信息和 token
        this.user = response.data.user;
        this.token = response.data.token;

        // 将 token 存入 localStorage
        localStorage.setItem('token', response.data.token);

        // 设置 axios 请求头，带上 token
        axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;

        // 根据用户角色跳转到不同前端路由路径
        this.navigateByRole(response.data.user.role);
      } catch (error) {
        // 清除可能存在的无效 token
        this.token = null;
        localStorage.removeItem('token');

        // 处理错误
        const errorMessage = error.response?.data?.message || '登录失败，请重试';
        throw new Error(errorMessage);
      }
    },
    navigateByRole(role) {
      // 定义角色到前端路由路径的映射
      const routeMap = {
        2: '/admin/courses',    // 管理员路径
        1: '/teacher/courses',  // 教师路径
        0: '/student/courses'   // 学生路径
      };

      // 获取目标路由路径
      const targetRoute = routeMap[role] || '/default'; // 默认路径

      // 使用 router 进行跳转
      const router = useRouter();
      router.push(targetRoute);
    },
    logout() {
      // 清除用户信息和 token
      this.user = null;
      this.token = null;

      // 从 localStorage 移除 token
      localStorage.removeItem('token');

      // 清除 axios 请求头中的 token
      delete axios.defaults.headers.common['Authorization'];

      // 跳转到登录页
      const router = useRouter();
      router.push('/login');
    }
  }
})