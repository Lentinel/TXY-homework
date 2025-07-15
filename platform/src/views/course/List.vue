<template>
  <div class="course-container">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="课程名称">
          <el-input v-model="queryParams.title" placeholder="请输入课程名称" clearable />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="queryParams.categoryId" placeholder="请选择分类" clearable>
            <el-option
              v-for="item in categories"
              :key="item.id"
              :label="item.name"
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
            创建课程
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 课程列表 -->
    <el-table
      v-loading="loading"
      :data="courseList"
      style="width: 100%"
    >
      <el-table-column prop="title" label="课程名称" />
      <el-table-column prop="description" label="描述" show-overflow-tooltip />
      <el-table-column prop="price" label="价格">
        <template #default="{ row }">
          ¥{{ row.price.toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column prop="categoryName" label="分类" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">
            {{ row.status === 1 ? '已上架' : '已下架' }}
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
            查看
          </el-button>
          <template v-if="userStore.isTeacher">
            <el-button 
              link 
              type="primary" 
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/modules/user'
import { getCourseList, deleteCourse } from '@/api/course'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

// 查询参数
const queryParams = reactive({
  page: 1,
  pageSize: 10,
  title: '',
  categoryId: ''
})

const loading = ref(false)
const total = ref(0)
const courseList = ref([])
const categories = ref([]) // TODO: 从API获取分类列表

// 获取课程列表
const getList = async () => {
  loading.value = true
  try {
    const res = await getCourseList(queryParams)
    courseList.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取课程列表失败')
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
  queryParams.categoryId = ''
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

// 创建课程
const handleCreate = () => {
  router.push('/course/create')
}

// 查看课程
const handleView = (row) => {
  router.push(`/course/${row.id}`)
}

// 编辑课程
const handleEdit = (row) => {
  router.push(`/course/edit/${row.id}`)
}

// 删除课程
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该课程吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await deleteCourse(row.id)
      ElMessage.success('删除成功')
      getList()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

onMounted(() => {
  getList()
  // TODO: 获取分类列表
})
</script>

<style scoped>
.course-container {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>