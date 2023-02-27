import { reactive, ref } from "vue"
import { throttle } from "@konmio/utils"

/**
 * 滚动处理
 * @param scrollCustom 自定义终止开启触发事件 可选
 * @param keepTriggering 事件一直触发
 * @returns
 */
export const useScroll = () => {
  let scrollTrigger = true
  const open = () => {
    scrollTrigger = true
  }
  const close = () => {
    scrollTrigger = false
  }
  // 实例
  const scrollTarget = ref<HTMLDivElement | null>(null)
  // 滚动数据
  const scrollData = reactive({
    scrollTop: 0,
    clientHeight: 0,
    scrollHeight: 0
  })
  // 节流滚动回调
  const scroll = (callback = () => {}, delay = 300) => {
    throttle(() => {
      if (!scrollTarget.value) return
      const scrollTop: number = scrollTarget.value.scrollTop as number
      const clientHeight: number = scrollTarget.value.clientHeight as number
      const scrollHeight: number = scrollTarget.value.scrollHeight as number
      scrollData.scrollTop = scrollTop
      scrollData.clientHeight = clientHeight
      scrollData.scrollHeight = scrollHeight
      if (!scrollTrigger) return
      callback()
    }, delay)()
  }
  // 普通滚动回调
  const commonScroll = () => {
    const scrollTop: number = scrollTarget.value?.scrollTop as number
    const clientHeight: number = scrollTarget.value?.clientHeight as number
    const scrollHeight: number = scrollTarget.value?.scrollHeight as number
    scrollData.scrollTop = scrollTop
    scrollData.clientHeight = clientHeight
    scrollData.scrollHeight = scrollHeight
  }

  return {
    scrollData,
    scrollTarget,
    scroll,
    commonScroll,
    open,
    close
  }
}
