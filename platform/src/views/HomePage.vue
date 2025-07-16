<template>
  <div class="home-page">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>热门帖子</span>
            </div>
          </template>
          <div v-if="hotPosts.length">
            <div v-for="post in hotPosts" :key="post.id" class="post-item">
              <router-link :to="`/forum/sections/${post.sectionId}/posts/${post.id}`" class="post-link">
                <h4>{{ post.title }}</h4>
              </router-link>
              <p class="post-meta">
                <span>作者: {{ post.authorName }}</span>
                <span>点赞数: {{ post.likeCount }}</span>
              </p>
            </div>
          </div>
          <el-empty v-else description="暂无热门帖子"></el-empty>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>热门课程</span>
            </div>
          </template>
          <div v-if="hotCourses.length">
            <div v-for="course in hotCourses" :key="course.id" class="course-item">
              <router-link :to="`/course/${course.id}`" class="course-link">
                <h4>{{ course.name }}</h4>
              </router-link>
            </div>
          </div>
          <el-empty v-else description="暂无热门课程"></el-empty>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';

export default {
  name: 'HomePage',
  setup() {
    const hotPosts = ref([]);
    const hotCourses = ref([]);

    const fetchHotPosts = async () => {
      try {
        const response = await axios.get('/api/forum/hot-posts');
        if (response.data.code === 200) {
          hotPosts.value = response.data.data;
        }
      } catch (error) {
        console.error('获取热门帖子失败:', error);
      }
    };

    const fetchHotCourses = async () => {
      // 假设有一个获取热门课程的API，例如 /api/courses/hot
      try {
        const response = await axios.get('/api/courses/hot');
        if (response.data.code === 200) {
          hotCourses.value = response.data.data;
        }
      } catch (error) {
        console.error('获取热门课程失败:', error);
      }
    };

    onMounted(() => {
      fetchHotPosts();
      fetchHotCourses();
    });

    return {
      hotPosts,
      hotCourses,
    };
  },
};
</script>

<style scoped>
.home-page {
  padding: 20px;
}
.card-header {
  font-size: 18px;
  font-weight: bold;
}
.post-item, .course-item {
  padding: 10px 0;
  border-bottom: 1px solid #ebeef5;
}
.post-item:last-child, .course-item:last-child {
  border-bottom: none;
}
.post-link, .course-link {
  text-decoration: none;
  color: #303133;
}
.post-link:hover, .course-link:hover {
  color: #409EFF;
}
.post-meta {
  font-size: 12px;
  color: #909399;
  margin-top: 5px;
}
</style>
