<template>
  <div class="course-detail">
    <el-card v-loading="loading">
      <!-- 课程基本信息 -->
      <template #header>
        <div class="card-header">
          <h2 class="title">{{ course.title }}</h2>
          <div class="actions" v-if="userStore.isTeacher">
            <el-button type="primary" @click="handleEdit">编辑课程</el-button>
          </div>
        </div>
      </template>

      <!-- 课程信息 -->
      <el-descriptions :column="2" border>
        <el-descriptions-item label="课程价格">
          ¥{{ course.price?.toFixed(2) }}
        </el-descriptions-item>
        <el-descriptions-item label="课程分类">
          {{ course.categoryName }}
        </el-descriptions-item>
        <el-descriptions-item label="课程状态">
          <el-tag :type="course.status === 1 ? 'success' : 'info'">
            {{ course.status === 1 ? '已上架' : '已下架' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ formatDate(course.createTime) }}
        </el-descriptions-item>
      </el-descriptions>

      <!-- 课程描述 -->
      <div class="description-section">
        <h3>课程描述</h3>
        <div class="description">{{ course.description }}</div>
      </div>

      <!-- 课程章节 -->
      <div class="chapter-section">
        <div class="section-header">
          <h3>课程章节</h3>
          <el-button 
            v-if="userStore.isTeacher"
            type="primary" 
            @click="handleAddChapter"
          >
            添加章节
          </el-button>
        </div>

        <el-collapse v-model="activeChapters">
          <el-collapse-item 
            v-for="chapter in course.chapters" 
            :key="chapter.id"
            :title="chapter.title"
            :name="chapter.id"
          >
            <div class="chapter-content">
              <div class="chapter-desc">{{ chapter.description }}</div>
              
              <!-- 资源列表 -->
              <div class="resource-list" v-if="chapter.resources?.length">
                <div 
                  v-for="resource in chapter.resources"
                  :key="resource.id"
                  class="resource-item"
                >
                  <el-icon><Document /></el-icon>
                  <span class="resource-name">{{ resource.name }}</span>
                  <div class="resource-actions">
                    <el-button 
                      link
                      type="primary"
                      @click="handleViewResource(resource)"
                    >
                      查看
                    </el-button>
                    <el-button 
                      v-if="userStore.isTeacher"
                      link
                      type="danger"
                      @click="handleDeleteResource(resource)"
                    >
                      删除
                    </el-button>
                  </div>
                </div>
              </div>

              <!-- 教师操作按钮 -->
              <div class="chapter-actions" v-if="userStore.isTeacher">
                <el-button 
                  type="primary" 
                  link
                  @click="handleEditChapter(chapter)"
                >
                  编辑章节
                </el-button>
                <el-button 
                  type="primary" 
                  link
                  @click="handleAddResource(chapter)"
                >
                  添加资源
                </el-button>
                <el-button 
                  type="danger" 
                  link
                  @click="handleDeleteChapter(chapter)"
                >
                  删除章节
                </el-button>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>

      <!-- 课程考试 -->
      <div class="exam-section" v-if="course.exams?.length">
        <div class="section-header">
          <h3>课程考试</h3>
          <el-button 
            v-if="userStore.isTeacher"
            type="primary" 
            @click="handleAddExam"
          >
            添加考试
          </el-button>
        </div>

        <el-table :data="course.exams">
          <el-table-column prop="title" label="考试名称" />
          <el-table-column prop="duration" label="考试时长（分钟）" />
          <el-table-column prop="passingScore" label="及格分数" />
          <el-table-column label="操作">
            <template #default="{ row }">
              <el-button 
                link
                type="primary" 
                @click="handleViewExam(row)"
              >
                {{ userStore.isTeacher ? '编辑' : '参加考试' }}
              </el-button>
              <el-button 
                v-if="userStore.isTeacher"
                link
                type="danger" 
                @click="handleDeleteExam(row)"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>

    <!-- 添加/编辑章节对话框 -->
    <el-dialog
      v-model="chapterDialog.visible"
      :title="chapterDialog.type === 'add' ? '添加章节' : '编辑章节'"
      width="500px"
    >
      <el-form
        ref="chapterFormRef"
        :model="chapterForm"
        :rules="chapterRules"
        label-width="80px"
      >
        <el-form-item label="章节名称" prop="title">
          <el-input v-model="chapterForm.title" />
        </el-form-item>
        <el-form-item label="章节描述" prop="description">
          <el-input
            v-model="chapterForm.description"
            type="textarea"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="chapterForm.sortOrder" :min="1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="chapterDialog.visible = false">取消</el-button>
        <el-button type="primary" @click="submitChapter">确定</el-button>
      </template>
    </el-dialog>

    <!-- 上传资源对话框 -->
    <el-dialog
      v-model="resourceDialog.visible"
      title="上传资源"
      width="500px"
    >
      <el-upload
        class="upload-demo"
        action="/api/admin/common/upload"
        :headers="uploadHeaders"
        :on-success="handleUploadSuccess"
        :on-error="handleUploadError"
        :before-upload="beforeUpload"
      >
        <el-button type="primary">选择文件</el-button>
        <template #tip>
          <div class="el-upload__tip">
            支持上传视频、PDF等文件
          </div>
        </template>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/modules/user'
import { getCourseDetail } from '@/api/course'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Document } from '@element-plus/icons-vue'
import { formatDate } from '@/utils/format'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const course = ref({})
const activeChapters = ref([])

// 章节表单
const chapterDialog = reactive({
  visible: false,
  type: 'add' // or 'edit'
})

const chapterForm = reactive({
  title: '',
  description: '',
  sortOrder: 1
})

const chapterRules = {
  title: [
    { required: true, message: '请输入章节名称', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入章节描述', trigger: 'blur' }
  ]
}

// 资源上传
const resourceDialog = reactive({
  visible: false,
  currentChapter: null
})

const uploadHeaders = computed(() => ({
  Authorization: `Bearer ${userStore.token}`
}))

// 获取课程详情
const getCourse = async () => {
  loading.value = true
  try {
    const res = await getCourseDetail(route.params.id)
    course.value = res.data
  } catch (error) {
    ElMessage.error('获取课程详情失败')
  } finally {
    loading.value = false
  }
}

// 编辑课程
const handleEdit = () => {
  router.push(`/course/edit/${course.value.id}`)
}

// 添加章节
const handleAddChapter = () => {
  chapterDialog.type = 'add'
  chapterDialog.visible = true
}

// 编辑章节
const handleEditChapter = (chapter) => {
  chapterDialog.type = 'edit'
  Object.assign(chapterForm, chapter)
  chapterDialog.visible = true
}

// 删除章节
const handleDeleteChapter = (chapter) => {
  ElMessageBox.confirm(
    '确认删除该章节吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await deleteChapter(chapter.id)
      ElMessage.success('删除成功')
      getCourse()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

// 添加资源
const handleAddResource = (chapter) => {
  resourceDialog.currentChapter = chapter
  resourceDialog.visible = true
}

// 上传相关方法
const beforeUpload = (file) => {
  // 可以添加文件类型、大小限制
  return true
}

const handleUploadSuccess = (response) => {
  ElMessage.success('上传成功')
  resourceDialog.visible = false
  getCourse()
}

const handleUploadError = () => {
  ElMessage.error('上传失败')
}

// 考试相关方法
const handleAddExam = () => {
  router.push(`/exam/create?courseId=${course.value.id}`)
}

const handleViewExam = (exam) => {
  if (userStore.isTeacher) {
    router.push(`/exam/edit/${exam.id}`)
  } else {
    router.push(`/exam/take/${exam.id}`)
  }
}

onMounted(() => {
  getCourse()
})
</script>

<style scoped>
.course-detail {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  margin: 0;
}

.description-section,
.chapter-section,
.exam-section {
  margin-top: 20px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.chapter-content {
  padding: 0 20px;
}

.chapter-desc {
  color: #666;
  margin-bottom: 16px;
}

.resource-list {
  margin-top: 12px;
}

.resource-item {
  display: flex;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}

.resource-name {
  margin-left: 8px;
  flex: 1;
}

.chapter-actions {
  margin-top: 16px;
  display: flex;
  gap: 16px;
}

.upload-demo {
  text-align: center;
}
</style>