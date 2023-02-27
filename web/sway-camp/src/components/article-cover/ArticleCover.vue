<template>
  <img ref="cover" class="article-cover" :src="src" @error="loadError" />
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue"
import { ArticleCover } from "./type"

const props = withDefaults(
  defineProps<{
    src?: string
  }>(),
  {
    src: "https://sway-camp.oss-cn-qingdao.aliyuncs.com/image/cover/540083f441c14c8e839dd6b70a831eaa.webp"
  }
)

const cover = ref<HTMLImageElement | null>(null)
// 默认信息
const coverInfo = reactive<ArticleCover>({
  src: props.src,
  width: 0,
  height: 0,
  top: 0,
  left: 0
})
// 获取图片信息
const getCover = (): ArticleCover | undefined => {
  if (!cover.value) return
  const articleCoverInfo: ArticleCover = {
    src: cover.value.src,
    width: cover.value.offsetWidth,
    height: cover.value.offsetHeight,
    top: cover.value.getBoundingClientRect().top,
    left: cover.value.getBoundingClientRect().left - 200
  }
  Object.assign(coverInfo, articleCoverInfo)
  return coverInfo
}
// 图片加载错误
const loadError = () => {
  if (!cover.value) return
  cover.value.src = props.src
}
// 导出方法
defineExpose({
  getCover
})
</script>

<style lang="less" scoped>
.article-cover {
  width: 100%;
  height: 100%;
  object-fit: cover;
  user-select: none;
}
</style>
