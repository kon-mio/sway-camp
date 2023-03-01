<template>
  <div class="root-reply">
    <span class="reply-content root-reply">{{ info.content }}</span>
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
import { CommonInfo } from "@/api/comment/type"
import { isEmpty } from "@/utils/valid"
import { computed } from "vue"

const props = defineProps<{
  baseInfo: CommonInfo
}>()
const emits = defineEmits<{
  (el: "openReply", isReplyComment: boolean): void
}>()
const info = computed(() => {
  return props.baseInfo
})
const open = () => {
  // 区分回复评论还是回复用户
  console.log(info)
  emits("openReply", isEmpty(info.value.commentId) ? true : false)
}
</script>

<style lang="less" scoped>
.root-reply {
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

  .reply-operation-warp {
    position: absolute;
    right: 20px;
    display: none;
    cursor: pointer;
    .reply-operation {
      display: inline-flex;
      position: relative;
      ul {
        padding: 0;
        margin: 0;
        list-style: none;
      }
      .operation-list {
        display: flex;
        flex-direction: column;
        position: absolute;
        top: 20px;
        right: 0;
        z-index: 10;
        width: 120px;
        border-radius: 4px;
        font-size: 14px;
        color: #18191c;
        background-color: #ffffff;
        box-shadow: 0 0 5px rgb(0 0 0 / 20%);
        .operation-option {
          display: flex;
          align-items: center;
          height: 36px;
          padding: 0 15px;
          cursor: pointer;

          &:hover {
            background-color: #f1f2f3;
            color: #00aeec;
          }
        }
      }
    }
  }
}
</style>
