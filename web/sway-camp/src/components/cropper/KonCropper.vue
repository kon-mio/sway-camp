<template>
  <div class="kon-cropper">
    <div class="kon-cropper-mark"></div>
    <div class="kon-cropper-container">
      <!-- 图片源 -->
      <div class="kon-cropper__source">
        <div class="kon-cropper__source--img">
          <img id="image" ref="sourceImg" class="image-source" />
        </div>
      </div>
      <div class="kon-cropper__line"></div>
      <!-- 裁切预览 -->
      <div class="kon-cropper__preview">
        <div class="kon-cropper__preview--img"></div>
        <slot></slot>
      </div>
      <!-- 选择图片 -->
      <div class="kon-tip__choose">
        <span>选择图片</span>
        <input ref="chooseInput" type="file" accept="image/*" />
      </div>
      <!-- 预览图片 -->
      <div class="kon-tip__preview">
        <span>预览图片</span>
      </div>
      <div class="kon-tip__rule">
        请选择图片上传：大小180 * 180像素支持JPG、PNG等格式，图片需小于10M
      </div>
      <!-- 操作按钮 -->
      <div class="kon-btn">
        <button class="kon-btn__cropper">裁剪</button>
        <div class="kon-btn__route">
          <button class="route-left">左转</button>
          <button class="route-right">右转</button>
        </div>
      </div>
      <!-- 标题 -->
      <div class="kon-title">
        <b>裁剪图片</b>
      </div>
      <div class="close">
        <sway-icon name="guanbi" :size="24" color="#999" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue"
import Cropper from "cropperjs"
import "cropperjs/dist/cropper.css"
import { base64ToFile, getBase64Image, loadImage } from "./util/ImageUtil"

const props = withDefaults(
  defineProps<{
    options: Cropper.Options<HTMLImageElement>
    imgLink: string
  }>(),
  {}
)
const emits = defineEmits<{
  (e: "currentImage", image: File): void
  (e: "closeCropper"): void
}>()

// 默认属性
const defineOptions: Cropper.Options<HTMLImageElement> = {
  // 视图模式
  viewMode: 2,
  // 拖拽模式
  dragMode: "move",
  // 预览
  preview: ".kon-cropper__preview--img",
  // 固定裁剪框宽高比
  aspectRatio: 1 / 1,
  // 是否可移动or放大图片
  movable: false,
  scalable: false,
  zoomable: false,
  zoomOnTouch: false,
  zoomOnWheel: false,
  // 当点击两次时可以在“crop”和“move”之间切换拖拽模式，
  toggleDragModeOnDblclick: false,
  checkCrossOrigin: false
}

// 初始化裁剪插件实例
const swayCropper = ref<Cropper>()
const sourceImg = ref<HTMLImageElement>()
const cropperOptions = ref<Cropper.Options<HTMLImageElement>>({})

// 合并默认属性
const handleOption = (
  propOptions: Cropper.Options<HTMLImageElement>
): Cropper.Options<HTMLImageElement> => {
  return Object.assign(defineOptions, propOptions)
}
// 初始化裁切插件
const cropperInit = (image: HTMLImageElement) => {
  if (!sourceImg.value) return
  const base64Image = getBase64Image(image)
  sourceImg.value.src = base64Image
  swayCropper.value = new Cropper(sourceImg.value, cropperOptions.value)
}
onMounted(async () => {
  if (!sourceImg.value) return
  // 整合配置项
  cropperOptions.value = handleOption(props.options as Cropper.Options<HTMLImageElement>)
  // 图片链接处理
  loadImage(props.imgLink, cropperInit)
})
</script>

<style lang="less" scoped>
::v-deep(.cropper-bg) {
  background-color: rgb(141, 141, 141) !important;
  background-image: none !important;
}
.kon-cropper {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  z-index: 2000;
  &-mark {
    position: absolute;
    width: 100%;
    height: 100%;
    opacity: 0.2;
    background-color: black;
  }

  &-container {
    position: relative;
    display: grid;
    grid-template-columns: 3fr 1px 2fr;
    grid-template-rows: 200px 40px 40px 1fr;
    grid-template-areas:
      "a b c"
      "d e f"
      "g g g"
      "h h h";
    width: 800px;
    height: 500px;
    box-sizing: border-box;
    padding: 100px 20px 0px 20px;
    overflow: hidden;
    border-radius: 12px;
    background-color: rgb(255, 255, 255);
  }
  &__source {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    &--img {
      width: 360px;
      height: 200px;
      border-radius: 12px;
      overflow: hidden;
      .image-source {
        display: none;
        width: 256px;
        height: 200px;
        object-fit: cover;
      }
    }
  }
  &__line {
    margin-top: 10px;
    height: 180px;
    border: 0.5px #ebebeb solid;
  }
  &__preview {
    display: flex;
    align-items: center;
    justify-content: center;
    &--img {
      width: 180px;
      height: 180px;
      border-radius: 50%;
      overflow: hidden;
    }
    &--square {
      width: 240px;
      height: 150px;
      border-radius: 8px;
      overflow: hidden;
    }
  }
}
.kon-title {
  position: absolute;
  top: 20px;
  left: 30px;
  b {
    font-size: 18px;
  }
}
.close {
  position: absolute;
  top: 18px;
  right: 20px;
  cursor: pointer;
  box-sizing: border-box;
  padding: 4px;
  border-radius: 50%;
  border: 1px solid rgb(235, 235, 235);
  z-index: 999999;
  transition: all 0.25s;
  &:hover {
    transform: scale(1.1) rotate(180deg);
    box-shadow: 0 0 4px 1px rgb(175, 175, 175);
  }
}
.kon-tip {
  position: relative;
  &__choose {
    grid-area: d;
    display: flex;
    align-items: center;
    justify-content: center;
    span {
      font-size: 12px;
      color: #acacac;
      cursor: pointer;
    }
    input {
      display: none;
    }
  }
  &__preview {
    grid-area: f;
    display: flex;
    align-items: center;
    justify-content: center;
    span {
      font-size: 12px;
      color: #acacac;
      cursor: pointer;
    }
  }
  &__rule {
    grid-area: g;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 12px;
    color: #acacac;
  }
}
.kon-btn {
  grid-area: h;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  &__cropper {
    width: 120px;
    height: 40px;
    border: none;
    outline: none;
    border-radius: 6px;
    cursor: pointer;
    &:hover {
      background-color: rgb(195, 195, 195);
    }
  }
  &__route {
    position: absolute;
    bottom: 20px;
    right: 40px;
    button {
      width: 60px;
      height: 30px;
      border: none;
      outline: none;
      border-radius: 6px;
      margin: 2px;
      cursor: pointer;
      &:hover {
        background-color: rgb(195, 195, 195);
      }
    }
  }
}
</style>
