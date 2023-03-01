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
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, reactive, ref, onMounted } from "vue"
import ReplyBox from "./reply-box/ReplyBox.vue"
import { CommentDTO, CommentPage } from "@/api/comment/type"
import { listCommentApi, uploadCommentApi } from "@/api/comment/api"
import { HttpStatusCode } from "@/common/enum"
import { useGlobalStore } from "@/stores/global.sotre"
import CommentItem from "./comment-item/CommentItem.vue"

// 文章ID
const props = defineProps<{ articleId: number }>()
const articleId = computed(() => {
  return props.articleId
})
// 回复框活动Id
const replyBoxId = ref<number | null>(null)

// 迷你通知
const globalStore = useGlobalStore()

// 评论分页信息
const commentPage = reactive<{
  index: number
  size: number
}>({
  index: 1,
  size: 5
})

const commentList = reactive<CommentPage>({
  list: [],
  total: 0
})
// 上传评论信息
const commentDTO = reactive<CommentDTO>({
  articleId: articleId.value,
  content: ""
})
// 回复框实例
const commentBox = ref<InstanceType<typeof ReplyBox> | null>(null)

// 打开回复框
const openReplyBox = (commentId: number) => {
  replyBoxId.value = commentId
}

// 提交评论
const submitComment = async (text: string) => {
  commentDTO.content = text
  const res = await uploadCommentApi(commentDTO)
  if (res.code === HttpStatusCode.Success) {
    globalStore.openMessageMini("发送成功")
    commentBox.value?.boxInit()
  } else {
    globalStore.openMessageMini("发送失败")
  }
}
// 查询评论里列表
const listComment = async () => {
  const res = await listCommentApi(commentPage.index, commentPage.size)
  if (res.code === HttpStatusCode.Success) {
    Object.assign(commentList, res.data)
  }
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
    // .comment-page {
    //   display: flex;
    //   flex-direction: row;
    //   align-items: center;
    //   box-sizing: border-box;
    //   padding-left: 20px;
    //   padding-top: 20px;
    //   span {
    //     display: flex;
    //     flex-direction: row;
    //     // align-items: ;
    //     height: 20px;
    //     font-size: 13px;
    //     color: #808080;
    //   }
    // }
  }
}
</style>
