<template>
  <div class="teacher-dashboard">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>欢迎你，老师！</span>
              <el-button style="float: right;" type="primary" @click="goToCreateCourse">创建新课程</el-button>
            </div>
          </template>
          <p class="welcome-message">在这里管理您的课程，回答学生问题，并上传教学资源。</p>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>我创建的课程</span>
            </div>
          </template>
          <div v-if="myCourses.length">
            <el-table :data="myCourses" style="width: 100%">
              <el-table-column prop="title" label="课程名称"></el-table-column>
              <el-table-column prop="studentsCount" label="学生数"></el-table-column>
              <el-table-column prop="status" label="状态"></el-table-column>
              <el-table-column label="操作">
                <template #default="{ row }">
                  <el-button type="primary" size="mini" @click="goToEditCourse(row.id)">管理</el-button>
                  <el-button type="info" size="mini" @click="goToCourseDiscussions(row.id)">讨论</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-empty v-else description="您还没有创建任何课程"></el-empty>
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
const myCourses = ref([]);
const userId = ref(2); // 假设当前用户ID为2

const fetchMyCourses = async () => {
  // 假设有一个API用于获取教师创建的课程列表，例如：/api/teacher/courses?teacherId=xxx
  try {
    const response = await axios.get('/api/teacher/courses', { params: { teacherId: userId.value } });
    if (response.data.code === 200) {
      myCourses.value = response.data.data;
    }
  } catch (error) {
    ElMessage.error('获取课程失败');
  }
};

const goToCreateCourse = () => {
  router.push('/course/create');
};

const goToEditCourse = (courseId) => {
  router.push(`/course/edit/${courseId}`);
};

const goToCourseDiscussions = (courseId) => {
  router.push(`/course/${courseId}/discussions`);
};

onMounted(() => {
  fetchMyCourses();
});
</script>

<style scoped>
.teacher-dashboard {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.welcome-message {
  color: #606266;
}
</style>