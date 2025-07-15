import { defineStore } from 'pinia'
import { login as loginApi } from '@/api/user'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: JSON.parse(localStorage.getItem('userInfo') || '{}'),
    role: localStorage.getItem('role') || ''
  }),
  
  getters: {
    isLoggedIn: (state) => !!state.token,
    isTeacher: (state) => state.role === 'teacher',
    isStudent: (state) => state.role === 'student'
  },
  
  actions: {
    async login(username, password) {
      try {
        const res = await loginApi({ username, password })
        this.token = res.data.token
        this.userInfo = res.data.userInfo
        this.role = res.data.role
        
        // 持久化存储
        localStorage.setItem('token', res.data.token)
        localStorage.setItem('userInfo', JSON.stringify(res.data.userInfo))
        localStorage.setItem('role', res.data.role)
        
        return res
      } catch (error) {
        throw error
      }
    },
    
    logout() {
      this.token = ''
      this.userInfo = {}
      this.role = ''
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      localStorage.removeItem('role')
    }
  }
})