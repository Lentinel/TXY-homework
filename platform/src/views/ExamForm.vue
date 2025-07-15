<template>
  <el-card style="margin:20px">
    <h2>新建/编辑考试</h2>
    <el-form :model="form" ref="formRef" label-width="100px">
      <el-form-item label="考试名称" prop="title">
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="考试时间" prop="date">
        <el-date-picker
          v-model="form.date"
          type="datetime"
          placeholder="选择日期时间"
        />
      </el-form-item>
      <el-form-item label="添加题目">
        <el-button type="primary" @click="addQuestion">新增题目</el-button>
      </el-form-item>
      <div v-for="(q, idx) in form.questions" :key="idx" style="margin-bottom:16px">
        <el-input v-model="q.text" placeholder="题目描述" />
        <div style="margin-top:8px">
          <el-radio-group v-model="q.answer">
            <el-radio v-for="(opt, i) in q.options" :label="opt" :key="i">{{ opt }}</el-radio>
          </el-radio-group>
        </div>
        <el-button type="text" @click="removeQuestion(idx)">删除题目</el-button>
      </div>
      <el-form-item>
        <el-button type="primary" @click="submitExam">提交</el-button>
        <el-button @click="$router.back()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: 'ExamForm',
  data() {
    return {
      form: {
        title: '',
        date: '',
        questions: []
      }
    }
  },
  methods: {
    addQuestion() {
      this.form.questions.push({ text: '', options: ['A','B','C','D'], answer: 'A' })
    },
    removeQuestion(idx) {
      this.form.questions.splice(idx, 1)
    },
    submitExam() {
      this.$message.success('考试已保存')
      this.$router.push('/exams')
    }
  }
}
</script>