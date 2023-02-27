<template>
  <div class="article-read">
    <!-- 背景图 -->
    <div class="article-read-bg">
      <div ref="transBgRef" class="trans-bg" @animationend="removeAnime(getArticle)">
        <img v-if="articleInfo.cover" :src="articleInfo.cover" />
        <div v-show="coverMark" class="trans-bg-mark"></div>
      </div>
    </div>
    <!-- 内容 -->
    <Transition name="base">
      <div v-if="articleInfo.content" class="article-read-frame">
        <div class="left"></div>
        <!-- 文章 -->
        <div class="center">
          <div class="center-header"></div>
          <div class="center-main">
            <!-- <kon-md-preview :markdown="articleInfo.content" /> -->
            <!-- <MdPreview class="abyss-md" :text="articleInfo.content" /> -->
            <VMdPreview :content="articleInfo.content" @get-catalogues="getCataLogue" />
          </div>
          <div id="center-comment" class="center-comment"></div>
        </div>
        <!-- 导航 -->
        <div class="right">
          <div class="time">
            <time-card :card-bg="articleInfo.cover" />
          </div>
          <div class="right-cata">
            <header>导航目录</header>
            <div class="right-cata-scrollbar">
              <div
                v-for="(item, index) in catalogues"
                :key="index"
                class="right-cata__item"
                :style="`padding-left:${item.indent * 10}px`"
              >
                <!-- <a :class="item.active === true ? 'active' : ''" :href="`#${item.id}`">{{ item.title }}</a> -->
                <a
                  @click="directoryJump(item.tagName)"
                  :class="item.active === true ? 'active' : ''"
                  >{{ item.title }}</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, onBeforeMount, ref, reactive } from "vue"
import { storeToRefs } from "pinia"
import { getArticleApi } from "@/api/article/api"
import { useArticleStore } from "@/stores/article.store"
import { HttpStatusCode } from "@/common/enum"
import type { ArticleInfo } from "@/api/article/type"
import type { ArticleCover } from "@/components/article-cover/type"
import KonMdPreview from "@/components/md-editor/KonMdEditor.vue"
import MdPreview from "../../components/md-preview/MdPreview.vue"
import VMdPreview from "../../components/md-preview/VMdPreview.vue"
import SwayNotion from "@/utils/notice"
import TimeCard from "@/components/time-card/TimeCard.vue"
import { Catalogue } from "../../type"
// 背景处理
function coverFuncModule() {
  const transBgRef = ref<HTMLDivElement | null>()
  const coverMark = ref(false)
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
      coverMark.value = true
      callback()
    }, 100)
  }
  // 设置图片初始信息
  const coverInit = (coverInfo: ArticleCover): string => {
    // console.log(coverInfo)
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
  return { transBgRef, coverMark, coverInit, removeAnime }
}
// 目录处理
function catalogueModule() {
  const catalogues = reactive<Catalogue[]>([])

  // 获取目录
  const getCataLogue = (catalogueList: Catalogue[]) => {
    console.log(catalogueList)
    Object.assign(catalogues, catalogueList)
  }
  // 目录跳转
  const directoryJump = (targetName: string) => {
    document.querySelector(targetName)?.scrollIntoView(true)
  }
  // 滚动刷新目录
  const refreshCalalogue = () => {
    return
  }

  return {
    catalogues,
    getCataLogue,
    directoryJump
  }
}
// 通过路由传递文章ID
const props = defineProps<{
  id: string
}>()
const articleStore = useArticleStore()
const { coverInfo } = storeToRefs(articleStore)
const { transBgRef, coverMark, coverInit, removeAnime } = coverFuncModule()
const { catalogues, getCataLogue, directoryJump } = catalogueModule()

// 文章信息
const articleInfo = reactive<ArticleInfo>({
  id: 0,
  userId: 0,
  username: "",
  sort: "",
  label: "",
  cover: "",
  title: "",
  introduction: "",
  viewCount: 0,
  likeCount: 0,
  createTime: "",
  updateTime: "",
  content: null,
  reprinted: null
})
const getArticle = async () => {
  const res = await getArticleApi(Number(props.id))
  if (res.code === HttpStatusCode.Success) {
    Object.assign(articleInfo, res.data)
  } else {
    SwayNotion("获取文章失败", "error", "warning")
  }
}

onBeforeMount(() => {
  // 跳转错误页
  if (!props.id) return
})
onMounted(async () => {
  // 初始化图片位置
  if (coverInfo.value) {
    articleInfo.cover = coverInit(coverInfo.value)
  } else {
    await getArticle()
    coverMark.value = true
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

  &-frame {
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    box-sizing: border-box;
    padding: 50px 40px 40px 40px;
    overflow-x: hidden;
    overflow-y: auto;
    scroll-behavior: smooth;
    .left {
      position: sticky;
      top: -30px;
      left: 0;
      width: 260px;
      height: fit-content;
      border-radius: 12px;
      &-tools {
        margin-top: 30px;
        display: flex;
        flex-direction: column;
        align-items: flex-end;
        div {
          display: flex;
          justify-content: center;
          align-items: center;
          width: 36px;
          height: 36px;
          margin-top: 16px;
          border-radius: 50%;
          background: #fff;
          box-shadow: 0 2px 4px #0000000a;
          cursor: pointer;
          color: #b2bac2;
          outline: none;
        }
      }
      &-friendship {
        margin-top: 12px;
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
    }
    .center {
      flex: 1;
      min-width: 500px;
      max-width: 800px;
      height: fit-content;
      margin: 0 30px;
      padding: 20px;
      box-sizing: border-box;
      border-radius: 12px;
      background-color: white;

      &-comment {
        width: 100%;
        height: 100%;
        min-height: 400px;
        box-sizing: border-box;
        padding-top: 40px;
      }
    }
    .right {
      position: sticky;
      top: -30px;
      left: 0;
      display: flex;
      flex-direction: column;
      // align-items: center;
      width: 260px;
      height: 90vh;
      border-radius: 12px;

      .time {
        margin-bottom: 20px;
        ::v-deep(.time-card) {
          border-radius: 12px;
        }
      }

      &-cata {
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
        padding: 8px 4px 8px 16px;
        overflow-y: auto;
        border-radius: 12px;
        background-color: white;
        header {
          display: flex;
          align-items: center;
          width: 100%;
          height: 30px;
          font-size: 1rem;
          font-weight: 700;
          color: rgb(105, 105, 105);
          box-sizing: border-box;
          padding: 10px 0 20px 0;
        }

        &-scrollbar {
          display: flex;
          flex-direction: column;
          overflow-x: hidden;
          box-sizing: border-box;
          padding: 4px 4px 0 0;

          /* scrollbar */
          &::-webkit-scrollbar {
            width: 4px;
          }
          &::-webkit-scrollbar-thumb {
            border-radius: 15px;
          }
        }

        &__item {
          a {
            box-sizing: border-box;
            margin: 1.2px 0;
            padding: 4px;
            display: block;
            position: relative;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            text-decoration: none;
            color: #000;
            font-size: 14px;
            cursor: pointer;
          }
        }
      }
      .active {
        color: #007fff;
        background-color: #ebedef;
        border-radius: 4px;
      }
    }
  }
}
.base-enter-active,
.base-leave-active {
  transition: opacity 0.5s ease;
}

.base-enter-from,
.base-leave-to {
  opacity: 0;
}
</style>
