<template>
  <div class="global-discussions-page">
    <h2>全局讨论</h2>
    <el-table :data="discussions" style="width: 100%">
      <el-table-column prop="title" label="讨论标题"></el-table-column>
      <el-table-column prop="authorName" label="发布者"></el-table-column>
      <el-table-column prop="courseName" label="所属课程"></el-table-column>
      <el-table-column prop="createTime" label="发布时间"></el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="text" @click="goToDiscussionDetail(row.id)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { format } from 'date-fns';

export default {
  setup() {
    const discussions = ref([]);
    const router = useRouter();

    const fetchGlobalDiscussions = async () => {
      // 获取全局讨论列表 API: GET /api/discussions/global
      const response = await axios.get('/api/discussions/global');
      if (response.data.code === 200) {
        discussions.value = response.data.data.map(d => ({
          ...d,
          createTime: format(new Date(d.createTime), 'yyyy-MM-dd HH:mm')
        }));
      }
    };

    const goToDiscussionDetail = (discussionId) => {
      // 注意：这里需要传入 courseId，如果后端返回数据不包含，需要另想办法
      // 假设 discussion 对象包含 courseId
      router.push(`/course/${discussions.value.find(d => d.id === discussionId).courseId}/discussions/${discussionId}`);
    };

    onMounted(() => {
      fetchGlobalDiscussions();
    });

    return {
      discussions,
      goToDiscussionDetail,
    };
  },
};
</script>