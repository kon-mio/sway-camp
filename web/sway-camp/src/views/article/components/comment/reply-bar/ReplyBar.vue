<template>
  <div class="reply-bar">
    <span v-if="!isReply" class="reply-content reply-bar">{{ info.content }}</span>
    <div class="reply-info">
      <span class="reply-time">{{ info.date }}</span>
      <span class="reply-like">
        <sway-icon name="like" :size="16" />
      </span>
      <span class="reply-btn" @click="open">回复</span>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Comment, Reply } from "@/api/comment/type"
import { computed } from "vue"

const props = withDefaults(
  defineProps<{
    baseInfo: Comment | Reply
    isReply?: boolean
  }>(),
  { isReply: false }
)
const emits = defineEmits<{
  (el: "openReply", reply: Comment | Reply): void
}>()
const info = computed(() => {
  return props.baseInfo
})
const open = () => {
  // 区分回复评论还是回复用户
  emits("openReply", info.value)
}
</script>

<style lang="less" scoped>
.reply-bar {
  position: relative;
  padding: 2px 0;
  font-size: 14px;
  line-height: 24px;
  .reply-content {
    color: #18191c;
    overflow: hidden;
    word-wrap: break-word;
    word-break: break-word;
    white-space: pre-wrap;
    line-height: 24px;
    vertical-align: baseline;
  }
}
.reply-info {
  display: flex;
  align-items: center;
  position: relative;
  margin-top: 2px;
  font-size: 13px;
  color: #9499a0;
  .reply-time {
    margin-right: 20px;
  }
  .reply-btn {
    cursor: pointer;
    &:hover {
      color: #40c2f1;
    }
  }
  .reply-like,
  .reply-dislike {
    display: flex;
    align-items: center;
    margin-right: 19px;
    cursor: pointer;
    i {
      &:hover {
        color: #40c2f1;
      }
    }
  }
}
</style>
