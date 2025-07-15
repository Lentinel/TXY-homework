<template>
  <div class="exam-result">
    <el-card v-loading="loading">
      <template #header>
        <div class="result-header">
          <h2>考试结果</h2>
        </div>
      </template>

      <!-- 成绩信息 -->
      <el-result
        :icon="result.passed ? 'success' : 'error'"
        :title="result.passed ? '恭喜你通过考试！' : '很遗憾，未通过考试'"
        :sub-title="`得分：${result.score}分 (及格分数：${result.passingScore}分)`"
      >
        <template #extra>
          <el-button type="primary" @click="viewDetail">查看详情</el-button>
          <el-button @click="backToList">返回列表</el-button>
        </template>
      </el-result>

      <!-- 答题详情 -->
      <div v-if="showDetail" class="answer-detail">
        <el-divider>答题详情</el-divider>
        
        <div 
          v-for="(question, index) in result.questions" 
          :key="index"
          class="question-item"
        >
          <div class="question-header">
            <span class="question-index">第 {{ index + 1 }} 题</span>
            <span :class="['question-result', question.correct ? 'correct' : 'wrong']">
              {{ question.correct ? '正确' : '错误' }}
              （{{ question.score }}分）
            </span>
          </div>

          <div class="question-content">
            <div class="question-type">
              {{ questionTypeMap[question.type] }}
            </div>
            <div class="question-title">{{ question.title }}</div>

            <!-- 选择题 -->
            <template v-if="question.type === 'single' || question.type === 'multiple'">
              <div 
                v-for="(option, optionIndex) in question.options"
                :key="optionIndex"
                :class="[
                  'question-option',
                  {
                    'correct-option': option.isCorrect,
                    'wrong-option': isWrongOption(question, optionIndex)
                  }
                ]"
              >
                <span class="option-label">{{ optionLabels[optionIndex] }}.</span>
                {{ option.content }}
              </div>
            </template>

            <!-- 判断题 -->
            <template v-if="question.type === 'judge'">
              <div class="judge-answer">
                <div>你的答案：{{ question.userAnswer ? '正确' : '错误' }}</div>
                <div>正确答案：{{ question.correctAnswer ? '正确' : '错误' }}</div>
              </div>
            </template>

            <!-- 简答题 -->
            <template v-if="question.type === 'essay'">
              <div class="essay-answer">
                <div class="answer-item">
                  <h4>你的答案：</h4>
                  <div class="answer-content">{{ question.userAnswer || '未作答' }}</div>
                </div>
                <div class="answer-item">
                  <h4>参考答案：</h4>
                  <div class="answer-content">{{ question.correctAnswer }}</div>
                </div>
              </div>
            </template>

            <!-- 解析 -->
            <div v-if="question.analysis" class="question-analysis">
              <h4>解析：</h4>
              <div class="analysis-content">{{ question.analysis }}</div>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getExamResult } from '@/api/exam'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const result = ref({})
const showDetail = ref(false)

// 题型映射
const questionTypeMap = {
  single: '单选题',
  multiple: '多选题',
  judge: '判断题',
  essay: '简答题'
}

// 选项标签
const optionLabels = ['A', 'B', 'C', 'D', 'E', 'F']

// 判断是否是错误选项
const isWrongOption = (question, optionIndex) => {
  if (question.type === 'single') {
    return question.userAnswer === optionIndex && !question.correct
  } else {
    return question.userAnswer.includes(optionIndex) && !question.correct
  }
}

// 获取考试结果
const getResult = async () => {
  loading.value = true
  try {
    const res = await getExamResult(route.params.id)
    result.value = res.data
  } catch (error) {
    ElMessage.error('获取考试结果失败')
  } finally {
    loading.value = false
  }
}

// 查看详情
const viewDetail = () => {
  showDetail.value = true
}

// 返回列表
const backToList = () => {
  router.push('/exam')
}

onMounted(() => {
  getResult()
})
</script>

<style scoped>
.exam-result {
  padding: 20px;
}

.question-item {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  margin-bottom: 20px;
  padding: 20px;
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.question-index {
  font-weight: bold;
}

.question-result {
  font-weight: bold;
  &.correct {
    color: #67c23a;
  }
  &.wrong {
    color: #f56c6c;
  }
}

.question-type {
  color: #606266;
  margin-bottom: 8px;
}

.question-title {
  margin-bottom: 16px;
}

.question-option {
  margin-bottom: 8px;
  padding: 8px;
  border-radius: 4px;
  
  &.correct-option {
    background-color: #f0f9eb;
    color: #67c23a;
  }
  
  &.wrong-option {
    background-color: #fef0f0;
    color: #f56c6c;
  }
}

.option-label {
  margin-right: 8px;
  font-weight: bold;
}

.judge-answer, .essay-answer {
  margin-top: 16px;
}

.answer-item {
  margin-bottom: 16px;
  
  h4 {
    margin: 0 0 8px;
  }
}

.answer-content {
  padding: 12px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.question-analysis {
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px dashed #dcdfe6;
  
  h4 {
    margin: 0 0 8px;
    color: #409eff;
  }
  
  .analysis-content {
    color: #606266;
  }
}
</style>