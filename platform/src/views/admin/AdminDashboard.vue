<template>
  <div class="admin-dashboard">
    <h2>管理员控制台</h2>
    <el-row :gutter="20" class="dashboard-widgets">
      <el-col :span="8">
        <el-card class="dashboard-card" shadow="hover">
          <div class="card-content" @click="goToPage('UserManagementPage')">
            <i class="el-icon-user-solid icon"></i>
            <div class="text">用户管理</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="dashboard-card" shadow="hover">
          <div class="card-content" @click="goToPage('CourseManagementPage')">
            <i class="el-icon-s-management icon"></i>
            <div class="text">课程管理</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="dashboard-card" shadow="hover">
          <div class="card-content" @click="goToPage('OrderManagementPage')">
            <i class="el-icon-s-finance icon"></i>
            <div class="text">订单管理</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goToPage = (name) => {
  router.push({ name: name });
};
</script>

<style scoped>
.admin-dashboard {
  padding: 20px;
}
.dashboard-widgets {
  margin-top: 20px;
}
.dashboard-card {
  cursor: pointer;
}
.card-content {
  text-align: center;
  padding: 20px;
}
.icon {
  font-size: 50px;
  color: #409EFF;
}
.text {
  margin-top: 10px;
  font-size: 18px;
  font-weight: bold;
}
</style>