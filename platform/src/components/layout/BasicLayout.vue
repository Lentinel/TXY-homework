<template>
  <el-container class="layout">
    <el-aside width="200px">
      <el-menu
        :default-active="route.path"
        router
        class="menu"
      >
        <el-menu-item index="/dashboard">
          <el-icon><DataLine /></el-icon>
          <span>控制台</span>
        </el-menu-item>
        
        <el-sub-menu index="/course">
          <template #title>
            <el-icon><Reading /></el-icon>
            <span>课程管理</span>
          </template>
          <el-menu-item index="/course">课程列表</el-menu-item>
          <el-menu-item index="/course/create">创建课程</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="/forum">
          <template #title>
            <el-icon><ChatDotRound /></el-icon>
            <span>论坛管理</span>
          </template>
          <el-menu-item index="/forum">帖子列表</el-menu-item>
          <el-menu-item index="/forum/category">分类管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-header>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">
              {{ userStore.userInfo.username }}
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/modules/user'
import { DataLine, Reading, ChatDotRound, ArrowDown } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout {
  height: 100vh;
}

.menu {
  height: 100%;
  border-right: none;
}

.el-header {
  border-bottom: 1px solid #dcdfe6;
  background-color: #fff;
}

.header-right {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 100%;
}

.user-info {
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
}
</style>