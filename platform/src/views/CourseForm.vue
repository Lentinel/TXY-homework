<template>
  <el-card style="margin:20px">
    <h2>{{ isEdit ? '编辑课程' : '新建课程' }}</h2>
    <el-form :model="form" ref="formRef" label-width="120px">
      <el-form-item label="课程名称">
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="form.category">
          <el-option label="前端" value="前端" />
          <el-option label="后端" value="后端" />
          <el-option label="数据库" value="数据库" />
        </el-select>
      </el-form-item>
      <el-form-item label="价格">
        <el-input-number v-model="form.price" :min="0" />
      </el-form-item>
      <el-form-item label="简介">
        <el-input type="textarea" v-model="form.description" rows="4" />
      </el-form-item>

      <el-form-item label="章节列表">
        <div v-for="(ch, i) in form.chapters" :key="i" style="margin-bottom:10px">
          <el-input v-model="ch.title" placeholder="章标题" style="width:40%;margin-right:10px" />
          <el-input v-model="ch.content" placeholder="章内容" style="width:50%" />
          <el-button type="text" @click="removeChapter(i)">删除</el-button>
        </div>
        <el-button plain @click="addChapter">添加章节</el-button>
      </el-form-item>

      <el-form-item label="课程资源">
        <ResourceUpload :courseId="123" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="$router.back()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import ResourceUpload from '@/components/ResourceUpload.vue'

export default {
  name: 'CourseForm',
  components: { ResourceUpload },
  data() {
    return {
      isEdit: !!this.$route.params.id,
      form: {
        title: '', category: '', price: 0,
        description: '', chapters: []
      }
    }
  },
  methods: {
    addChapter() { this.form.chapters.push({ title:'', content:'' }) },
    removeChapter(i) { this.form.chapters.splice(i,1) },
    submitForm() { this.$message.success('提交成功') }
  }
}
</script>