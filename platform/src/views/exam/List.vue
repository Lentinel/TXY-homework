<template>
  <div class="exam-list">
    <el-card>
      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-form :inline="true" :model="queryParams">
          <el-form-item label="考试名称">
            <el-input v-model="queryParams.title" placeholder="请输入考试名称" clearable />
          </el-form-item>
          <el-form-item label="课程">
            <el-select v-model="queryParams.courseId" placeholder="请选择课程" clearable>
              <el-option
                v-for="item in courseOptions"
                :key="item.id"
                :label="item.title"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQuery">查询</el-button>
            <el-button @click="resetQuery">重置</el-button>
            <el-button 
              type="success" 
              v-if="userStore.isTeacher"
              @click="handleCreate"
            >
              创建考试
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 考试列表 -->
      <el-table
        v-loading="loading"
        :data="examList"
        style="width: 100%"
      >
        <el-table-column prop="title" label="考试名称" />
        <el-table-column prop="courseName" label="所属课程" />
        <el-table-column prop="duration" label="考试时长">
          <template #default="{ row }">
            {{ row.duration }}分钟
          </template>
        </el-table-column>
        <el-table-column prop="passingScore" label="及格分数" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '进行中' : '已结束' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button 
              link 
              type="primary" 
              @click="handleView(row)"
            >
              {{ userStore.isTeacher ? '编辑' : '参加考试' }}
            </el-button>
            <template v-if="userStore.isTeacher">
              <el-button 
                link 
                type="danger" 
                @click="handleDelete(row)"
              >
                删除
              </el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="queryParams.page"
          v-model:page-size="queryParams.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/modules/user'
import { getExamList, deleteExam } from '@/api/exam'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

// 查询参数
const queryParams = reactive({
  page: 1,
  pageSize: 10,
  title: '',
  courseId: ''
})

const loading = ref(false)
const total = ref(0)
const examList = ref([])
const courseOptions = ref([])

// 获取考试列表
const getList = async () => {
  loading.value = true
  try {
    const res = await getExamList(queryParams)
    examList.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取考试列表失败')
  } finally {
    loading.value = false
  }
}

// 查询
const handleQuery = () => {
  queryParams.page = 1
  getList()
}

// 重置
const resetQuery = () => {
  queryParams.title = ''
  queryParams.courseId = ''
  handleQuery()
}

// 分页
const handleSizeChange = (val) => {
  queryParams.pageSize = val
  getList()
}

const handleCurrentChange = (val) => {
  queryParams.page = val
  getList()
}

// 创建考试
const handleCreate = () => {
  router.push('/exam/create')
}

// 查看/编辑考试
const handleView = (row) => {
  if (userStore.isTeacher) {
    router.push(`/exam/edit/${row.id}`)
  } else {
    router.push(`/exam/take/${row.id}`)
  }
}

// 删除考试
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该考试吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await deleteExam(row.id)
      ElMessage.success('删除成功')
      getList()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

onMounted(() => {
  getList()
  // TODO: 获取课程列表作为筛选选项
})
</script>

<style scoped>
.exam-list {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>