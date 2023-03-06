<template>
  <div class="carousel">
    <div class="carousel-bg">
      <Carousel
        ref="carouselTarget"
        :time="animeTime + 100"
        :auto="true"
        :tool="false"
        :interval="6000"
        :sequence="false"
        :carousel-items="coverList"
        @carousel-next="carouselNext"
      />
    </div>
    <div class="carousel-content">
      <div
        class="carousel-content__info"
        @mouseenter="carouselTarget?.stopInterval"
        @mouseleave="carouselTarget?.startInterval"
      >
        <anime-info
          :current-anime="recommendAnime[currentIndex].list"
          :info-visible="infoVisible"
        />
      </div>
      <div class="carousel-content__anime">
        <AnimeTab>
          <TabContainer @arrow-click="arrowClick">
            <AnimeItem v-for="(item, index) in recommendAnime" :key="index" :anime="item" />
          </TabContainer>
        </AnimeTab>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onBeforeMount, computed, reactive, ref } from "vue"
import Carousel from "@/components/carousel/Carousel.vue"
import AnimeInfo from "./anime-info/AnimeInfo.vue"
import AnimeTab from "./anime-tab/AnimeTab.vue"
import TabContainer from "./anime-tab/TabContainer.vue"
import AnimeItem from "./anime-item/AnimeItem.vue"
import { wait } from "@/utils/common"
import { repeatThrottle } from "@konmio/utils"
import type { Anime, RecommendAnime } from "@/api/anime/type"

const ITEM_WIDTH = 340
const props = withDefaults(
  defineProps<{
    animeList: Anime[]
    time?: number
  }>(),
  { time: 600 }
)
const animeTime = computed(() => props.time)
const animeList = computed(() => props.animeList)

const infoVisible = ref(true)
// 轮播图列表偏移
const offsetWidth = ref(0)
const currentIndex = ref(1)
const carouselTarget = ref<InstanceType<typeof Carousel> | null>()

// 推荐动漫
const recommendAnime = reactive<RecommendAnime[]>([])
// 动漫封面列表
const coverList = reactive<string[]>([])

const arrowClick = (isNext: boolean) => {
  repeatThrottle(async () => {
    carouselTarget.value?.stopInterval()
    carouselTarget.value?.arrowClick(isNext)
    changeCurrentItem(!isNext)
    await wait(animeTime.value)
    carouselTarget.value?.startInterval()
  })()
}
const changeCurrentItem = (isNext: boolean) => {
  infoVisible.value = false
  const delta = isNext ? -1 : 1
  currentIndex.value = (currentIndex.value + delta + recommendAnime.length) % recommendAnime.length
  const checkActive = (item: RecommendAnime) => {
    const marginLeftStart = item.action.id * ITEM_WIDTH
    item.action.active = marginLeftStart + item.action.translateX === ITEM_WIDTH
  }
  recommendAnime.forEach((item) => {
    if (isNext) {
      if (item.action.translateX < 0) {
        item.action.animation = false
        item.action.translateX += ITEM_WIDTH
      } else {
        const maxRight = offsetWidth.value - ITEM_WIDTH * (item.action.id + 1)
        if (Math.abs(item.action.translateX) === maxRight) {
          item.action.animation = true
          item.action.translateX = item.action.id * -ITEM_WIDTH
        } else {
          item.action.animation = false
          item.action.translateX += ITEM_WIDTH
        }
      }
    } else {
      if (item.action.translateX > 0) {
        item.action.animation = false
        item.action.translateX -= ITEM_WIDTH
      } else {
        const maxLeft = item.action.id * ITEM_WIDTH
        if (Math.abs(item.action.translateX) === maxLeft) {
          item.action.animation = true
          item.action.translateX = offsetWidth.value - (item.action.id + 1) * ITEM_WIDTH
        } else {
          item.action.animation = false
          item.action.translateX -= ITEM_WIDTH
        }
      }
    }
    checkActive(item)
  })
  setTimeout(() => {
    infoVisible.value = true
  }, animeTime.value - 200)
}
// 轮播图切换回调
const carouselNext = () => {
  changeCurrentItem(false)
}
//处理图片顺序
const handelSequence = (data: unknown[], start: number) => {
  const before = data.slice(0, start)
  return [...data.slice(start, data.length), ...before]
}
const carouselInt = () => {
  Object.assign(recommendAnime, handelSequence(animeList.value, animeList.value.length - 1))

  Object.assign(
    recommendAnime,
    recommendAnime.map((item, index) => ({
      list: item,
      action: {
        id: index,
        translateX: 0,
        animation: animeList.value.length - 1 === index ? true : false,
        active: index === 1 ? true : false
      }
    }))
  )
  // 处理封面
  Object.assign(
    coverList,
    recommendAnime.map((item) => item.list.cover)
  )

  offsetWidth.value = recommendAnime.length * 340
}
onBeforeMount(() => {
  carouselInt()
})
</script>

<style lang="less" scoped>
.carousel {
  width: 100%;
  height: 100%;
  &-bg {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
  &-content {
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    background: linear-gradient(to right, rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0));

    &__info {
      width: 100%;
      flex: 1;
      display: flex;
      align-items: center;
    }

    &__anime {
      position: relative;
      width: 100%;
      height: 300px;
      padding: 0 20px 10px 20px;
      box-sizing: border-box;
    }
  }
}
</style>
