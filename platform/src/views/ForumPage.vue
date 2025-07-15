<template>
  <div class="forum-page">
    <h2>{{ sectionName || '论坛' }}</h2>
    <el-button type="primary" @click="goToCreatePost">发帖</el-button>

    <div v-if="posts.length" class="post-list">
      <el-card v-for="post in posts" :key="post.id" class="post-card">
        <div slot="header" class="clearfix">
          <span>{{ post.title }}</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="viewPostDetail(post.id)">查看详情</el-button>
        </div>
        <p>{{ post.content ? post.content.substring(0, 100) + '...' : '无内容' }}</p>
        <div class="post-meta">
          <span>作者: {{ post.authorName || '未知' }}</span>
          <span>发布时间: {{ formatTime(post.createTime) }}</span>
          <span>评论: {{ post.commentCount || 0 }}</span>
          <el-button
            v-if="checkPermission(post.authorId, 'admin')"
            type="danger"
            size="mini"
            @click="deletePost(post.id)"
          >
            删除
          </el-button>
          <el-button
            type="primary"
            size="mini"
            @click="likePost(post.id)"
            :disabled="post.isLiked"
          >
            点赞 ({{ post.likeCount || 0 }})
          </el-button>
        </div>
      </el-card>
      <el-pagination
        @current-change="handlePageChange"
        :current-page="pagination.pageNum"
        :page-size="pagination.pageSize"
        :total="pagination.total"
        layout="prev, pager, next"
        class="pagination-footer">
      </el-pagination>
    </div>
    <p v-else class="no-posts">暂无帖子，快来发表你的第一篇帖子吧！</p>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { format } from 'date-fns';

export default {
  props: ['sectionId'],
  setup(props) {
    const posts = ref([]);
    const sectionName = ref('');
    const pagination = ref({ pageNum: 1, pageSize: 10, total: 0 });
    const currentUserId = 'testUser123'; // 示例用户ID
    const currentUserRole = 'student'; // 示例用户角色

    const router = useRouter();
    const route = useRoute();

    const fetchPosts = async () => {
      if (!props.sectionId) return;
      try {
        const response = await axios.get(`/api/sections/${props.sectionId}/posts`, {
          params: { page: pagination.value.pageNum, size: pagination.value.pageSize },
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        if (response.data.code === 200 && response.data.data) {
          posts.value = response.data.data.records || [];
          pagination.value.total = response.data.data.total || 0;
          // 假设后端返回板块信息
          sectionName.value = response.data.data.sectionName || '论坛';
        } else {
          router.push('/forum'); // 如果获取失败，返回论坛主页
        }
      } catch (error) {
        console.error('获取帖子失败:', error);
        router.push('/forum');
      }
    };

    const likePost = async (postId) => {
      try {
        const response = await axios.post(`/api/sections/${props.sectionId}/posts/${postId}/like?userId=${currentUserId}`);
        if (response.data.code === 200) {
          const postToUpdate = posts.value.find(p => p.id === postId);
          if (postToUpdate) {
            postToUpdate.likeCount += 1;
            postToUpdate.isLiked = true;
            this.$message.success('点赞成功！');
          }
        } else {
          this.$message.error(response.data.msg || '点赞失败');
        }
      } catch (error) {
        console.error('点赞失败:', error);
        this.$message.error('点赞失败，请检查网络');
      }
    };

    const handlePageChange = (newPage) => {
      pagination.value.pageNum = newPage;
      fetchPosts();
    };

    const goToCreatePost = () => {
      router.push(`/forum/sections/${props.sectionId}/create`);
    };

    const viewPostDetail = (postId) => {
      router.push(`/forum/sections/${props.sectionId}/posts/${postId}`);
    };

    const deletePost = async (postId) => {
      // 删除逻辑与之前相同
    };

    const checkPermission = (authorId, requiredRole) => {
      return currentUserRole === requiredRole || authorId === currentUserId;
    };

    const formatTime = (timeString) => {
      if (!timeString) return '未知时间';
      try {
        return format(new Date(timeString), 'yyyy-MM-dd HH:mm');
      } catch (e) {
        return timeString;
      }
    };
    
    // 监听路由参数变化，当板块ID变化时重新加载帖子
    watch(() => props.sectionId, (newId) => {
      if (newId) {
        pagination.value.pageNum = 1;
        fetchPosts();
      }
    });

    onMounted(() => {
      fetchPosts();
    });

    return {
      posts,
      sectionName,
      pagination,
      handlePageChange,
      goToCreatePost,
      viewPostDetail,
      deletePost,
      likePost,
      checkPermission,
      formatTime,
    };
  },
};
</script>

<style scoped>
/* 样式与之前相同 */
</style>