<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card class="welcome-card">
          <div class="welcome-header">
            <i class="el-icon-school"></i>
            <h2>欢迎回到在线教育平台！</h2>
          </div>
          <div class="welcome-body">
            <p>您好，{{ userName }}！这里是您的控制台概览。</p>
            <p>在这里，您可以快速查看重要的项目数据和最新动态。</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="8">
        <el-card>
          <div slot="header" class="clearfix">
            <span>课程概览</span>
          </div>
          <div class="card-content">
            <div class="statistic-item">
              <span class="statistic-label">课程总数</span>
              <span class="statistic-value">{{ courseCount }}</span>
            </div>
            <div class="statistic-item">
              <span class="statistic-label">已上线课程</span>
              <span class="statistic-value">{{ publishedCourseCount }}</span>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <div slot="header" class="clearfix">
            <span>用户统计</span>
          </div>
          <div class="card-content">
            <div class="statistic-item">
              <span class="statistic-label">学生总数</span>
              <span class="statistic-value">{{ studentCount }}</span>
            </div>
            <div class="statistic-item">
              <span class="statistic-label">教师总数</span>
              <span class="statistic-value">{{ teacherCount }}</span>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <div slot="header" class="clearfix">
            <span>待办事项</span>
          </div>
          <div class="card-content">
            <el-empty description="暂无待办事项"></el-empty>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Dashboard',
  data() {
    return {
      username: '管理员', // 示例用户名，实际应从登录用户信息中获取
      courseCount: 0,
      publishedCourseCount: 0,
      studentCount: 0,
      teacherCount: 0
    };
  },
  created() {
    // 假设你有 API 来获取这些统计数据
    this.fetchDashboardData();
  },
  methods: {
    async fetchDashboardData() {
      try {
        // 假设后端提供一个获取统计数据的API
        const response = await axios.get('/api/dashboard/stats', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        });
        if (response.data.code === 200) {
          const stats = response.data.data;
          this.courseCount = stats.courseCount || 0;
          this.publishedCourseCount = stats.publishedCourseCount || 0;
          this.studentCount = stats.studentCount || 0;
          this.teacherCount = stats.teacherCount || 0;
          // 假设可以获取用户名
          this.username = stats.username || '管理员';
        }
      } catch (error) {
        console.error('获取仪表盘数据失败:', error);
      }
    }
  }
};
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}
.welcome-card {
  text-align: center;
  background-color: #ecf5ff;
  border-color: #d9ecff;
}
.welcome-header {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  margin-bottom: 10px;
}
.welcome-header i {
  font-size: 48px;
  color: #409eff;
}
.welcome-header h2 {
  color: #303133;
  font-size: 28px;
  margin: 0;
}
.welcome-body p {
  color: #606266;
  font-size: 16px;
  margin: 5px 0;
}
.card-content {
  text-align: center;
}
.statistic-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #ebeef5;
}
.statistic-item:last-child {
  border-bottom: none;
}
.statistic-label {
  font-size: 14px;
  color: #909399;
}
.statistic-value {
  font-size: 20px;
  font-weight: bold;
  color: #409eff;
}
.el-empty {
  padding: 0;
}
</style>