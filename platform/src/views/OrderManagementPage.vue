<template>
  <div class="order-management-page">
    <h2>订单管理</h2>
    <el-button @click="$router.go(-1)">返回</el-button>

    <div class="search-bar">
      <el-input v-model="searchOrderId" placeholder="输入订单号搜索" style="width: 200px; margin-right: 10px;"></el-input>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
    </div>

    <el-table :data="orders" v-loading="loading">
      <el-table-column prop="id" label="订单号"></el-table-column>
      <el-table-column prop="courseName" label="课程名称"></el-table-column>
      <el-table-column prop="username" label="购买用户"></el-table-column>
      <el-table-column prop="amount" label="金额"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag>{{ getOrderStatus(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button size="mini" @click="viewOrderDetail(row.id)">详情</el-button>
          <el-button size="mini" type="warning" @click="refundOrder(row.id)">退款</el-button>
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
const orders = ref([]);
const loading = ref(false);
const searchOrderId = ref('');
const pagination = ref({ page: 1, size: 10, total: 0 });

const fetchOrders = async () => {
  loading.value = true;
  try {
    // API: GET /api/admin/order/page
    const response = await axios.get('/api/admin/order/page', {
      params: { 
        page: pagination.value.page, 
        size: pagination.value.size,
        orderId: searchOrderId.value
      }
    });
    if (response.data.code === 200) {
      orders.value = response.data.data.records;
      pagination.value.total = response.data.data.total;
    }
  } catch (error) {
    ElMessage.error('获取订单列表失败');
  } finally {
    loading.value = false;
  }
};

const handleSearch = () => {
  pagination.value.page = 1;
  fetchOrders();
};

const handlePageChange = (newPage) => {
  pagination.value.page = newPage;
  fetchOrders();
};

const viewOrderDetail = async (id) => {
  // API: GET /api/admin/order/{id}/detail
  try {
    const response = await axios.get(`/api/admin/order/${id}/detail`);
    if (response.data.code === 200) {
      // 假设有一个显示详情的对话框或新页面
      alert('订单详情：' + JSON.stringify(response.data.data, null, 2));
    }
  } catch (error) {
    ElMessage.error('获取订单详情失败');
  }
};

const refundOrder = async (id) => {
  if (!confirm('确定要为该订单执行退款操作吗？')) return;
  // API: POST /api/admin/order/{id}/refund
  try {
    await axios.post(`/api/admin/order/${id}/refund`);
    ElMessage.success('退款操作成功');
    fetchOrders(); // 刷新列表
  } catch (error) {
    ElMessage.error('退款操作失败');
  }
};

const getOrderStatus = (status) => {
  const statuses = { 0: '待支付', 1: '已完成', 2: '已退款' };
  return statuses[status] || '未知';
};

onMounted(() => {
  fetchOrders();
});
</script>

<style scoped>
.order-management-page {
  padding: 20px;
}
.search-bar {
  margin: 20px 0;
}
</style>