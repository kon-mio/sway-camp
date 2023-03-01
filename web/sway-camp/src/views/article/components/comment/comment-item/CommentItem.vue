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
      <div v-if="comment.replyCount > 2" class="view-more">
        <div v-if="!viewMore" class="view-more-default">
          <span>共 {{ comment.replyCount }} 条回复, </span>
          <span class="view-more-btn" @click="viewMoreReply">点击查看</span>
        </div>
        <div
          v-if="viewMore && Math.ceil(comment.replyCount / replyPage.size) > 1"
          class="view-more-pagination"
        >
          <span>共 {{ Math.ceil(comment.replyCount / replyPage.size) }} 页</span>
          <el-pagination
            v-model:current-page="replyPage.index"
            small
            hide-on-single-page
            prev-text="上一页"
            next-text="下一页"
            layout="prev, pager, next"
            :pager-count="5"
            :page-count="Math.ceil(comment.replyCount / replyPage.size)"
            @next-click="nextPage"
            @prev-click="prevPage"
            @current-change="currentChange"
          />
        </div>
      </div>
    </div>
    <!-- 回复框 -->
    <div v-if="comment.id === activeBoxId" class="reply-box-container">
      <reply-box ref="replyBox" :placeholder="placeholder" @submit="submitReply" />
    </div>
    <div class="bottom-line"></div>
  </div>
</template>

<script lang="ts" setup>
import { Comment, Reply } from "@/api/comment/type"
import { computed, ref, reactive } from "vue"
import Avatar from "../avatar/Avatar.vue"
import ReplyBar from "../reply-bar/ReplyBar.vue"
import ReplyBox from "../reply-box/ReplyBox.vue"
import { ReplyDTO } from "@/api/comment/type"
import { listReplyApi, uploadReplyApi } from "@/api/comment/api"
import ReplyItem from "../reply-item/ReplyItem.vue"
import { useGlobalStore } from "@/stores/global.sotre"
import { HttpStatusCode } from "@/common/enum"

const props = defineProps<{
  comment: Comment
  activeBoxId: number | null
}>()
const emits = defineEmits<{
  (el: "openReplyBox", commentId: number): void
  (el: "uploadReply", commentId: number, reply: Reply): void
  (el: "updateReply", commentId: number, replies: Reply[]): void
}>()

const globalStore = useGlobalStore()
const replyBox = ref<InstanceType<typeof ReplyBox> | null>(null)
const viewMore = ref(false)
const replyComment = ref<Comment | Reply | null>(null)
const placeholder = ref("发一条友善的评论")
const replyDTO = reactive<ReplyDTO>({
  commentId: null,
  content: "",
  replyId: null,
  replyUserId: null
})
const replyPage = reactive<{
  index: number
  size: number
}>({
  index: 1,
  size: 5
})
const comment = computed(() => {
  return props.comment
})

const listReply = async () => {
  const res = await listReplyApi(replyPage.index, replyPage.size, comment.value.id)
  if (res.code === HttpStatusCode.Success) {
    emits("updateReply", comment.value.id, res.data.list)
  }
}
// 查看更多回复
const viewMoreReply = () => {
  listReply()
  viewMore.value = true
}
const prevPage = () => {
  listReply()
}
const nextPage = () => {
  listReply()
}
const currentChange = () => {
  listReply()
}

// 打开回复框
const openReplyBox = (thisComment: Comment | Reply) => {
  emits("openReplyBox", comment.value.id)
  replyComment.value = thisComment
  placeholder.value = "回复  @" + replyComment.value.username
}
const submitReply = async (text: string) => {
  replyDTO.content = text
  replyDTO.commentId = comment.value.id
  if (!(replyComment.value as Reply).commentId) {
    replyDTO.replyId = null
    replyDTO.replyUserId = null
  } else {
    replyDTO.replyId = (replyComment.value as Reply).id
    replyDTO.replyUserId = (replyComment.value as Reply).userId
  }
  const res = await uploadReplyApi(replyDTO)
  if (res.code === HttpStatusCode.Success) {
    globalStore.openMessageMini("回复成功")
    replyBox.value?.boxInit()
    emits("uploadReply", comment.value.id, res.data)
  } else {
    globalStore.openMessageMini("回复失败")
  }
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
