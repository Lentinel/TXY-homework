<template>
  <el-card style="margin:20px">
    <h2>课程管理</h2>
    <el-button type="primary" @click="$router.push('/courses/new')">新建课程</el-button>

    <el-empty v-if="!loading && courses.length===0 && !error" description="暂无课程" />
    <el-loading v-if="loading" />
    <el-alert v-if="error" type="error" :title="error" show-icon />

    <el-table v-if="!loading && courses.length" :data="courses" stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="课程名称" />
      <el-table-column prop="category" label="分类" />
      <el-table-column prop="price" label="价格" />
      <el-table-column label="操作" width="260">
        <template #default="{ row }">
          <el-button type="text" @click="edit(row)">编辑</el-button>
          <el-button type="text" @click="remove(row)">删除</el-button>
          <el-button type="text" @click="approve(row)">审核通过</el-button>
          <el-button type="text" @click="reject(row)">驳回</el-button>
          <el-button type="text" @click="toggleRecommend(row)">推荐/取消</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
// 不依赖后端，先用本地模拟数据
export default {
  name: 'CourseList',
  data() {
    return {
      courses: [
        { id: 1, title: 'Vue.js 从入门到实战', category: '前端', price: 299 },
        { id: 2, title: 'Spring Boot 极速开发', category: '后端', price: 399 }
      ],
      loading: false,
      error: null
    }
  },
  methods: {
    edit(row) {
      this.$router.push(`/courses/${row.id}/edit`)
    },
    remove(row) {
      this.courses = this.courses.filter(c => c.id !== row.id)
    },
    approve(row) {
      this.$message.success(`课程 ${row.id} 审核通过`)
    },
    reject(row) {
      this.$message.warning(`课程 ${row.id} 已被驳回`)
    },
    toggleRecommend(row) {
      this.$message.info(`课程 ${row.id} 推荐状态切换`)
    }
  }
}
</script>