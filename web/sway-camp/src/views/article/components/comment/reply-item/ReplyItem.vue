<template>
  <div class="reply-item" @mouseenter="showOption" @mouseleave="closeOption">
    <div class="user-info">
      <div class="reply-avatar">
        <Avatar :height="24" :width="24" :avatar="reply.avatar" />
      </div>
      <div class="user-name">{{ reply.username }}</div>
    </div>
    <span v-if="reply.replyUserId === null" class="reply-content reply-content">
      {{ reply.content }}
    </span>
    <span v-else class="reply-content reply-content">
      回复
      <a class="jump-link user">@{{ reply.replyUsername }}</a>
      {{ reply.content }}
    </span>
    <reply-bar ref="replyBar" :base-info="reply" :is-reply="true" @open-reply="openBox" />
  </div>
</template>

<script lang="ts" setup>
import { Reply } from "@/api/comment/type"
import { computed, ref } from "vue"
import Avatar from "../avatar/Avatar.vue"
import ReplyBar from "../reply-bar/ReplyBar.vue"

const props = defineProps<{
  reply: Reply
}>()
const emits = defineEmits<{
  (el: "openReplyBox", reply: Reply): void
}>()

const replyBar = ref<InstanceType<typeof ReplyBar> | null>(null)

const reply = computed(() => {
  return props.reply
})

// 打开回复框
const openBox = () => {
  emits("openReplyBox", reply.value)
}
// 显示操作
const showOption = () => {
  replyBar.value?.openOperation()
}
const closeOption = () => {
  replyBar.value?.closeOperation()
}
</script>

<style lang="less" scoped>
::v-deep(.operation-list) {
  top: 14px !important;
}
.reply-item {
  position: relative;
  padding: 8px 0 8px 42px;
  border-radius: 4px;

  &:hover {
    .reply-operation-warp {
      display: block !important;
    }
  }
  .user-info {
    display: inline-flex;
    align-items: center;
    margin-right: 9px;
    line-height: 24px;
    vertical-align: baseline;
    white-space: nowrap;
    font-size: 13px;
    .reply-avatar {
      position: absolute;
      left: 8px;
      cursor: pointer;
    }
    .user-name {
      font-weight: 500;
      margin-right: 5px;
      color: #fb7299;
      cursor: pointer;
    }
  }
  .reply-content {
    text-align: justify;
    color: #18191c;
    overflow: hidden;
    word-wrap: break-word;
    word-break: break-word;
    white-space: pre-wrap;
    line-height: 24px;
    vertical-align: baseline;
    font-size: 14px;
    a {
      text-decoration: none;
      background-color: transparent;
      color: #008ac5;
      cursor: pointer;
      &:hover {
        color: #3ebee8;
      }
    }
    .jump-link {
      line-height: 24px;
      vertical-align: baseline;
    }
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
