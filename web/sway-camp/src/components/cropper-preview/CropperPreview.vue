<template>
  <div class="kon-cropper">
    <div class="kon-cropper-container">
      <!-- 图片源 -->
      <div v-loading="sourceLoading" class="kon-cropper__source">
        <div class="kon-cropper__source--img">
          <img id="image" ref="sourceImg" class="image-source" />
        </div>
      </div>
      <div class="kon-cropper__line"></div>
      <!-- 裁切预览 -->
      <div class="kon-cropper__preview">
        <div v-if="!options?.preview" class="kon-cropper__preview--img"></div>
        <slot></slot>
      </div>
      <!-- 选择图片 -->
      <div class="kon-tip__choose">
        <span @click="chooseImage">选择图片</span>
        <input ref="localInput" type="file" accept="image/*" @change="ImageChange" />
      </div>
      <!-- 预览图片 -->
      <div class="kon-tip__preview">
        <span>预览图片</span>
      </div>
      <div class="kon-tip__rule">
        请选择图片上传：大小180 * 180像素支持JPG、PNG等格式，图片需小于10M
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
    options?: Cropper.Options<HTMLImageElement> | null
    src?: string
  }>(),
  { options: null, src: "", isOpen: false }
)

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
const sourceLoading = ref(true)
const currentLoading = ref(false)
// 初始化裁剪插件实例
const swayCropper = ref<Cropper | null>(null)
const sourceImg = ref<HTMLImageElement | null>(null)
const cropperOptions = ref<Cropper.Options<HTMLImageElement>>({})

// 选择图片
const localInput = ref<HTMLInputElement>()
const chooseImage = () => {
  if (!localInput.value) return
  localInput.value.click()
}
const ImageChange = () => {
  //获取file对象blob
  if (localInput.value?.files && swayCropper.value) {
    const localImage = localInput.value.files[0]
    swayCropper.value.replace(URL.createObjectURL(localImage))
  }
}

// 裁切图片回调
const currentImage = () => {
  currentLoading.value = true
  if (!swayCropper.value) return
  const canvas = swayCropper.value.getCroppedCanvas()
  const image = canvas.toDataURL("image/webp")
  return base64ToFile(image)
}

// 关闭裁切加载动画
const closeCurrentLoading = () => {
  currentLoading.value = false
}
// 合并默认属性
const handleOption = (
  propOptions: Cropper.Options<HTMLImageElement> | null
): Cropper.Options<HTMLImageElement> => {
  return Object.assign(defineOptions, propOptions)
}
// 初始化裁切插件
const cropperInit = (image: HTMLImageElement) => {
  if (!sourceImg.value) return
  const base64Image = getBase64Image(image)
  sourceImg.value.src = base64Image
  swayCropper.value = new Cropper(sourceImg.value, cropperOptions.value)
  addEvent()
}

const addEvent = () => {
  sourceImg.value?.addEventListener("ready", () => {
    sourceLoading.value = false
    swayCropper.value?.reset()
  })
}
onMounted(() => {
  // 整合配置项
  cropperOptions.value = handleOption(props.options)
  // 图片链接处理
  loadImage(props.src, cropperInit)
})

defineExpose({
  closeCurrentLoading,
  currentImage
})
</script>

<style lang="less" scoped>
::v-deep(.cropper-bg) {
  background-color: rgb(141, 141, 141) !important;
  background-image: none !important;
}
.kon-cropper {
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;

  &-container {
    position: relative;
    display: grid;
    grid-template-columns: 3fr 1px 2fr;
    grid-template-rows: 200px 40px 40px 1fr;
    grid-template-areas:
      "a b c"
      "d e f"
      "g g g";
    width: 800px;
    height: 280px;
    box-sizing: border-box;
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
      width: 360px !important;
      height: 200px !important;
      border-radius: 12px;
      overflow: hidden;
      .image-source {
        display: none;
        width: 256px !important;
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
</style>
