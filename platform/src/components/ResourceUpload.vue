<template>
  <div>
    <el-upload
      action="#"
      :show-file-list="false"
      :before-upload="() => true"
      @success="() => $message.success('上传成功')"
      @error="() => $message.error('上传失败')"
    >
      <el-button size="small">上传文件</el-button>
    </el-upload>
    <el-radio-group v-model="fileType" size="small" style="margin-left:10px">
      <el-radio-button label="video">视频</el-radio-button>
      <el-radio-button label="document">文档</el-radio-button>
    </el-radio-group>
  </div>
</template>

<script>
export default {
  name: 'ResourceUpload',
  props: { courseId: [String, Number] },
  data() {
    return { fileType: 'video' }
  }
}
</script>