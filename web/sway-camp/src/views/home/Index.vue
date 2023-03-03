<template>
  <div id="sway-home" ref="scrollTarget" class="sway-home" @scroll="scroll(homeScroll)">
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
        <template #article>
          <title-box title="受欢迎文章">
            <recommend-article-card
              v-for="(item, index) in recomArticleList"
              :key="index"
              :article="item"
              :article-index="index"
            />
          </title-box>
        </template>
      </main-left>
      <main-center>
        <div v-for="(item, index) in articleList.list" :key="index" class="article-item">
          <article-card :article="item" />
        </div>
        <div class="loading-tip">
          <div v-show="loading" class="loading-anime animate__animated animate__fadeInUp">
            <notion-loading />
            <span>{{ loadText }}</span>
          </div>
          <span v-if="page.all && !loading" class="animate__animated animate__fadeInUp"
            >加载完成</span
          >
        </div>
      </main-center>
      <main-right>
        <template #about>
          <AboutMe />
        </template>
        <template #find>
          <TitleBox title="关于我">
            <find-me v-for="(item, index) in aboutList" :key="index" :item="item" />
          </TitleBox>
        </template>
        <template #testack>
          <TitleBox title="我的技术栈">
            <TestackCard
              v-for="(item, index) in teStackList"
              :key="index"
              :color="item.color"
              :name="item.name"
            />
          </TitleBox>
        </template>
        <template #friendship>
          <TitleBox title="友情链接">
            <div class="friendship">
              <ul>
                <li>
                  <sway-icon name="pinglun" :size="12" />
                  高木
                </li>
                <li>
                  <sway-icon name="pinglun" :size="12" />
                  西片
                </li>
              </ul>
            </div>
          </TitleBox>
        </template>
      </main-right>
    </div>
    <break-top target="sway-home" />
  </div>
</template>

<script lang="ts" setup>
import { reactive, onMounted, ref, computed } from "vue"
import HomeBanner from "./components/banner/HomeBanner.vue"
import HomeCarousel from "./components/banner/components/HomeCarousel.vue"
import type { carouselType } from "./components/banner/type"
import HomeImgView from "./components/banner/components/HomeImgView.vue"
import HomeAnimeShow from "./components/banner/components/HomeAnimeShow.vue"
import MainCenter from "./components/main/MainCenter.vue"
import MainLeft from "./components/main/MainLeft.vue"
import MainRight from "./components/main/MainRight.vue"
import WebSiteCard from "./components/about-me/WebSiteCard.vue"
import ArticleCard from "./components/article/ArticleCard.vue"
import { listArticleApi, listRecommendApi } from "@/api/article/api"
import { ArticleInfo, ArticleList } from "@/api/article/type"
import { HttpStatusCode } from "@/common/enum"
import TitleBox from "@/components/title-box/TitleBox.vue"
import RecommendArticleCard from "@/components/article/article-card-recommend/ArticleCard.vue"
import { useScroll } from "@/hooks/useScroll.hooks"
import NotionLoading from "@/components/loading/NotionLoading.vue"
import TestackCard from "./components/about-me/TestackCard.vue"
import AboutMe from "./components/about-me/AboutMe.vue"
import FindMe from "./components/about-me/FindMe.vue"

function articleModule() {
  const { scrollData, scrollTarget, scroll, open, close } = useScroll()
  // 文章分页信息
  const loading = ref(false)
  const loadText = computed(() => {
    return loading.value ? "加载中" : "加载完成"
  })
  const page = reactive<{ index: number; size: number; max: number; all: boolean }>({
    index: 1,
    size: 2,
    max: 40,
    all: false
  })
  // 文章列表
  const articleList = reactive<ArticleList>({
    list: [],
    total: 0
  })
  // 推荐文章列表
  const recomArticleList = reactive<ArticleInfo[]>([])
  const listArticle = async () => {
    close()
    loading.value = true
    const res = await listArticleApi(page.index, page.size)
    if (res.code === HttpStatusCode.Success) {
      articleList.list.push(...res.data.list)
      articleList.total = res.data.total
      page.index++
      open()
    } else {
      page.all = true
    }
    loading.value = false
  }
  const listRecomArticle = async () => {
    const res = await listRecommendApi(3)
    if (res.code === HttpStatusCode.Success) {
      recomArticleList.push(...res.data)
    }
  }
  // 滚动事件
  const homeScroll = () => {
    if (scrollData.scrollTop + scrollData.clientHeight + 200 > scrollData.scrollHeight) {
      listArticle()
    }
  }
  return {
    page,
    loading,
    loadText,
    articleList,
    scrollTarget,
    recomArticleList,
    scroll,
    homeScroll,
    listArticle,
    listRecomArticle
  }
}
// 文章加载相关
const {
  page,
  loading,
  loadText,
  articleList,
  scrollTarget,
  recomArticleList,
  scroll,
  homeScroll,
  listArticle,
  listRecomArticle
} = articleModule()

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

// 其他
const aboutList = reactive<
  {
    id: number
    name: string
    link: string
    icon: string
    size: number
  }[]
>([
  { id: 1, name: "npm", link: "", icon: "npm", size: 12 },
  { id: 2, name: "掘金", link: "", icon: "shuqian", size: 16 },
  { id: 3, name: "BiliBili", link: "", icon: "dianshi", size: 16 },
  { id: 4, name: "GitHub", link: "", icon: "github-fill", size: 16 }
])

const teStackList = reactive<
  {
    name: string
    color: string
  }[]
>([
  { name: "Vue2/3", color: "skyblue" },
  { name: "SpringBoot", color: "pink" },
  { name: "Java", color: "rgb(212, 243, 189)" },
  { name: "HTML5", color: "rgb(182, 132, 244)" },
  { name: "Css", color: "skyblue" },
  { name: "Less", color: "rgb(255, 133, 154)" },
  { name: "TypeScript", color: "rgb(212, 243, 189)" },
  { name: "Vite", color: "pink" },
  { name: "WebPack", color: "rgb(255, 133, 154)" }
])

onMounted(() => {
  listArticle()
  listRecomArticle()
})
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

    .friendship {
      position: relative;
      ul {
        position: relative;
        margin: 0;
        padding: 0;
        list-style-type: none;
        li {
          position: relative;
          left: -18px;
          width: calc(100% + 36px);
          margin: 0;
          padding: 0;
          list-style-type: none;
          box-sizing: border-box;
          display: block;
          padding: 6px 18px;
          font-size: 12px;
          color: #768791;
          transition: all 0.4s ease;
          cursor: pointer;
          &:hover {
            background-color: rgb(242, 242, 242);
          }
        }
      }
    }
    .article-item {
      height: 225px;
      margin-bottom: 20px;
    }
    .loading-tip {
      position: relative;
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 100%;
      height: 60px;
      .loading-anime {
        position: relative;
        display: flex;
        align-items: center;
        justify-content: center;
        width: 100%;
        height: 20px;
      }
      span {
        position: relative;
        top: 20px;
        font-size: 12px;
        color: #a1a1a1;
      }
    }
  }
}
</style>
