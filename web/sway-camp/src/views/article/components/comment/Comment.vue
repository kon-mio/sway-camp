<template>
  <div class="comment">
    <div class="comment-container">
      <!-- 头部导航 -->
      <div class="reply-header">
        <div class="reply-navigation">
          <ul class="nav-bar">
            <li class="nav-title">
              <span class="nav-title-text">评论</span>
            </li>
          </ul>
        </div>
      </div>
      <!--  -->
      <div class="reply-warp">
        <!-- 评论 -->
        <reply-box ref="commentBox" @submit="submitComment" />
        <div class="comment-list">
          <comment-item
            v-for="(item, index) in commentList.list"
            :key="index"
            :comment="item"
            :active-box-id="replyBoxId"
            @open-reply-box="openReplyBox"
            @upload-reply="uploadReply"
            @update-reply="updateReply"
          />
          <div v-if="Math.ceil(commentList.total / commentPage.size) > 1" class="comment-page">
            <span>共 {{ Math.ceil(commentList.total / commentPage.size) }} 页</span>
            <el-pagination
              v-model:current-page="commentPage.index"
              small
              hide-on-single-page
              prev-text="上一页"
              next-text="下一页"
              layout="prev, pager, next"
              :pager-count="5"
              :page-count="Math.ceil(commentList.total / commentPage.size)"
              @next-click="listComment"
              @prev-click="listComment"
              @current-change="listComment"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, reactive, ref, onMounted } from "vue"
import { listCommentApi, uploadCommentApi } from "@/api/comment/api"
import type { CommentDTO, CommentPage, Reply } from "@/api/comment/type"
import { HttpStatusCode } from "@/common/enum"
import { useGlobalStore } from "@/stores/global.sotre"
import ReplyBox from "./reply-box/ReplyBox.vue"
import CommentItem from "./comment-item/CommentItem.vue"

// 文章ID
const props = defineProps<{ articleId: number }>()
const articleId = computed(() => {
  return props.articleId
})
// 迷你通知
const globalStore = useGlobalStore()

// 回复框活动Id
const replyBoxId = ref<number | null>(null)
// 回复框实例
const commentBox = ref<InstanceType<typeof ReplyBox> | null>(null)

// 评论分页信息
const commentPage = reactive<{
  index: number
  size: number
}>({
  index: 1,
  size: 5
})
// 上传评论信息
const commentDTO = reactive<CommentDTO>({
  articleId: articleId.value,
  content: ""
})
const commentList = reactive<CommentPage>({
  list: [],
  total: 0
})

// 打开回复框
const openReplyBox = (commentId: number) => {
  replyBoxId.value = commentId
}
// 查询评论里列表
const listComment = async () => {
  const res = await listCommentApi(commentPage.index, commentPage.size, articleId.value)
  if (res.code === HttpStatusCode.Success) {
    Object.assign(commentList, res.data)
  }
}
// 提交评论
const submitComment = async (text: string) => {
  commentDTO.content = text
  const res = await uploadCommentApi(commentDTO)
  if (res.code === HttpStatusCode.Success) {
    globalStore.openMessageMini("发送成功")
    commentBox.value?.boxInit()
    listComment()
  } else {
    globalStore.openMessageMini("发送失败")
  }
}
// 上传回复回调
const uploadReply = (id: number, reply: Reply) => {
  commentList.list.forEach((item) => {
    if (item.id === id) {
      item.replies.push(reply)
    }
  })
}
// 更新回复回调
const updateReply = (id: number, replies: Reply[]) => {
  commentList.list.forEach((item) => {
    if (item.id === id) {
      item.replies = replies
    }
  })
}
onMounted(() => {
  listComment()
})
</script>

<style lang="less" scoped>
.reply-header {
  margin-bottom: 22px;
  .nav-bar {
    display: flex;
    align-items: center;
    list-style: none;
    margin: 0;
    padding: 0;
    .nav-title {
      display: flex;
      align-items: center;
      font-size: 20px;

      &-text {
        color: black;
        font-weight: 600;
      }
      .total-reply {
        font-size: 13px;
        margin: 0 36px 0 6px;
        font-weight: normal;
        color: #9499a0;
      }
    }
  }
}
.reply-warp {
  position: relative;
  .comment-list {
    margin-top: 24px;
    padding-bottom: 100px;
    .comment-page {
      display: flex;
      flex-direction: row;
      align-items: center;
      box-sizing: border-box;
      padding-left: 20px;
      padding-top: 20px;
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
</style>
