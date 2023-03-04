<template>
  <Transition name="fade">
    <div v-show="modelValue" class="dialog-overlay">
      <div class="dialog-container-mask" @click="closeDialog"></div>
      <div class="dialog-container-renderer">
        <div class="header">
          <div class="header-container">
            <div class="header-container-title">{{ title }}</div>
            <div class="header-container-right" @click="closeDialog">
              <div class="close">
                <sway-icon name="guanbi" :size="20" />
              </div>
            </div>
          </div>
        </div>
        <div class="main"><slot></slot></div>
      </div>
    </div>
  </Transition>
</template>

<script lang="ts" setup>
const props = withDefaults(
  defineProps<{
    modelValue: boolean
    title?: string
  }>(),
  {
    title: "标题"
  }
)
const emits = defineEmits<{
  (e: "update:modelValue", visible: boolean): void
}>()
// 关闭对话框
const closeDialog = () => {
  emits("update:modelValue", false)
}
</script>

<style lang="less" scoped>
.dialog-overlay {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 2000;
  height: 100%;
  background: rgba(0, 0, 0, 0.2);
  overflow: auto;
}
.dialog-container {
  &-mask {
    position: relative;
    width: 100%;
    height: 100%;
    z-index: 2000;
  }
  &-renderer {
    position: absolute;
    margin-left: auto;
    display: flex;
    flex-direction: column;
    max-width: 970px;
    border-radius: 3px;
    background: white;
    box-shadow: rgb(15 15 15 / 2%) 0px 0px 0px 1px, rgb(15 15 15 / 3%) 0px 3px 6px,
      rgb(15 15 15 / 6%) 0px 9px 24px;
    top: 72px;
    left: 72px;
    right: 72px;
    margin-right: auto;
    height: calc(100% - 144px);
    z-index: 9999;
  }
}
.header {
  z-index: 110;
  background: white;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  user-select: none;
  &-container {
    height: 44px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-left: 12px;
    padding-right: 12px;
    &-title {
      color: rgb(165, 164, 161);
    }
    &-right {
      .close {
        margin-right: 10px;
        cursor: pointer;
      }
    }
  }
}
.main {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  position: relative;
  z-index: 1;
  overflow: hidden auto;
  box-sizing: border-box;
  //     ::-webkit-scrollbar 滚动条整体部分，可以设置宽度啥的
  // ::-webkit-scrollbar-button 滚动条两端的按钮
  // ::-webkit-scrollbar-track 外层轨道
  // ::-webkit-scrollbar-track-piece 内层滚动槽
  // ::-webkit-scrollbar-thumb 滚动的滑块
  // ::-webkit-scrollbar-corner 边角
  // ::-webkit-resizer 定义右下角拖动块的样式
  &::-webkit-scrollbar {
    // width: 10px;
    // color: black;
    width: 10px;
  }
  &::-webkit-scrollbar-thumb {
    background-color: #d3d1cb;
  }

  &::-webkit-scrollbar-thumb:hover {
    background-color: #aeaca6;
  }
  &::-webkit-scrollbar-track {
    background-color: #edece9;
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
