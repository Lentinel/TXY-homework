<template>
  <el-card style="margin:20px">
    <h2>课程浏览</h2>
    <el-row :gutter="20">
      <el-col :span="6" v-for="course in courses" :key="course.id">
        <el-card shadow="hover" @click="$router.push(`/courses/${course.id}`)" style="cursor:pointer">
          <h3>{{ course.title }}</h3>
          <p>{{ course.category }} · ￥{{ course.price }}</p>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
export default {
  name: 'CourseBrowser',
  data() {
    return {
      courses: [
        { id: 1, title: 'Vue.js 实战', category: '前端', price: 299 },
        { id: 2, title: 'Spring Boot 入门', category: '后端', price: 399 }
      ]
    }
  }
}
</script>
