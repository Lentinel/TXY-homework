<template>
  <div class="create-discussion-page">
    <h2>发布课程讨论</h2>
    <el-form :model="discussionForm" ref="discussionForm" label-width="120px">
      <el-form-item label="讨论标题" prop="title">
        <el-input v-model="discussionForm.title"></el-input>
      </el-form-item>
      <el-form-item label="讨论内容" prop="content">
        <el-input type="textarea" v-model="discussionForm.content"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitDiscussion">发布</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';

export default {
  props: ['courseId'],
  setup(props) {
    const discussionForm = ref({
      title: '',
      content: '',
      courseId: props.courseId,
    });
    const router = useRouter();

    const submitDiscussion = async () => {
      try {
        const response = await axios.post('/api/discussions', discussionForm.value);
        if (response.data.code === 200) {
          this.$message.success('讨论发布成功！');
          router.push(`/course/${props.courseId}/discussions`);
        } else {
          this.$message.error(response.data.msg || '发布失败');
        }
      } catch (error) {
        console.error('发布讨论失败:', error);
      }
    };

    return {
      discussionForm,
      submitDiscussion,
    };
  },
};
</script>

<style scoped>
/* 样式 */
</style>