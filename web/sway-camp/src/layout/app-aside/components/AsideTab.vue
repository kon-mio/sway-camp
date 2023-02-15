<template>
  <div class="aside-tab">
    <slot></slot>
    <div class="aside-tab__line" :style="lineStyle">
      <div class="line"></div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, CSSProperties, defineComponent } from "vue"
import { useRoute } from "vue-router"
export default defineComponent({
  name: "AsideTab",
  setup() {
    const route = useRoute()
    const lineStyle = computed(() => {
      return {
        transform: `translateY(
        ${(route.meta.index as number) * 80}px)`
      } as CSSProperties
    })
    return {
      lineStyle
    }
  }
})
</script>

<style lang="less" scoped>
.aside-tab {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  width: 100%;
  height: 100%;

  &__line {
    position: absolute;
    right: 0px;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 5px;
    height: 80px;
    transition: all 0.25s ease-in-out;

    .line {
      width: 5px;
      height: 40px;
      background-color: @line-gray-3;
      border-radius: 6px;
    }
  }
}
</style>
