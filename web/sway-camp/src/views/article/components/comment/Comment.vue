<template>
  <div class="comment">
    <div class="comment-container">
      <!-- 头部导航 -->
      <div class="reply-header">
        <div class="reply-navigation">
          <ul class="nav-bar">
            <li class="nav-title">
              <span class="nav-title-text">评论</span>
              <!-- <span class="total-reply">{{ commentData.commentCount }}</span> -->
            </li>
          </ul>
        </div>
      </div>
      <!--  -->
      <div class="reply-warp">
        <ReplyBox />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup name="">
import ReplyBox from "./reply-box/ReplyBox.vue"
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
