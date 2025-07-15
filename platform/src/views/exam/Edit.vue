<template>
  <div class="exam-edit">
    <el-card v-loading="loading">
      <template #header>
        <div class="card-header">
          <h2>{{ isEdit ? '编辑考试' : '创建考试' }}</h2>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="120px"
      >
        <!-- 基本信息 -->
        <el-divider>基本信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="考试名称" prop="title">
              <el-input v-model="form.title" placeholder="请输入考试名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属课程" prop="courseId">
              <el-select 
                v-model="form.courseId" 
                placeholder="请选择课程"
                style="width: 100%"
              >
                <el-option
                  v-for="item in courseOptions"
                  :key="item.id"
                  :label="item.title"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="考试时长(分钟)" prop="duration">
              <el-input-number 
                v-model="form.duration" 
                :min="1"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="及格分数" prop="passingScore">
              <el-input-number 
                v-model="form.passingScore" 
                :min="0"
                :max="100"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="考试说明" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="请输入考试说明"
          />
        </el-form-item>

        <el-form-item label="考试设置">
          <el-checkbox v-model="form.shuffleQuestions">随机题目顺序</el-checkbox>
          <el-checkbox v-model="form.shuffleOptions">随机选项顺序</el-checkbox>
        </el-form-item>

        <!-- 试题管理 -->
        <el-divider>试题管理</el-divider>
        <div class="question-tools">
          <el-button type="primary" @click="handleAddQuestion">添加试题</el-button>
          <el-button type="success" @click="handleImportQuestions">导入试题</el-button>
        </div>

        <div class="question-list">
          <div 
            v-for="(question, index) in form.questions" 
            :key="index"
            class="question-item"
          >
            <div class="question-header">
              <span class="question-index">第 {{ index + 1 }} 题</span>
              <div class="question-actions">
                <el-button 
                  link
                  type="primary" 
                  @click="handleEditQuestion(index)"
                >
                  编辑
                </el-button>
                <el-button 
                  link
                  type="danger" 
                  @click="handleDeleteQuestion(index)"
                >
                  删除
                </el-button>
              </div>
            </div>
            
            <div class="question-content">
              <div class="question-type">
                {{ questionTypeMap[question.type] }}
                <span class="question-score">（{{ question.score }}分）</span>
              </div>
              <div class="question-title">{{ question.title }}</div>
              
              <!-- 选择题选项 -->
              <template v-if="question.type === 'single' || question.type === 'multiple'">
                <div 
                  v-for="(option, optionIndex) in question.options" 
                  :key="optionIndex"
                  class="question-option"
                >
                  <span class="option-label">{{ optionLabels[optionIndex] }}.</span>
                  {{ option.content }}
                </div>
              </template>
            </div>
          </div>
        </div>

        <!-- 提交按钮 -->
        <el-form-item>
          <el-button type="primary" @click="submitForm">保存</el-button>
          <el-button @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 试题编辑对话框 -->
    <el-dialog
      v-model="questionDialog.visible"
      :title="questionDialog.isEdit ? '编辑试题' : '添加试题'"
      width="60%"
    >
      <el-form
        ref="questionFormRef"
        :model="questionForm"
        :rules="questionRules"
        label-width="100px"
      >
        <el-form-item label="题目类型" prop="type">
          <el-radio-group v-model="questionForm.type">
            <el-radio label="single">单选题</el-radio>
            <el-radio label="multiple">多选题</el-radio>
            <el-radio label="judge">判断题</el-radio>
            <el-radio label="essay">简答题</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="分值" prop="score">
          <el-input-number 
            v-model="questionForm.score" 
            :min="1"
            :max="100"
          />
        </el-form-item>

        <el-form-item label="题目内容" prop="title">
          <el-input
            v-model="questionForm.title"
            type="textarea"
            :rows="3"
            placeholder="请输入题目内容"
          />
        </el-form-item>

        <!-- 选择题选项 -->
        <template v-if="questionForm.type === 'single' || questionForm.type === 'multiple'">
          <el-divider>选项</el-divider>
          <div 
            v-for="(option, index) in questionForm.options"
            :key="index"
            class="option-item"
          >
            <el-checkbox
              v-if="questionForm.type === 'multiple'"
              v-model="option.isCorrect"
            />
            <el-radio
              v-else
              v-model="questionForm.correctOption"
              :label="index"
            />
            <el-input
              v-model="option.content"
              placeholder="请输入选项内容"
            >
              <template #prepend>
                {{ optionLabels[index] }}
              </template>
            </el-input>
            <el-button
              type="danger"
              circle
              @click="removeOption(index)"
              v-if="questionForm.options.length > 2"
            >
              <el-icon><Delete /></el-icon>
            </el-button>
          </div>
          <div class="add-option">
            <el-button 
              type="primary" 
              plain
              @click="addOption"
              :disabled="questionForm.options.length >= 6"
            >
              添加选项
            </el-button>
          </div>
        </template>

        <!-- 判断题答案 -->
        <template v-if="questionForm.type === 'judge'">
          <el-form-item label="正确答案" prop="correctAnswer">
            <el-radio-group v-model="questionForm.correctAnswer">
              <el-radio :label="true">正确</el-radio>
              <el-radio :label="false">错误</el-radio>
            </el-radio-group>
          </el-form-item>
        </template>

        <!-- 简答题答案 -->
        <template v-if="questionForm.type === 'essay'">
          <el-form-item label="参考答案" prop="correctAnswer">
            <el-input
              v-model="questionForm.correctAnswer"
              type="textarea"
              :rows="3"
              placeholder="请输入参考答案"
            />
          </el-form-item>
        </template>
      </el-form>
      <template #footer>
        <el-button @click="questionDialog.visible = false">取消</el-button>
        <el-button type="primary" @click="submitQuestion">确定</el-button>
      </template>
    </el-dialog>

    <!-- 导入试题对话框 -->
    <el-dialog
      v-model="importDialog.visible"
      title="导入试题"
      width="500px"
    >
      <el-upload
        class="upload-demo"
        action="/api/question/import"
        :headers="uploadHeaders"
        :on-success="handleImportSuccess"
        :on-error="handleImportError"
        accept=".xlsx,.xls"
      >
        <el-button type="primary">选择文件</el-button>
        <template #tip>
          <div class="el-upload__tip">
            请上传 Excel 文件，<el-button link type="primary" @click="downloadTemplate">下载模板</el-button>
          </div>
        </template>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/modules/user'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete } from '@element-plus/icons-vue'
import { getExamDetail, createExam, updateExam } from '@/api/exam'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const formRef = ref(null)
const loading = ref(false)

// 是否是编辑模式
const isEdit = computed(() => !!route.params.id)

// 题型映射
const questionTypeMap = {
  single: '单选题',
  multiple: '多选题',
  judge: '判断题',
  essay: '简答题'
}

// 选项标签
const optionLabels = ['A', 'B', 'C', 'D', 'E', 'F']

// 表单数据
const form = reactive({
  title: '',
  courseId: '',
  duration: 60,
  passingScore: 60,
  description: '',
  shuffleQuestions: false,
  shuffleOptions: false,
  questions: []
})

// 表单验证规则
const rules = {
  title: [
    { required: true, message: '请输入考试名称', trigger: 'blur' }
  ],
  courseId: [
    { required: true, message: '请选择所属课程', trigger: 'change' }
  ],
  duration: [
    { required: true, message: '请输入考试时长', trigger: 'blur' }
  ],
  passingScore: [
    { required: true, message: '请输入及格分数', trigger: 'blur' }
  ]
}

// 试题对话框
const questionDialog = reactive({
  visible: false,
  isEdit: false,
  editIndex: -1
})

// 试题表单
const questionForm = reactive({
  type: 'single',
  score: 5,
  title: '',
  options: [
    { content: '', isCorrect: false },
    { content: '', isCorrect: false }
  ],
  correctOption: 0,
  correctAnswer: ''
})

// 试题验证规则
const questionRules = {
  title: [
    { required: true, message: '请输入题目内容', trigger: 'blur' }
  ],
  score: [
    { required: true, message: '请输入分值', trigger: 'blur' }
  ]
}

// 导入对话框
const importDialog = reactive({
  visible: false
})

// 获取考试详情
const getExam = async () => {
  if (!isEdit.value) return
  
  loading.value = true
  try {
    const res = await getExamDetail(route.params.id)
    Object.assign(form, res.data)
  } catch (error) {
    ElMessage.error('获取考试信息失败')
  } finally {
    loading.value = false
  }
}

// 添加试题
const handleAddQuestion = () => {
  questionDialog.isEdit = false
  questionDialog.editIndex = -1
  Object.assign(questionForm, {
    type: 'single',
    score: 5,
    title: '',
    options: [
      { content: '', isCorrect: false },
      { content: '', isCorrect: false }
    ],
    correctOption: 0,
    correctAnswer: ''
  })
  questionDialog.visible = true
}

// 编辑试题
const handleEditQuestion = (index) => {
  questionDialog.isEdit = true
  questionDialog.editIndex = index
  const question = form.questions[index]
  Object.assign(questionForm, JSON.parse(JSON.stringify(question)))
  questionDialog.visible = true
}

// 删除试题
const handleDeleteQuestion = (index) => {
  ElMessageBox.confirm(
    '确认删除该试题吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    form.questions.splice(index, 1)
    ElMessage.success('删除成功')
  })
}

// 添加选项
const addOption = () => {
  if (questionForm.options.length >= 6) return
  questionForm.options.push({ content: '', isCorrect: false })
}

// 删除选项
const removeOption = (index) => {
  questionForm.options.splice(index, 1)
  if (questionForm.correctOption >= index) {
    questionForm.correctOption = Math.max(0, questionForm.correctOption - 1)
  }
}

// 提交试题
const submitQuestion = async () => {
  const question = JSON.parse(JSON.stringify(questionForm))
  
  if (questionDialog.isEdit) {
    form.questions[questionDialog.editIndex] = question
  } else {
    form.questions.push(question)
  }
  
  questionDialog.visible = false
  ElMessage.success(questionDialog.isEdit ? '更新成功' : '添加成功')
}

// 导入试题
const handleImportQuestions = () => {
  importDialog.visible = true
}

const handleImportSuccess = (res) => {
  form.questions.push(...res.data)
  importDialog.visible = false
  ElMessage.success('导入成功')
}

const handleImportError = () => {
  ElMessage.error('导入失败')
}

// 下载模板
const downloadTemplate = () => {
  window.open('/api/question/template')
}

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    
    loading.value = true
    if (isEdit.value) {
      await updateExam(route.params.id, form)
      ElMessage.success('更新成功')
    } else {
      await createExam(form)
      ElMessage.success('创建成功')
    }
    
    router.push('/exam')
  } catch (error) {
    ElMessage.error(isEdit.value ? '更新失败' : '创建失败')
  } finally {
    loading.value = false
  }
}

// 取消
const cancel = () => {
  router.back()
}

onMounted(() => {
  getExam()
  // TODO: 获取课程列表
})
</script>

<style scoped>
.exam-edit {
  padding: 20px;
}

.question-tools {
  margin-bottom: 20px;
}

.question-list {
  margin-bottom: 20px;
}

.question-item {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  margin-bottom: 16px;
  padding: 16px;
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.question-index {
  font-weight: bold;
}

.question-type {
  color: #606266;
  margin-bottom: 8px;
}

.question-score {
  color: #f56c6c;
}

.question-title {
  margin-bottom: 12px;
}

.question-option {
  margin-bottom: 8px;
}

.option-label {
  margin-right: 8px;
  font-weight: bold;
}

.option-item {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.add-option {
  margin-top: 12px;
}

.upload-demo {
  text-align: center;
}
</style>