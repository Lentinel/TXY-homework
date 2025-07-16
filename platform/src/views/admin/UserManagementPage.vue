<template>
  <div class="user-management-page">
    <h2>用户管理</h2>
    <el-button @click="$router.go(-1)">返回</el-button>

    <div class="search-bar">
      <el-input v-model="searchUsername" placeholder="输入用户名搜索" style="width: 200px; margin-right: 10px;"></el-input>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
    </div>

    <el-table :data="users" v-loading="loading">
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="role" label="角色">
        <template #default="{ row }">
          {{ getRoleText(row.role) }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button size="mini" @click="viewUserDetail(row.id)">详情</el-button>
          <el-button size="mini" :type="row.status === 1 ? 'danger' : 'success'" @click="toggleStatus(row)">
            {{ row.status === 1 ? '禁用' : '启用' }}
          </el-button>
          <el-button size="mini" type="warning" @click="resetPassword(row.id)">重置密码</el-button>
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { ElMessage } from 'element-plus';

const router = useRouter();
const users = ref([]);
const loading = ref(false);
const searchUsername = ref('');
const pagination = ref({ page: 1, size: 10, total: 0 });

const fetchUsers = async () => {
  loading.value = true;
  try {
    const response = await axios.get('/api/admin/user/page', {
      params: { 
        page: pagination.value.page, 
        size: pagination.value.size,
        username: searchUsername.value
      }
    });
    if (response.data.code === 200) {
      users.value = response.data.data.records;
      pagination.value.total = response.data.data.total;
    }
  } catch (error) {
    ElMessage.error('获取用户列表失败');
  } finally {
    loading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.page = 1;
  fetchUsers();
};

const handlePageChange = (newPage) => {
  pagination.value.page = newPage;
  fetchUsers();
};

const viewUserDetail = (id) => {
  router.push(`/admin/user/${id}/detail`);
};

const toggleStatus = async (user) => {
  const newStatus = user.status === 1 ? 0 : 1;
  try {
    await axios.post(`/api/admin/user/${user.id}/status/${newStatus}`);
    user.status = newStatus;
    ElMessage.success('用户状态更新成功');
  } catch (error) {
    ElMessage.error('更新用户状态失败');
  }
};

const resetPassword = async (id) => {
  if (!confirm('确定要重置该用户密码吗？')) return;
  try {
    await axios.post(`/api/admin/user/${id}/reset_pdw`);
    ElMessage.success('密码重置成功');
  } catch (error) {
    ElMessage.error('重置密码失败');
  }
};

const getRoleText = (role) => {
  const roles = { 0: '学生', 1: '教师', 2: '管理员' };
  return roles[role] || '未知';
};

onMounted(() => {
  fetchUsers();
});
</script>

<style scoped>
.user-management-page {
  padding: 20px;
}
.search-bar {
  margin: 20px 0;
}
</style>