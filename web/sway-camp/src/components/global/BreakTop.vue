<template>
  <div class="break-top" :style="curPos">
    <div class="break-top__bg" @click="breakTop"></div>
  </div>
</template>
<script lang="ts">
import { computed, CSSProperties, defineComponent, onBeforeUnmount, onMounted, ref } from "vue"
import { throttle } from "@konmio/utils"
export default defineComponent({
  name: "BreakTop",
  props: {
    target: {
      type: String,
      default: ""
    }
  },
  setup(props) {
    const dom = ref<HTMLElement>()
    const visible = ref(false)

    const curPos = computed<CSSProperties>(() => ({
      transform: `translateY(${visible.value ? "0px" : "-86vh"})`
    }))
    const hasTarget = computed(() => props.target !== "")

    // 监听滚动事件
    const scroller = (e: Event) => {
      throttle(() => {
        const scTop = (e.target as HTMLElement).scrollTop
        visible.value = scTop > 300
      }, 100)()
    }
    const addEvent = () => {
      if (!hasTarget.value || dom.value === null) return
      dom.value?.addEventListener("scroll", scroller)
    }
    const removeEvent = () => {
      if (!hasTarget.value || dom.value === null) return
      dom.value?.removeEventListener("scroll", scroller)
    }
    // 点击事件
    const breakTop = () => {
      if (dom.value === null) return
      dom.value?.scrollTo({
        top: 0,
        left: 0,
        behavior: "smooth"
      })
    }

    const init = () => {
      if (!dom.value) return
      if (dom.value?.scrollTop > 300) return
      dom.value?.scrollTo({
        top: 0,
        left: 0,
        behavior: "smooth"
      })
    }

    onMounted(() => {
      if (!hasTarget.value) return
      dom.value = document.querySelector("#" + props.target) as HTMLElement
      addEvent()
      init()
    })
    onBeforeUnmount(() => {
      removeEvent()
    })

    return {
      breakTop,
      curPos
    }
  }
})
</script>
<style lang="less" scoped>
.break-top {
  position: fixed;
  top: 0;
  right: 30px;
  z-index: 999;
  transition: all 0.6s;
  &__bg {
    position: relative;
    width: 70px;
    height: 86vh;
    animation-name: break-top-anime;
    animation-duration: 2s;
    // 延时动画
    animation-delay: 0s;
    // 次数
    animation-iteration-count: infinite;
    // 运动曲线
    animation-timing-function: ease-in-out;
    background: url("data:image/png;base64,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")
      no-repeat bottom / cover;
    cursor: pointer;
  }
}
@keyframes break-top-anime {
  0% {
    transform: translateY(-12px);
  }
  50% {
    transform: translateY(0px);
  }
  100% {
    transform: translateY(-12px);
  }
}
</style>
