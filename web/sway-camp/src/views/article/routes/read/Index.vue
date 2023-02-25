<template>
  <div class="article-read">
    <!-- 背景图 -->
    <div class="article-read-bg">
      <div ref="transBgRef" class="trans-bg">
        <img :src="articleBg" />
        <div v-show="coverLoading" class="trans-bg-mark"></div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ArticleCover } from "@/components/article-cover/type"
import { useArticleStore } from "@/stores/article.store"
import { storeToRefs } from "pinia"
import { onMounted, onBeforeMount, ref } from "vue"
// 背景处理
function CoverFuncModule() {
  const transBgRef = ref<HTMLDivElement | null>()
  const coverLoading = ref(false)
  // 添加动画
  const addAnime = () => {
    transBgRef.value?.classList.add("active")
  }
  // 移除动画
  const removeAnime = (callback: () => void) => {
    transBgRef.value?.classList.remove("active")
    transBgRef.value?.classList.add("mark")
    coverDestroy()
    setTimeout(() => {
      coverLoading.value = true
      callback()
    }, 100)
  }
  // 设置图片初始信息
  const coverInit = (coverInfo: ArticleCover): string => {
    console.log(coverInfo)
    addAnime()
    transBgRef.value?.style.setProperty("--article-cover-top", coverInfo.top + "px")
    transBgRef.value?.style.setProperty("--article-cover-left", coverInfo.left + "px")
    transBgRef.value?.style.setProperty("--article-cover-width", coverInfo.width + "px")
    transBgRef.value?.style.setProperty("--article-cover-height", coverInfo.height + "px")
    return coverInfo.src
  }
  const coverDestroy = () => {
    transBgRef.value?.style.removeProperty("--article-cover-top")
    transBgRef.value?.style.removeProperty("--article-cover-left")
    transBgRef.value?.style.removeProperty("--article-cover-width")
    transBgRef.value?.style.removeProperty("--article-cover-height")
  }
  return { transBgRef, coverLoading, coverInit, removeAnime }
}

const articleStore = useArticleStore()
const { coverInfo } = storeToRefs(articleStore)
const { transBgRef, coverLoading, coverInit, removeAnime } = CoverFuncModule()

//
const articleBg = ref()

onBeforeMount(() => {
  console.log("read1", coverInfo.value)
}),
  onMounted(() => {
    // 初始化图片位置
    if (coverInfo.value) {
      articleBg.value = coverInit(coverInfo.value)
    } else {
      // 刷新直接加载数据
      // if (Object.keys(articleData.article).length) return
      coverLoading.value = true
    }
  })
</script>

<style lang="less" scoped>
.article-read {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;

  &-bg {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    .trans-bg {
      position: relative;
      width: 100%;
      height: 100%;
      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
      &-mark {
        position: absolute;
        top: 0;
        width: 100%;
        height: 100%;
        background: rgba(220, 212, 212, 0.54);
      }
    }
    .active {
      animation: scale-up 0.8s ease-in-out;
    }
    @keyframes scale-up {
      0% {
        top: var(--article-cover-top);
        left: var(--article-cover-left);
        width: var(--article-cover-width);
        height: var(--article-cover-height);
      }
      100% {
        top: 0px;
        left: 0px;
        width: 100%;
        height: 100%;
      }
    }
  }
}
</style>
