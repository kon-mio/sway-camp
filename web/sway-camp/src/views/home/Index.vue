<template>
  <div id="sway-home" class="sway-home">
    <div class="sway-home__banner">
      <home-banner>
        <template #Carousel>
          <home-carousel :carousel-items="carouselItems" />
        </template>
        <template #Img>
          <home-img-view
            img="https://sway-camp.oss-cn-qingdao.aliyuncs.com/image/avatar/81e7bbcc651b49e493f4897a8d8c1915.webp"
          />
        </template>
        <template #Anime>
          <home-anime-show />
        </template>
      </home-banner>
    </div>
    <div class="sway-home__main">
      <main-left>
        <template #website>
          <web-site-card />
        </template>
      </main-left>
      <main-center>
        <div class="article-item" v-for="index in 20" :key="index">
          <article-card />
        </div>
      </main-center>
      <main-right></main-right>
    </div>
    <break-top target="sway-home" />
  </div>
</template>

<script lang="ts" setup>
import { reactive } from "vue"
import HomeBanner from "./components/banner/HomeBanner.vue"
import HomeCarousel from "./components/banner/components/HomeCarousel.vue"
import type { carouselType } from "./components/banner/type"
import HomeImgView from "./components/banner/components/HomeImgView.vue"
import HomeAnimeShow from "./components/banner/components/HomeAnimeShow.vue"
import MainCenter from "./components/main/MainCenter.vue"
import MainLeft from "./components/main/MainLeft.vue"
import MainRight from "./components/main/MainRight.vue"
import WebSiteCard from "./components/WebSiteCard.vue"
import ArticleCard from "./components/article/ArticleCard.vue"

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
    imgUrl:
      "https://sway-camp.oss-cn-qingdao.aliyuncs.com/image/avatar/940935cb641541889b946472021b815f.webp"
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
    position: relative;
    top: 40px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    width: 100%;
    .article-item {
      height: 225px;
      margin-bottom: 20px;
    }
  }
}
</style>
