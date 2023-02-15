<template>
  <div class="header">
    <div class="title animate__animated animate__rubberBand">
      <h2>Sway Camp</h2>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
export default defineComponent({
  name: "AsideHeader"
})
</script>

<style lang="less" scoped>
.header {
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  padding: 20px;
  .logo {
    width: 100%;
    height: 100%;
    border-radius: 12px;
    overflow: hidden;
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }
  .title {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
    h2 {
      font-size: 24px;
      color: @h-blue-3;
    }
  }
}
</style>
