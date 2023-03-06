<template>
  <div class="carousel-slide" :style="itemWidth">
    <slot></slot>
  </div>
</template>

<script lang="ts">
import { computed, CSSProperties, defineComponent } from "vue"
export default defineComponent({
  name: "CarouselSlide",
  props: {
    width: {
      type: String,
      default: ""
    }
  },
  setup(props) {
    const itemWidth = computed(() => {
      return { width: props.width } as CSSProperties
    })
    return { itemWidth }
  }
})
</script>

<style lang="less" scoped>
.carousel-slide {
  float: left;
  transition: all 0.3s;
  z-index: 1;
  height: 100%;
}
</style>
