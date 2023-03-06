<template>
  <div class="carousel-item">
    <div class="carousel-inner">
      <picture class="v-img">
        <img :src="src" />
      </picture>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed } from "vue"

const props = withDefaults(
  defineProps<{
    src?: string
  }>(),
  { src: "" }
)

const src = computed(() => {
  return props.src
})
</script>

<style lang="less" scoped>
.carousel-item {
  position: relative;
  display: block;
  width: 100%;
  height: 100%;
}
.carousel-inner {
  width: 100%;
  height: 100%;
}
.v-img {
  position: relative;
  display: inline-block;
  line-height: 1;
  width: 100%;
  height: 100%;
  vertical-align: middle;
  background-color: #f1f2f3;
  img {
    display: block;
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-style: none;
  }
}
</style>
