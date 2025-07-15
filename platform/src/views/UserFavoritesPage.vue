<template>
  <div class="user-favorites-page">
    <h2>我的收藏 ({{ totalCount }})</h2>
    <el-table :data="favoriteCourses" style="width: 100%">
      <el-table-column prop="courseName" label="课程名称"></el-table-column>
      <el-table-column prop="teacherName" label="教师"></el-table-column>
      <el-table-column prop="favoriteTime" label="收藏时间"></el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="danger" size="mini" @click="removeFavorite(row.courseId)">取消收藏</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { format } from 'date-fns';

export default {
  setup() {
    const favoriteCourses = ref([]);
    const totalCount = ref(0);
    const userId = ref(1); // 假设当前用户ID为1

    const fetchFavorites = async () => {
      try {
        // 获取收藏列表 API: GET /api/favorites/courses
        const response = await axios.get('/api/favorites/courses', { params: { userId: userId.value } });
        if (response.data.code === 200) {
          favoriteCourses.value = response.data.data.map(item => ({
            ...item,
            favoriteTime: format(new Date(item.favoriteTime), 'yyyy-MM-dd HH:mm')
          }));
        }
        // 获取收藏数量 API: GET /api/favorites/courses/count
        const countRes = await axios.get('/api/favorites/courses/count', { params: { userId: userId.value } });
        if (countRes.data.code === 200) {
          totalCount.value = countRes.data.data;
        }
      } catch (error) {
        console.error('获取收藏失败:', error);
      }
    };

    const removeFavorite = async (courseId) => {
      try {
        // 取消收藏 API: DELETE /api/favorites/courses/{courseId}
        await axios.delete(`/api/favorites/courses/${courseId}`, { params: { userId: userId.value } });
        alert('取消收藏成功！');
        fetchFavorites(); // 刷新列表
      } catch (error) {
        console.error('取消收藏失败:', error);
      }
    };

    onMounted(() => {
      fetchFavorites();
    });

    return {
      favoriteCourses,
      totalCount,
      removeFavorite,
    };
  },
};
</script>