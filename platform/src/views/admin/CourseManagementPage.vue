<template>
  <div class="course-management-page">
    <h2>课程管理</h2>
    <el-button @click="$router.go(-1)">返回</el-button>

    <div class="search-bar">
      <el-input v-model="searchCourseName" placeholder="输入课程名搜索" style="width: 200px; margin-right: 10px;"></el-input>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button type="info" @click="openCategoryDialog">管理分类</el-button>
    </div>

    <el-table :data="courses" v-loading="loading">
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="title" label="课程名称"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">
            {{ row.status === 1 ? '已发布' : '待审核' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="recommend" label="推荐状态">
        <template #default="{ row }">
          <el-switch
            v-model="row.recommend"
            :active-value="1"
            :inactive-value="0"
            @change="toggleRecommend(row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button v-if="row.status === 0" type="primary" size="mini" @click="reviewCourse(row.id, 1)">审核通过</el-button>
          <el-button v-if="row.status === 0" type="danger" size="mini" @click="reviewCourse(row.id, 2)">审核不通过</el-button>
          <el-button type="danger" size="mini" @click="deleteCourse(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @current-change="handlePageChange"
      :current-page="pagination.page"
      :page-size="pagination.size"
      :total="pagination.total"
      layout="prev, pager, next"
    ></el-pagination>

    <el-dialog title="课程分类管理" v-model="categoryDialogVisible">
      <el-button type="primary" @click="createCategory">新增分类</el-button>
      <el-table :data="categories">
        <el-table-column prop="name" label="分类名称"></el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button size="mini" @click="updateCategory(row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteCategory(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { ElMessage, ElMessageBox } from 'element-plus';

const courses = ref([]);
const categories = ref([]);
const loading = ref(false);
const searchCourseName = ref('');
const pagination = ref({ page: 1, size: 10, total: 0 });
const categoryDialogVisible = ref(false);

const fetchCourses = async () => {
  loading.value = true;
  try {
    const response = await axios.get('/api/admin/course/course-page', {
      params: { 
        page: pagination.value.page, 
        size: pagination.value.size,
        courseName: searchCourseName.value
      }
    });
    if (response.data.code === 200) {
      courses.value = response.data.data.records;
      pagination.value.total = response.data.data.total;
    }
  } catch (error) {
    ElMessage.error('获取课程列表失败');
  } finally {
    loading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.page = 1;
  fetchCourses();
};

const handlePageChange = (newPage) => {
  pagination.value.page = newPage;
  fetchCourses();
};

const reviewCourse = async (id, status) => {
  try {
    await axios.put(`/api/admin/course/${id}/review`, { courseReviewDTO: { id, status } });
    ElMessage.success('课程审核成功');
    fetchCourses();
  } catch (error) {
    ElMessage.error('课程审核失败');
  }
};

const toggleRecommend = async (course) => {
  try {
    await axios.put(`/api/admin/course/${course.id}/recommend`, null, { params: { recommend: course.recommend } });
    ElMessage.success('推荐状态更新成功');
  } catch (error) {
    ElMessage.error('更新推荐状态失败');
    course.recommend = course.recommend === 1 ? 0 : 1;
  }
};

const deleteCourse = async (id) => {
  if (!confirm('确定要删除该课程吗？')) return;
  try {
    await axios.delete(`/api/admin/course/${id}`);
    ElMessage.success('课程删除成功');
    fetchCourses();
  } catch (error) {
    ElMessage.error('删除课程失败');
  }
};

const openCategoryDialog = async () => {
  categoryDialogVisible.value = true;
  await fetchCategories();
};

const fetchCategories = async () => {
  try {
    const response = await axios.get('/api/admin/course/category');
    if (response.data.code === 200) {
      categories.value = response.data.data.records;
    }
  } catch (error) {
    ElMessage.error('获取分类失败');
  }
};

const createCategory = async () => {
  const { value: categoryName } = await ElMessageBox.prompt('请输入新分类名称', '新增分类', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
  });
  if (categoryName) {
    try {
      await axios.post('/api/admin/course/category/create', { name: categoryName });
      ElMessage.success('分类创建成功');
      await fetchCategories();
    } catch (error) {
      ElMessage.error('分类创建失败');
    }
  }
};

const updateCategory = async (category) => {
  const { value: newName } = await ElMessageBox.prompt('请输入新的分类名称', '编辑分类', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputValue: category.name
  });
  if (newName) {
    try {
      await axios.put(`/api/admin/course/category/${category.id}`, { name: newName });
      ElMessage.success('分类更新成功');
      await fetchCategories();
    } catch (error) {
      ElMessage.error('分类更新失败');
    }
  }
};

const deleteCategory = async (id) => {
  if (!confirm('确定要删除该分类吗？')) return;
  try {
    await axios.delete(`/api/admin/course/category/${id}`);
    ElMessage.success('分类删除成功');
    await fetchCategories();
  } catch (error) {
    ElMessage.error('分类删除失败');
  }
};

onMounted(() => {
  fetchCourses();
});
</script>