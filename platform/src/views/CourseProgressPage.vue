<template>
  <div class="course-progress-page">
    <h2>课程学习进度</h2>
    <div class="progress-summary">
      <el-progress 
        type="circle" 
        :percentage="completionRate"
        :color="colors"
      ></el-progress>
      <p>课程完成率</p>
    </div>
    
    <h3>章节进度</h3>
    <el-table :data="chapterProgress" style="width: 100%">
      <el-table-column prop="chapterName" label="章节名称"></el-table-column>
      <el-table-column prop="progress" label="学习进度">
        <template #default="{ row }">
          <el-progress :percentage="row.progress * 100" :text-inside="true" :stroke-width="24"></el-progress>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态"></el-table-column>
      <el-table-column prop="studyDuration" label="学习时长 (分钟)"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

export default {
  setup() {
    const chapterProgress = ref([]);
    const completionRate = ref(0);
    const route = useRoute();
    const { courseId } = route.params;
    const userId = ref(1); // 假设当前用户ID为1

    const fetchProgress = async () => {
      try {
        // 获取课程所有章节进度 API: GET /api/progress/courses/{courseId}
        const progressRes = await axios.get(`/api/progress/courses/${courseId}`, { params: { userId: userId.value } });
        if (progressRes.data.code === 200) {
          chapterProgress.value = progressRes.data.data;
        }

        // 获取课程完成率 API: GET /api/progress/courses/{courseId}/completion
        const completionRes = await axios.get(`/api/progress/courses/${courseId}/completion`, { params: { userId: userId.value } });
        if (completionRes.data.code === 200) {
          completionRate.value = Math.floor(completionRes.data.data * 100);
        }
      } catch (error) {
        console.error('获取学习进度失败:', error);
      }
    };
    
    const colors = [
      { color: '#f56c6c', percentage: 20 },
      { color: '#e6a23c', percentage: 40 },
      { color: '#5cb87a', percentage: 60 },
      { color: '#1989fa', percentage: 80 },
      { color: '#6f7ad3', percentage: 100 }
    ];

    onMounted(() => {
      fetchProgress();
    });

    return {
      chapterProgress,
      completionRate,
      colors,
    };
  },
};
</script>