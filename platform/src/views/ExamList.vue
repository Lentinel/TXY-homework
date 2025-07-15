<template>
  <el-card style="margin:20px">
    <h2>考试管理</h2>
    <el-button type="primary" @click="$router.push('/exams/new')">新建考试</el-button>
    <el-table :data="exams" stripe style="margin-top:16px">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="考试名称" />
      <el-table-column prop="date" label="时间" />
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button type="text" @click="editExam(row)">编辑</el-button>
          <el-button type="text" @click="viewResults(row)">查看成绩</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
export default {
  name: 'ExamList',
  data() {
    return {
      exams: [
        { id: 1, title: '期末考试', date: '2025-07-01 09:00' },
        { id: 2, title: '阶段测评', date: '2025-08-15 14:00' }
      ]
    }
  },
  methods: {
    editExam(row) {
      this.$router.push(`/exams/${row.id}`)
    },
    viewResults(row) {
      this.$router.push(`/exams/${row.id}`)
    }
  }
}
</script>