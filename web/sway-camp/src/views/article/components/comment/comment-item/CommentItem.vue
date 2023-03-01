<template>
  <div class="reply-item">
    <!-- 根评论 -->
    <div class="comment-container">
      <div class="comment-avatar">
        <div class="avatar">
          <Avatar :height="34" :width="34" :avatar="comment.avatar" />
        </div>
      </div>
      <div class="content-warp">
        <div class="user-info">
          <div class="user-name">{{ comment.username }}</div>
        </div>
        <ReplyBar :base-info="comment" />
      </div>
    </div>

    <div class="bottom-line"></div>
  </div>
</template>

<script lang="ts" setup>
import { Comment } from "@/api/comment/type"
import Avatar from "../avatar/Avatar.vue"
import ReplyBar from "../reply-bar/ReplyBar.vue"

const props = defineProps<{
  comment: Comment
}>()
</script>

<style lang="less" scoped>
.reply-item {
  position: relative;
  .reply-box-container {
    padding: 25px 0 10px 80px;
  }
  .bottom-line {
    margin-left: 80px;
    border-bottom: 1px solid #e3e5e7;
    margin-top: 14px;
  }
  .view-more {
    padding-top: 8px;
    padding-left: 8px;
    font-size: 13px;
    color: #9499a0;
    // &-default {
    // }

    &-btn {
      cursor: pointer;
      &:hover {
        color: #00aeec;
      }
    }
    &-pagination {
      display: flex;
      flex-direction: row;
      align-items: center;
      color: #18191c;
      span {
        display: flex;
        flex-direction: row;
        // align-items: ;
        height: 20px;
        font-size: 13px;
        color: #808080;
      }
    }
  }
}

.comment-container {
  padding: 22px 0 0 80px;
  &:hover {
    .reply-operation-warp {
      display: block !important;
    }
  }
  .comment-avatar {
    display: flex;
    justify-content: center;
    position: absolute;
    left: 0;
    width: 80px;
    cursor: pointer;
  }
  .content-warp {
    flex: 1;
    position: relative;
    .reply-decorate {
      position: absolute;
      top: 0;
      right: 0;
      user-select: none;
      transform: translateY(-15px);
      .user-sailing {
        display: flex;
        flex-direction: row;
        align-items: center;
        &-img {
          vertical-align: middle;
          border-style: none;
          height: 48px;
        }
        &-text {
          position: absolute;
          right: 0;
          font-family: fanscard;
          font-size: 12px;
          color: #9d6aee;
          line-height: 16px;
          word-break: keep-all;
          transform: scale(0.7);
          transform-origin: center center;
        }
      }
    }
    .user-info {
      display: flex;
      align-items: center;
      margin-bottom: 4px;
      .user-name {
        font-family: PingFang SC, HarmonyOS_Medium, Helvetica Neue, Microsoft YaHei, sans-serif;
        font-size: 12px;
        font-weight: 500;
        margin-right: 5px;
        color: #fb7299;
        cursor: pointer;
      }
    }
    .comment {
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
  }
}
</style>
