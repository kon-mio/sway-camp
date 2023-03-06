<template>
  <div class="container-slide">
    <div calss="container-slide-items">
      <div :style="slideStyle">
        <slot></slot>
      </div>
    </div>
    <!-- 左右按钮 -->
    <div class="container-slide__arrow container-slide__arrow--left" @click="arrowClick(false)">
      <sway-icon name="arrow" :size="24" />
    </div>
    <div class="container-slide__arrow container-slide__arrow--right" @click="arrowClick(true)">
      <sway-icon name="arrow" :size="24" />
    </div>
  </div>
</template>

<script lang="ts">
import { repeatThrottle } from "@konmio/utils"
import { computed, CSSProperties, defineComponent } from "vue"
export default defineComponent({
  name: "AnimeTabContainer",
  props: {
    time: {
      type: Number,
      default: 500
    }
  },
  emits: ["arrowClick"],
  setup(props, ctx) {
    // 切换按钮
    const arrowClick = repeatThrottle((isNext = true) => {
      ctx.emit("arrowClick", isNext)
    }, props.time + 200)

    const slideStyle = computed<CSSProperties>(() => {
      return {
        display: "flex",
        transform: `translateX(-340px)`
      }
    })
    return {
      arrowClick,
      slideStyle
    }
  }
})
</script>

<style lang="less" scoped>
.container-slide {
  position: relative;

  .container-slide__arrow {
    position: absolute;
    top: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 60px;
    height: 100%;
    cursor: pointer;
    user-select: none;
    animation-duration: 0.25s;
  }

  .container-slide__arrow--left {
    left: 0;
    opacity: 0;
    z-index: -1;
    transition: all 0.25s ease-in-out;

    i {
      transform: rotateY(180deg);
    }
  }

  .container-slide__arrow--right {
    right: 0;
    opacity: 0;
    z-index: -1;
    transition: all 0.25s ease-in-out;
  }

  &:hover {
    .container-slide__arrow--left,
    .container-slide__arrow--right {
      opacity: 1;
      z-index: 3;

      i {
        font-size: 30px;
        color: white;
      }
    }
  }
}
</style>
