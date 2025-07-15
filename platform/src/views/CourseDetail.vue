<template>
  <el-card style="margin:20px">
    <h2>{{ course.title }}</h2>
    <p>{{ course.category }} · ￥{{ course.price }}</p>
    <p>{{ course.description }}</p>
    <el-button type="primary" @click="buy">购买课程</el-button>
    <el-button @click="toggleFav">{{ isFav ? '取消收藏' : '收藏课程' }}</el-button>
    <el-progress :percentage="70" />
<el-button type="text" @click="$router.push('/forum/new')">
  提问讨论
</el-button>

  </el-card>
</template>

<script>
import { useFavoritesStore } from '@/store/favorites'

export default {
  name: 'CourseDetail',
  data() {
    return { course: {}, isFav: false }
  },
  created() {
    const id = this.$route.params.id
    // TODO: 用 API 调用拉详情
    this.course = { id, title: '示例课程', category: '前端', price: 299, description: '课程简介...' }
    const favs = useFavoritesStore()
    this.isFav = favs.isFavorite(id)
  },
  methods: {
    buy() {
      this.$message.success('购买成功！')
      this.$router.push('/browse')
    },
    toggleFav() {
      const favs = useFavoritesStore()
      if (this.isFav) favs.remove(this.course.id)
      else          favs.add(this.course.id)
      this.isFav = !this.isFav
    }
  }
}
</script>
