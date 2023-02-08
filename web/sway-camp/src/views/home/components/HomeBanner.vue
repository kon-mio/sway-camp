<template>
  <div class="sway-home__banner--left">
    <slot name="Carosel"> </slot>
  </div>
  <div class="sway-home__banner--right">
    <div class="view-img">
      <slot name="Img"> </slot>
    </div>
    <div class="view-img">
      <slot name="Anime"> </slot>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
export default defineComponent({
  name: "HomeBanner"
})
</script>

<style lang="less" scoped>
.border-class {
  box-sizing: border-box;
  border: 3px @line-0 solid;
  box-shadow: 0 1px 3px @shadow-1;
  border-radius: 12px;
  overflow: hidden;
}
.sway-home__banner {
  &--left {
    width: 100%;
    height: 100%;
    .border-class;
  }
  &--right {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 5fr 3fr;
    row-gap: 20px;
    width: 100%;
    height: 100%;
    overflow: hidden;

    .view-img,
    .view-img {
      position: relative;
      .border-class;
    }
  }
}
</style>
