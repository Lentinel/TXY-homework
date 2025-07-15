<template>
  <div class="comment-item">
    <div class="comment-main">
      <p>
        <strong>{{ comment.authorName || '匿名用户' }}</strong>: {{ comment.content }}
      </p>
      <div class="comment-meta">
        <span class="comment-time">{{ formatTime(comment.createTime) }}</span>
        <el-button type="text" size="mini" @click="$emit('like-reply', comment.id)">
          点赞 ({{ comment.likeCount || 0 }})
        </el-button>
        </div>
    </div>
  </div>
</template>

<script>
import { format } from 'date-fns';

export default {
  name: 'CommentItem', 
  props: {
    comment: {
      type: Object,
      required: true
    },
  },
  setup(props, { emit }) {
    const formatTime = (timeString) => {
      if (!timeString) return '未知时间';
      try {
        return format(new Date(timeString), 'yyyy-MM-dd HH:mm');
      } catch (e) {
        return timeString;
      }
    };

    // 此处不再处理楼中楼回复，仅提供点赞功能
    const handleLike = () => {
      emit('like-reply', props.comment.id);
    };

    return {
      formatTime,
      handleLike
    };
  },
};
</script>



<style scoped>
.comment-item {
  border-left: 2px solid #ebeef5;
  padding-left: 10px;
  margin-bottom: 15px;
}
.comment-main {
  background-color: #f8f8f8;
  padding: 10px;
  border-radius: 4px;
}
.comment-time {
  font-size: 0.8em;
  color: #999;
  margin-right: 10px;
}
.reply-input-area {
  margin-top: 10px;
  margin-left: 20px; /* 缩进表示是回复 */
}
.reply-input {
  margin-bottom: 5px;
}
.replies-list {
  margin-top: 10px;
  margin-left: 20px; /* 嵌套回复进一步缩进 */
}
.nested-reply {
  margin-top: 10px;
}
</style>