<template>
  <div class="exam-take">
    <el-card v-loading="loading">
      <!-- 考试信息 -->
      <template #header>
        <div class="exam-header">
          <h2>{{ exam.title }}</h2>
          <div class="exam-timer" v-if="examStarted">
            剩余时间：{{ formatTime(remainingTime) }}
          </div>
        </div>
      </template>

      <!-- 考试说明 -->
      <div v-if="!examStarted" class="exam-info">
        <h3>考试说明</h3>
        <p>{{ exam.description }}</p>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="考试时长">
            {{ exam.duration }}分钟
          </el-descriptions-item>
          <el-descriptions-item label="及格分数">
            {{ exam.passingScore }}分
          </el-descriptions-item>
        </el-descriptions>
        <div class="start-exam">
          <el-button type="primary" @click="startExam">开始考试</el-button>
        </div>
      </div>

      <!-- 答题区域 -->
      <div v-else class="exam-content">
        <el-tabs v-model="activeQuestion" type="card">
          <el-tab-pane
            v-for="(question, index) in exam.questions"
            :key="index"
            :label="`第 ${index + 1} 题`"
            :name="index"
          >
            <div class="question-content">
              <div class="question-type">
                {{ questionTypeMap[question.type] }}
                <span class="question-score">（{{ question.score }}分）</span>
              </div>
              <div class="question-title">{{ question.title }}</div>
              
              <!-- 单选题 -->
              <template v-if="question.type === 'single'">
                <el-radio-group v-model="answers[index]">
                  <div
                    v-for="(option, optionIndex) in question.options"
                    :key="optionIndex"
                    class="question-option"
                  >
                    <el-radio :label="optionIndex">
                      {{ optionLabels[optionIndex] }}. {{ option.content }}
                    </el-radio>
                  </div>
                </el-radio-group>
              </template>

              <!-- 多选题 -->
              <template v-if="question.type === 'multiple'">
                <el-checkbox-group v-model="answers[index]">
                  <div
                    v-for="(option, optionIndex) in question.options"
                    :key="optionIndex"
                    class="question-option"
                  >
                    <el-checkbox :label="optionIndex">
                      {{ optionLabels[optionIndex] }}. {{ option.content }}
                    </el-checkbox>
                  </div>
                </el-checkbox-group>
              </template>

              <!-- 判断题 -->
              <template v-if="question.type === 'judge'">
                <el-radio-group v-model="answers[index]">
                  <el-radio :label="true">正确</el-radio>
                  <el-radio :label="false">错误</el-radio>
                </el-radio-group>
              </template>

              <!-- 简答题 -->
              <template v-if="question.type === 'essay'">
                <el-input
                  v-model="answers[index]"
                  type="textarea"
                  :rows="4"
                  placeholder="请输入答案"
                />
              </template>
            </div>

            <!-- 导航按钮 -->
            <div class="question-nav">
              <el-button
                v-if="index > 0"
                @click="activeQuestion = index - 1"
              >
                上一题
              </el-button>
              <el-button
                type="primary"
                v-if="index < exam.questions.length - 1"
                @click="activeQuestion = index + 1"
              >
                下一题
              </el-button>
              <el-button
                type="success"
                v-if="index === exam.questions.length - 1"
                @click="submitExam"
              >
                提交试卷
              </el-button>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-card>

    <!-- 交卷确认对话框 -->
    <el-dialog
      v-model="submitDialog.visible"
      title="确认提交"
      width="400px"
    >
      <p>确认提交试卷吗？提交后将无法修改答案。</p>
      <template #footer>
        <el-button @click="submitDialog.visible = false">取消</el-button>
        <el-button type="primary" @click="confirmSubmit">确认提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, onUnmounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getExamDetail, submitExam } from '@/api/exam'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const exam = ref({})
const examStarted = ref(false)
const activeQuestion = ref(0)
const answers = ref([])
const remainingTime = ref(0)
const timer = ref(null)

// 题型映射
const questionTypeMap = {
  single: '单选题',
  multiple: '多选题',
  judge: '判断题',
  essay: '简答题'
}

// 选项标签
const optionLabels = ['A', 'B', 'C', 'D', 'E', 'F']

// 提交对话框
const submitDialog = reactive({
  visible: false
})

// 格式化时间
const formatTime = (seconds) => {
  const minutes = Math.floor(seconds / 60)
  const remainingSeconds = seconds % 60
  return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`
}

// 获取考试详情
const getExam = async () => {
  loading.value = true
  try {
    const res = await getExamDetail(route.params.id)
    exam.value = res.data
    // 初始化答案数组
    answers.value = new Array(exam.value.questions.length).fill(null).map(() => null)
  } catch (error) {
    ElMessage.error('获取考试信息失败')
  } finally {
    loading.value = false
  }
}

// 开始考试
const startExam = () => {
  examStarted.value = true
  remainingTime.value = exam.value.duration * 60
  startTimer()
}

// 开始计时
const startTimer = () => {
  timer.value = setInterval(() => {
    if (remainingTime.value > 0) {
      remainingTime.value--
    } else {
      clearInterval(timer.value)
      ElMessage.warning('考试时间已到，系统将自动提交试卷')
      submitExam()
    }
  }, 1000)
}

// 提交考试
const submitExam = () => {
  // 检查是否有未答题目
  const unansweredQuestions = answers.value.reduce((acc, curr, index) => {
    if (curr === null) acc.push(index + 1)
    return acc
  }, [])

  if (unansweredQuestions.length > 0) {
    ElMessageBox.confirm(
      `第 ${unansweredQuestions.join(', ')} 题尚未作答，确认提交吗？`,
      '提示',
      {
        confirmButtonText: '确认提交',
        cancelButtonText: '继续作答',
        type: 'warning'
      }
    ).then(() => {
      submitDialog.visible = true
    })
  } else {
    submitDialog.visible = true
  }
}

// 确认提交
const confirmSubmit = async () => {
  loading.value = true
  try {
    await submitExam({
      examId: exam.value.id,
      answers: answers.value
    })
    ElMessage.success('提交成功')
    router.push(`/exam/result/${exam.value.id}`)
  } catch (error) {
    ElMessage.error('提交失败')
  } finally {
    loading.value = false
    submitDialog.visible = false
  }
}

// 组件卸载时清除定时器
onUnmounted(() => {
  if (timer.value) {
    clearInterval(timer.value)
  }
})

// 页面离开提示
window.addEventListener('beforeunload', (e) => {
  if (examStarted.value && !submitDialog.visible) {
    e.preventDefault()
    e.returnValue = ''
  }
})

onMounted(() => {
  getExam()
})
</script>

<style scoped>
.exam-take {
  padding: 20px;
}

.exam-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.exam-timer {
  font-size: 18px;
  color: #f56c6c;
}

.exam-info {
  text-align: center;
  padding: 20px;
}

.start-exam {
  margin-top: 20px;
}

.question-content {
  padding: 20px;
}

.question-type {
  color: #606266;
  margin-bottom: 12px;
}

.question-score {
  color: #f56c6c;
}

.question-title {
  font-size: 16px;
  margin-bottom: 20px;
}

.question-option {
  margin-bottom: 12px;
}

.question-nav {
  display: flex;
  justify-content: space-between;
  padding: 20px;
  border-top: 1px solid #dcdfe6;
  margin-top: 20px;
}

:deep(.el-tabs__header) {
  margin-bottom: 20px;
}

:deep(.el-radio), :deep(.el-checkbox) {
  display: block;
  margin-bottom: 12px;
}
</style>