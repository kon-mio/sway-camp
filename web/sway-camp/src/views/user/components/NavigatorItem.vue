<template>
  <div
    class="nav-item"
    ref="navItemRef"
    @click="click"
    @mouseenter="mouseEnter"
    @mouseleave="mouseLeave"
  >
    <span class="icon">
      <SwayIcon :name="navItem.icon" :color="navItem.color" :size="navItem.size" />
    </span>
    <span class="text" :class="{ 'item-active': active }">{{ navItem.title }}</span>
    <span class="num" v-if="navItem.num">{{ navItem.num }}</span>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import type { NavigatorItemType } from "../types/user-nav"

const props = withDefaults(
  defineProps<{
    navItem: NavigatorItemType
    itemIndex: number
    active?: boolean
  }>(),
  { active: false }
)
const emits = defineEmits<{
  (e: "getWidth", itemId: number, width: number): void
  (e: "mouseEnter", itemId: number): void
  (e: "mouseLeave"): void
  (e: "click", itemId: number): void
}>()

const isEnter = ref(false)
// item实例
const navItemRef = ref<HTMLDivElement | null>(null)

// 鼠标移入
const mouseEnter = () => {
  isEnter.value = true
  emits("mouseEnter", props.itemIndex)
}
// 鼠标移出
const mouseLeave = () => {
  isEnter.value = false
  emits("mouseLeave")
}
// 点击回调
const click = () => {
  emits("click", props.itemIndex)
}

onMounted(() => {
  // 宽度需要减去padding
  if (navItemRef.value != null) {
    emits("getWidth", props.itemIndex, navItemRef.value.offsetWidth - 20)
  }
})
</script>

<style lang="less" scoped>
.nav-item {
  position: relative;
  display: flex;
  flex-direction: row;
  line-height: 66px;
  padding-right: 20px;
  user-select: none;
  cursor: pointer;
  z-index: 2;
  .item-active {
    color: skyblue;
  }
  .icon {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    width: 20px;
    font-size: 20px;
    margin-right: 4px;
    vertical-align: middle;
  }
  .text {
    font-size: 13px;
    vertical-align: middle;
    transition: all 0.2s;
  }
  .num {
    margin-left: 2px;
    font-size: 12px;
    color: #99a2aa;
    font-family: Arial;
    vertical-align: middle;
  }
}
</style>
