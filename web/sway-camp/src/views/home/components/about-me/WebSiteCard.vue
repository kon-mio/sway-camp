<template>
  <div class="web-card">
    <time-card card-bg="http://file.takagi-san.cn/image/kon_all_3.webp" />
    <!-- 标题 -->
    <p class="web-card-title">sway-camp</p>
    <!-- 内容 -->
    <div class="web-card-introduce">
      <!-- 动漫、编程，这是博主的标签。本站推荐博主喜欢的动漫、自己所总结的知识，也会分享一些日常心得~ -->
      大家好,我是练习时长两年半的个人练习生,喜欢唱,跳,rap,篮球,music……在今后的节目中，
      我还准备了很多我自己的原创作品，期待的话，请多多为我投票吧。
    </div>
    <!-- 信息 -->
    <div class="web-card-info">
      <div>
        <p>文章</p>
        <span>0</span>
      </div>
      <div>
        <p>动漫</p>
        <span>0</span>
      </div>
      <div>
        <p>评论</p>
        <span>0</span>
      </div>
      <div>
        <p>浏览</p>
        <span>0</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import TimeCard from "@/components/time-card/TimeCard.vue"
</script>

<style lang="less" scoped>
.web-card {
  position: relative;
  width: 100%;
  min-height: 280px;
  overflow: hidden;
  border-radius: 12px;
  background-color: #fff;
  box-shadow: 1px 0 6px rgb(0 0 0 / 4%);

  &-title {
    padding: 8px;
    margin: 4px;
    font-size: 16px;
    font-weight: 600;
    color: #86b3ff;
    box-sizing: border-box;
  }

  &-introduce {
    text-align: justify;
    font-size: 12px;
    line-height: 18px;
    padding: 0 16px 10px 16px;
  }

  &-info {
    display: flex;
    width: 100%;

    div {
      position: relative;
      box-sizing: border-box;
      display: flex;
      flex-direction: column;
      justify-content: space-evenly;
      align-items: center;
      font-size: 12px;
      // 自适应填充满区域
      flex: 1;
      border-top: 1px solid #eee;
      height: 56px;
      transition: all 0.4s ease;
    }

    div:nth-child(1)::before,
    div:nth-child(2)::before,
    div:nth-child(3)::before {
      content: "";
      position: absolute;
      right: 0;
      display: block;
      width: 1px;
      height: 70%;
      background: #eee;
    }
  }
}
</style>
