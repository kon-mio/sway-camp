<template>
  <div class="sway-home">
    <div class="sway-home__banner">
      <home-banner>
        <template #Carosel>
          <home-carousel :carousel-items="carouselItems" />
        </template>
        <template #Img>
          <home-img-view img="http://file.takagi-san.cn/image/kon_all_5.webp" />
        </template>
        <template #Anime>
          <home-anime-show />
        </template>
      </home-banner>
    </div>
    <div class="sway-home__main"></div>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from "vue"
import HomeBanner from "./components/banner/HomeBanner.vue"
import HomeCarousel from "./components/banner/components/HomeCarousel.vue"
import HomeImgView from "./components/banner/components/HomeImgView.vue"
import HomeAnimeShow from "./components/banner/components/HomeAnimeShow.vue"
import type { carouselType } from "./components/banner/type"

// 轮播图列表
const carouselItems = reactive<carouselType[]>([
  {
    id: 0,
    title: "1",
    imgUrl: "http://file.takagi-san.cn/image/kon_all_3.webp"
  },
  {
    id: 1,
    title: "1",
    imgUrl: "http://file.takagi-san.cn/image/kon_all_18.webp"
  },
  {
    id: 2,
    title: "1",
    imgUrl: "http://file.takagi-san.cn/image/kon_all_5.webp"
  }
])
</script>
<style lang="less" scoped>
.sway-home {
  position: relative;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  overflow-y: auto;

  &__banner {
    position: relative;
    top: 20px;
    left: 2%;
    display: grid;
    grid-template-columns: 7fr 3fr;
    grid-template-rows: 1fr;
    column-gap: 20px;
    width: 96%;
    height: 34vw;
  }
  &__main {
    margin-top: 50px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    width: 100%;
    height: 2000px;
  }
}
</style>
