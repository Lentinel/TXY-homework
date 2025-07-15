<template>
  <el-card style="margin:20px">
    <h2>{{ exam.title }} - 成绩与解析</h2>
    <div v-for="(q, i) in exam.questions" :key="i" style="margin-bottom:20px">
      <p>Q{{ i+1 }}. {{ q.text }}</p>
      <p>你的答案：<strong>{{ q.userAnswer }}</strong></p>
      <p>正确答案：<strong>{{ q.answer }}</strong></p>
      <p>解析：{{ q.analysis }}</p>
    </div>
    <h3>总分：{{ exam.score }}/{{ exam.questions.length * 10 }}</h3>
  </el-card>
</template>

<script>
export default {
  name: 'ExamDetail',
  data() {
    return {
      exam: {
        id: 1,
        title: '期末考试',
        questions: [
          { text: '1+1=?', options: ['A','B','C','D'], answer: 'B', userAnswer: 'B', analysis: '1+1 等于 2' }
        ],
        score: 10
      }
    }
  }
}
</script>