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
        <reply-bar :base-info="comment" @open-reply="openReplyBox" />
      </div>
    </div>
    <!-- 回复列表 -->
    <div class="reply-container">
      <div class="reply-list">
        <ReplyItem
          v-for="(item, index) in comment.replies"
          :key="index"
          :reply="item"
          @open-reply-box="openReplyBox"
        />
      </div>
    </div>
    <!-- 回复框 -->
    <div class="reply-box-container">
      <reply-box
        v-if="comment.id === activeBoxId"
        :placeholder="placeholder"
        @submit="submitReply"
      />
    </div>
    <div class="bottom-line"></div>
  </div>
</template>

<script lang="ts" setup>
import { Comment, CommonInfo, Reply } from "@/api/comment/type"
import { computed, ref, reactive } from "vue"
import Avatar from "../avatar/Avatar.vue"
import ReplyBar from "../reply-bar/ReplyBar.vue"
import ReplyBox from "../reply-box/ReplyBox.vue"
import { ReplyDTO } from "@/api/comment/type"
import { uploadReplyApi } from "@/api/comment/api"
import { isEmpty } from "@/utils/valid"
import ReplyItem from "../reply-item/ReplyItem.vue"

const props = defineProps<{
  comment: Comment
  activeBoxId: number | null
}>()
const emits = defineEmits<{
  (el: "openReplyBox", commentId: number): void
}>()
const comment = computed(() => {
  return props.comment
})
const replyDTO = reactive<ReplyDTO>({
  commentId: null,
  content: "",
  replyId: null,
  replyUserId: null
})

// 回复评论
const replyComment = ref<CommonInfo | Reply | null>(null)
const placeholder = ref("发一条友善的评论")

// 打开回复框
const openReplyBox = (thisComment: CommonInfo) => {
  emits("openReplyBox", comment.value.id)
  replyComment.value = thisComment
  console.log(replyComment.value)
  placeholder.value = "回复  @" + replyComment.value.username
}
const submitReply = async (text: string) => {
  replyDTO.content = text
  replyDTO.commentId = comment.value.id
  if (!replyComment.value?.commentId) {
    replyDTO.replyId = null
    replyDTO.replyUserId = null
  } else {
    replyDTO.replyId = (replyComment.value as Reply).id
    replyDTO.replyUserId = (replyComment.value as Reply).userId
  }
  const res = await uploadReplyApi(replyDTO)
}
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
.reply-container {
  padding-left: calc(80px - 8px);
  .reply-list {
    position: relative;
  }
}
</style>
