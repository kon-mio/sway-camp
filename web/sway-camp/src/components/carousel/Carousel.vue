<template>
  <div class="carousel">
    <div class="carousel-container">
      <div
        class="carousel-transform"
        :style="carouselStyle"
        @mouseenter="stopInterval"
        @mouseleave="startInterval"
      >
        <CarouselSlide
          v-for="(item, index) in carouselItems"
          :key="index"
          :width="100 / carouselItems.length + '%'"
        >
          <CarouselItem :src="item as string" />
        </CarouselSlide>
      </div>

      <div v-if="tool" class="carousel-footer">
        <div class="carousel-mask"></div>
        <div class="carousel-tool not-gray">
          <ul class="not-gray">
            <li
              v-for="index in carouselItems.length"
              :key="index"
              :class="{ 'li-active': currentIndex === index - 1 }"
              @click="changeCurrentIndex(index - 1)"
            ></li>
          </ul>
        </div>
        <div class="carousel-buttons">
          <div>
            <button class="carousel-arrow--left" @click="arrowClick(false)">
              <sway-icon name="jiantouyou" color="white" />
            </button>
            <button class="carousel-arrow--right" @click="arrowClick(true)">
              <sway-icon name="jiantouyou" color="white" />
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, CSSProperties, onMounted, reactive, ref } from "vue"
import CarouselSlide from "./carousel-slide/CarouselSlide.vue"
import CarouselItem from "./carousel-item/CarouselItem.vue"
import { wait } from "@/utils/common"
import { repeatThrottle } from "@konmio/utils"

const props = withDefaults(
  defineProps<{
    carouselItems: unknown[]
    auto?: boolean
    tool?: boolean
    interval?: number
    time?: number
    sequence?: boolean
  }>(),
  { auto: true, tool: true, interval: 3000, time: 500, sequence: true }
)

const emits = defineEmits<{
  (el: "carouselNext", index: number): void
}>()

// 轮播图数据
const carItems = computed(() => {
  return props.carouselItems
})

const isAuto = ref(true)
const timer = ref<null | number>(null)
const currentIndex = ref(0)
const itemWidth = ref(0)
const translateX = ref(0)
const transitionTime = ref(500)
const carouselItems = reactive<unknown[]>([])

const carouselStyle = computed(() => {
  return {
    transform: `translateX(${translateX.value}%)`,
    transition: `all ${transitionTime.value}ms`,
    width: `${carouselItems.length * 100}%`
  } as CSSProperties
})
//打开定时器
const startInterval = () => {
  if (!timer.value) {
    timer.value = setInterval(() => {
      changeCurrentItem()
      emits("carouselNext", currentIndex.value)
    }, props.interval)
  }
}
//关闭定时器
const stopInterval = () => {
  if (timer.value) {
    clearInterval(timer.value)
    timer.value = null
  }
}

// 点击切换按钮
const changeCurrentItem = async (isNext = true) => {
  if (isNext) {
    currentIndex.value = currentIndex.value + 1 >= carouselItems.length ? 0 : currentIndex.value + 1
  } else {
    currentIndex.value =
      currentIndex.value - 1 < 0 ? carouselItems.length - 1 : currentIndex.value - 1
  }
  transitionTime.value = props.time
  translateX.value = isNext ? -itemWidth.value * 2 : 0
  // ------ 待加入过度动画完成回调
  await wait(transitionTime.value)
  transitionTime.value = 0
  translateX.value = -itemWidth.value

  if (isNext) {
    //将第一张房到最后
    Object.assign(carouselItems, handelSequence(carouselItems, 1))
  } else {
    translateX.value = -itemWidth.value
    //将最后一张放最前面
    Object.assign(carouselItems, handelSequence(carouselItems, carouselItems.length - 1))
  }
}
const arrowClick = (isNext: boolean) => {
  repeatThrottle(() => {
    manualSwitching(() => {
      changeCurrentItem(isNext)
    })
  }, transitionTime.value)()
}
//手动切换
const manualSwitching = (callback = () => {}) => {
  stopInterval()
  callback()
  //判断是否需要恢复计时
  if (!isAuto.value) return
  startInterval()
}
//根据index切换图片
const changeCurrentIndex = (i: number) => {
  if (i === currentIndex.value) return
  manualSwitching(() => {
    if (i === 0) {
      // 将最后一张图挪到最前面
      Object.assign(carouselItems, handelSequence(carItems.value, carItems.value.length - 1))
    } else {
      // 当前图片前一张在最前面，其他图片依次排列
      Object.assign(carouselItems, handelSequence(carItems.value, i - 1))
    }
    currentIndex.value = i
  })
}
//处理图片顺序
const handelSequence = (data: unknown[], start: number) => {
  const before = data.slice(0, start)
  return [...data.slice(start, data.length), ...before]
}

// 轮播图初始化
const carouselInit = () => {
  Object.assign(carouselItems, carItems.value)
  itemWidth.value = 100 / carouselItems.length
  translateX.value = -itemWidth.value
  if (props.sequence) {
    Object.assign(carouselItems, handelSequence(carouselItems, carouselItems.length - 1))
  }
  if (isAuto.value) {
    startInterval()
  }
  transitionTime.value = props.time
}

onMounted(() => {
  carouselInit()
})

defineExpose({
  arrowClick,
  startInterval,
  stopInterval
})
</script>

<style lang="less" scoped>
.carousel {
  height: 100%;
  user-select: none;

  &-container {
    position: relative;
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
  &-transform {
    height: 100%;
  }
  &-mask {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    cursor: pointer;
    z-index: -1;
  }
  &-tool {
    position: absolute;
    left: 20px;
    bottom: 10px;
    flex-grow: 1;
    z-index: 2;
    transition: filter 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
    ul,
    li {
      list-style-type: none;
      padding: 0;
      margin: 0;
    }
    li {
      display: inline-block;
      height: 4px;
      width: 20px;
      margin: 0 3px;
      border-radius: 2px;
      background-color: rgba(255, 255, 255, 0.3);
      cursor: pointer;
      transition: width 0.5s;
    }
    .li-active {
      width: 36px;
      background-color: #fff;
    }
  }
  &-buttons {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    position: absolute;
    bottom: 20px;
    right: 15px;
    z-index: 2;
    button {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      width: 28px;
      height: 28px;
      border-width: 0;
      border-radius: 8px;
      margin-right: 12px;
      background-color: rgba(255, 255, 255, 0.1);
      cursor: pointer;
    }
  }
  &-arrow--left {
    i {
      transform: rotate(180deg);
    }
  }
}
</style>
