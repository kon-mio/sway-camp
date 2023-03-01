<template>
  <div class="sub-reply-item">
    <div class="sub-user-info">
      <div class="sub-reply-avatar">
        <Avatar :height="24" :width="24" :avatar="reply.avatar" />
      </div>
      <div class="sub-user-name">{{ reply.username }}</div>
    </div>
    <span v-if="reply.replyUserId === null" class="reply-content sub-reply-content">
      {{ reply.content }}
    </span>
    <span v-else class="reply-content sub-reply-content">
      回复
      <a class="jump-link user">@{{ reply.replyUsername }}</a>
      {{ reply.content }}
    </span>
    <reply-bar :base-info="reply" :is-reply="true" @open-reply="openBox" />
  </div>
</template>

<script lang="ts" setup>
import { CommonInfo, Reply } from "@/api/comment/type"
import { computed } from "vue"
import Avatar from "../avatar/Avatar.vue"
import ReplyBar from "../reply-bar/ReplyBar.vue"

const props = defineProps<{
  reply: Reply
}>()
const reply = computed(() => {
  return props.reply
})
const emits = defineEmits<{
  (el: "openReplyBox", reply: Reply): void
}>()

const openBox = () => {
  emits("openReplyBox", reply.value)
}
</script>

<style lang="less" scoped>
::v-deep(.operation-list) {
  top: 14px !important;
}
.sub-reply-item {
  position: relative;
  padding: 8px 0 8px 42px;
  border-radius: 4px;

  &:hover {
    .reply-operation-warp {
      display: block !important;
    }
  }
  .sub-user-info {
    display: inline-flex;
    align-items: center;
    margin-right: 9px;
    line-height: 24px;
    vertical-align: baseline;
    white-space: nowrap;
    font-size: 13px;
    .sub-reply-avatar {
      position: absolute;
      left: 8px;
      cursor: pointer;
    }
    .sub-user-name {
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
