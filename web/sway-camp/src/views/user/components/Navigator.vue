<template>
  <div class="navigator">
    <div class="wapper">
      <div class="nav-tab">
        <NavigatorItem
          v-for="(item, index) in navList"
          :key="index"
          :nav-item="item"
          :item-index="index"
          :active="index === activeIndex"
          @click="chooseItem"
          @get-width="getWidth"
          @mouse-enter="mouseEnter"
          @mouse-leave="mouseLeave"
        />
      </div>
      <div
        class="cursor"
        :style="{
          width: compWidth + 'px',
          left: compLeft + 'px',
          transition: `all ${cursorTransTime}s ease`
        }"
      ></div>
    </div>
  </div>
</template>

<script lang="ts" setup>
// 注意：因为每次切换子路由时每次都会初始化'提示线'的数据，
//      导致切换路由时每次'提示线'都会从头开始，故使用定时器只在需要时提供线条动画
import { ref, computed, onBeforeMount } from "vue"
import { useRoute } from "vue-router"
import NavigatorItem from "./NavigatorItem.vue"
import type { NavigatorItemType } from "../types/user-nav"
import { isEmpty } from "@/utils/data/valid"

const props = withDefaults(
  defineProps<{
    navList: NavigatorItemType[]
    transTime?: number
  }>(),
  { transTime: 0.25 }
)
const emits = defineEmits<{
  (e: "click", id: number): void
}>()

const $route = useRoute()
const activeIndex = ref<number>(0)
const mouseEnterId = ref<number | null>(null)

// 提示线动画
const cursorTransTime = ref(0)

const compLeft = computed(() => {
  return mouseEnterId.value != null
    ? getCrusorStyle(mouseEnterId.value!)
    : getCrusorStyle(activeIndex.value!)
})
const compWidth = computed(() => {
  return mouseEnterId.value != null
    ? getCrusorStyle(mouseEnterId.value!, true)
    : getCrusorStyle(activeIndex.value!, true)
})
// 根据路由获取当前页面对应ID
const getCurrentIndex = () => {
  let currentIndex = 0
  props.navList.find((item, index) => {
    if (item.name === $route.name) {
      currentIndex = index
    }
  })
  return currentIndex
}

// 获取'提示线'宽高
const getCrusorStyle = (acIndex: number, isWidth: boolean = false) => {
  if (props.navList.length === 0) {
    return null
  }
  if (acIndex === null || acIndex > props.navList.length || acIndex < 0) {
    acIndex = getCurrentIndex()
  }
  if (isWidth) {
    let width = props.navList.find((item, index) => {
      return index === acIndex
    })?.width!
    return width + 4
  } else {
    let count = 0
    props.navList.forEach((item, index) => {
      if (index < acIndex) {
        count += item.width! + 20
      }
    })
    return count
  }
}

// 使用动画
const useCursorAnime = () => {
  cursorTransTime.value = props.transTime
  setTimeout(() => {
    cursorTransTime.value = 0
  }, props.transTime * 100)
}
// 鼠标移入
const mouseEnter = (index: number) => {
  useCursorAnime()
  mouseEnterId.value = index
}
const mouseLeave = () => {
  useCursorAnime()
  mouseEnterId.value = null
}
// 获取每个导航宽度
const getWidth = (itemIndex: number, width: number) => {
  props.navList.forEach((item, index) => {
    if (index == itemIndex) {
      item.width = width
      return
    }
  })
}
// 选择导航
const chooseItem = (acIndex: number) => {
  activeIndex.value = acIndex
  emits("click", activeIndex.value)
}
onBeforeMount(() => {
  activeIndex.value = getCurrentIndex()
})
</script>

<style lang="less" scoped>
.navigator {
  position: relative;
  width: 100%;
  height: 66px;
  box-sizing: border-box;
  padding: 0 30px;
  background-color: white;
  .wapper {
    position: relative;
    margin-left: 50px;
    .nav-tab {
      display: flex;
      flex-direction: row;
    }
  }
  .cursor {
    position: absolute;
    left: 0;
    bottom: 0px;
    width: 0;
    height: 4px;
    border-radius: 4px;
    background: skyblue;
  }
}
</style>