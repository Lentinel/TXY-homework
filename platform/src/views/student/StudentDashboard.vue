<template>
  <div class="student-dashboard">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>欢迎你，同学！</span>
            </div>
          </template>
          <div class="welcome-message">
            <p>欢迎回到在线教育平台，开始您的学习之旅吧！</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>我收藏的课程 ({{ favoriteCourses.length }})</span>
            </div>
          </template>
          <div v-if="favoriteCourses.length">
            <el-table :data="favoriteCourses" style="width: 100%">
              <el-table-column prop="courseName" label="课程名称"></el-table-column>
              <el-table-column prop="teacherName" label="教师"></el-table-column>
              <el-table-column label="操作">
                <template #default="{ row }">
                  <el-button type="primary" size="mini" @click="goToCourse(row.courseId)">进入学习</el-button>
                  <el-button type="danger" size="mini" @click="removeFavorite(row.courseId)">取消收藏</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-empty v-else description="您还没有收藏任何课程"></el-empty>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { ElMessage } from 'element-plus';

const router = useRouter();
const favoriteCourses = ref([]);
const userId = ref(1); // 假设当前用户ID为1

const fetchFavoriteCourses = async () => {
  try {
    const response = await axios.get('/api/favorites/courses', { params: { userId: userId.value } });
    if (response.data.code === 200) {
      favoriteCourses.value = response.data.data;
    }
  } catch (error) {
    ElMessage.error('获取收藏课程失败');
  }
};

const goToCourse = (courseId) => {
  // 假设进入课程学习的路由是 /course/:courseId
  router.push(`/course/${courseId}`);
};

const removeFavorite = async (courseId) => {
  try {
    await axios.delete(`/api/favorites/courses/${courseId}`, { params: { userId: userId.value } });
    ElMessage.success('取消收藏成功');
    fetchFavoriteCourses(); // 刷新列表
  } catch (error) {
    ElMessage.error('取消收藏失败');
  }
};

onMounted(() => {
  fetchFavoriteCourses();
});
</script>

<style scoped>
.student-dashboard {
  padding: 20px;
}
.welcome-message {
  font-size: 16px;
  color: #606266;
}
</style>