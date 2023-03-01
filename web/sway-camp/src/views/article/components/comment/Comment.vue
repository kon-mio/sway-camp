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
        <ReplyBox ref="commentBox" @submit="submitComment" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, reactive, ref } from "vue"
import ReplyBox from "./reply-box/ReplyBox.vue"
import { CommentDTO } from "@/api/comment/type"
import { uploadCommentApi } from "@/api/comment/api"
import { HttpStatusCode } from "@/common/enum"
import { useGlobalStore } from "@/stores/global.sotre"

// 文章ID
const props = defineProps<{ articleId: number }>()
const articleId = computed(() => {
  return props.articleId
})
// 迷你通知
const globalStore = useGlobalStore()
const commentDTO = reactive<CommentDTO>({
  articleId: articleId.value,
  content: ""
})
const commentBox = ref<InstanceType<typeof ReplyBox> | null>(null)
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
</style>
