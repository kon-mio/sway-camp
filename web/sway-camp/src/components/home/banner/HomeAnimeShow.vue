<template>
  <!-- 视频遮罩 -->
  <div class="mark"></div>
  <!-- controls 工具栏  自动播放必须添加静音属性 -->
  <video muted autoplay loop>
    <source src="@/assets/video/anime.mp4" type="video/mp4" />
  </video>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({
  name: 'HomeAnimeShow'
})
</script>

<style lang="less" scoped>
video {
  height: 100%;
  width: 100%;
  object-fit: fill;
  z-index: -1;
}
.mark {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 2;
}
</style>
