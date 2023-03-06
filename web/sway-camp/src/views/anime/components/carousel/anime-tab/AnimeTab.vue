<template>
  <div class="anime-tab">
    <!-- 导航 -->
    <div class="anime-tab__nav">
      <div class="anime-tab__nav-item active">推荐</div>
    </div>
    <!-- 内容 -->
    <div class="anime-tab__container">
      <slot></slot>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
export default defineComponent({
  name: "AnimeTab",
  setup() {
    return {}
  }
})
</script>

<style lang="less" scoped>
.anime-tab {
  position: relative;
  // width: 100%;
  height: 100%;
  overflow: hidden;

  &__nav {
    display: flex;
    height: 40px;
    width: 100%;
    margin-bottom: 32px;

    &-item {
      display: flex;
      align-items: center;
      height: 100%;
      color: rgba(255, 255, 255, 0.7);
      margin-right: 40px;
      cursor: pointer;
      user-select: none;
      font-weight: 800;
    }

    .active {
      color: skyblue;
      border-bottom: 2px solid skyblue;
    }
  }

  &__items {
    width: 100%;
  }
}
</style>
