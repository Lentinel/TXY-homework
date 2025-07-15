<template>
  <div class="forum-sections-page">
    <h2>论坛板块</h2>
    <div v-if="hotPosts.length" class="hot-posts-section">
      <h3>热门帖子</h3>
      <el-carousel :interval="4000" type="card" height="200px">
        <el-carousel-item v-for="post in hotPosts" :key="post.id">
          <div class="hot-post-card" @click="goToPostDetail(post.sectionId, post.id)">
            <h4>{{ post.title }}</h4>
            <p>{{ post.summary }}</p>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div v-if="sections.length" class="sections-list">
      <el-card v-for="section in sections" :key="section.id" class="section-card">
        <div class="section-info">
          <div class="section-icon">
            <i :class="getIcon(section.icon)"></i>
          </div>
          <div class="section-details">
            <h3>{{ section.name }}</h3>
            <p>{{ section.description }}</p>
          </div>
        </div>
        <el-button type="primary" @click="goToSectionPosts(section.id)">进入板块</el-button>
      </el-card>
    </div>
    <p v-else class="empty-text">暂无论坛板块。</p>
  </div>
</template>

<script>
import axios from 'axios';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';

export default {
  setup() {
    const sections = ref([]);
    const hotPosts = ref([]);
    const router = useRouter();

    const fetchSections = async () => {
      try {
        const response = await axios.get('/api/forum/sections');
        if (response.data.code === 200) {
          sections.value = response.data.data || [];
        }
      } catch (error) {
        console.error('获取板块失败:', error);
      }
    };

    const fetchHotPosts = async () => {
      try {
        const response = await axios.get('/api/forum/hot-posts');
        if (response.data.code === 200) {
          hotPosts.value = response.data.data || [];
        }
      } catch (error) {
        console.error('获取热门帖子失败:', error);
      }
    };

    const goToSectionPosts = (sectionId) => {
      router.push(`/forum/sections/${sectionId}`);
    };
    
    const goToPostDetail = (sectionId, postId) => {
      router.push(`/forum/sections/${sectionId}/posts/${postId}`);
    };

    const getIcon = (iconName) => {
      // 假设后端返回的是 Element UI 的图标类名，例如 el-icon-message
      return iconName;
    };

    onMounted(() => {
      fetchSections();
      fetchHotPosts();
    });

    return {
      sections,
      hotPosts,
      goToSectionPosts,
      goToPostDetail,
      getIcon,
    };
  },
};
</script>

<style scoped>
.forum-sections-page {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}
.hot-posts-section {
  margin-bottom: 30px;
}
.hot-post-card {
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 8px;
  cursor: pointer;
  height: 100%;
}
.section-card {
  margin-bottom: 20px;
}
.section-info {
  display: flex;
  align-items: center;
  gap: 20px;
}
.section-icon i {
  font-size: 40px;
  color: #409eff;
}
.section-details h3 {
  margin: 0;
}
.section-details p {
  margin: 5px 0 0;
  color: #606266;
}
.empty-text {
  text-align: center;
  color: #909399;
}
</style>