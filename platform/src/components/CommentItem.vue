<template>
  <div class="comment-item">
    <div class="comment-main">
      <p><strong>{{ comment.authorName }}</strong>: {{ comment.content }}</p>
      <span class="comment-time">{{ comment.createTime }}</span>
      <el-button type="text" size="mini" @click="showReplyInput = !showReplyInput">回复</el-button>
    </div>
    <div v-if="showReplyInput" class="reply-input-area">
      <el-input
        type="textarea"
        :rows="2"
        placeholder="回复此评论..."
        v-model="replyContent"
        class="reply-input"
      ></el-input>
      <el-button type="primary" size="mini" @click="submitReply" :disabled="!replyContent.trim()">提交回复</el-button>
      <el-button size="mini" @click="showReplyInput = false">取消</el-button>
    </div>
    <div v-if="comment.replies && comment.replies.length" class="replies-list">
      <CommentItem
        v-for="reply in comment.replies"
        :key="reply.id"
        :comment="reply"
        @reply-comment="handleNestedReply"
        class="nested-reply"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: 'CommentItem',
  props: {
    comment: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      showReplyInput: false,
      replyContent: '',
    };
  },
  methods: {
    submitReply() {
      // 触发父组件的回复事件，将评论ID和回复内容传出去
      this.$emit('reply-comment', this.comment.id, this.replyContent);
      this.replyContent = '';
      this.showReplyInput = false;
    },
    handleNestedReply(commentId, replyContent) {
      // 如果是嵌套回复，继续向上触发，直到 PostDetail.vue 处理
      this.$emit('reply-comment', commentId, replyContent);
    }
  }
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