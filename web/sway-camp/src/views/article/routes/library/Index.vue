<template>
  <!--！bug 为该页面增加路由缓存 导致跳转阅读界面（ReadArticle）onMounted加载两次 -->
  <div class="article-search">
    <div class="article-search__header">
      <div class="article-search__header--keyword">
        <input
          v-model="searchPage.keyword"
          type="text"
          style="width: 850px"
          placeholder="请输入文章关键字"
        />
        <el-button
          color="#08D9D6"
          type="primary"
          style="aspect-ratio: 1/1; height: 100%; margin-left: 6px"
          @click="searchArticle"
        >
          <sway-icon name="sousuo" :size="24" color="white" />
        </el-button>
      </div>
      <div class="article-search__header--label">
        <ul class="filter">
          <li
            v-for="(item, index) in sortList"
            :key="index"
            :class="item.id === activeIndex ? 'active' : ''"
            @click="filterArticle(item.id)"
          >
            {{ item.sortName }}
          </li>
        </ul>
      </div>
    </div>
    <!-- 内容 -->
    <div class="article-search__content">
      <div ref="scrollTarget" class="article-search__content--list" @scroll="scroll(libraryScroll)">
        <div v-for="(item, index) in articleList" :key="index" class="article-item">
          <ArticleItem :article="item" />
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onBeforeMount, onMounted } from "vue"
import { listSearchArticleApi, listSortApi } from "@/api/article/api"
import { ArticleInfo, ArticleSort, SearchArticleDTO } from "@/api/article/type"
import { HttpStatusCode } from "@/common/enum"
import ArticleItem from "../../components/library-main/article-card/ArticleItem.vue"
import { useScroll } from "@/hooks/useScroll.hooks"
import { filter } from "lodash"

const { scrollData, scrollTarget, scroll, open, close } = useScroll()

const activeIndex = ref(0)
const searchPage = reactive<SearchArticleDTO>({
  index: 1,
  size: 4,
  sort: null,
  keyword: null
})
const articleList = reactive<ArticleInfo[]>([])
// 文章标签列表
const sortList = reactive<ArticleSort[]>([
  {
    id: 0,
    sortName: "全部",
    sortDescription: "所有文章",
    articleCount: 0,
    labels: []
  }
])
// 初始化搜索信息
const searchInit = () => {
  searchPage.index = 1
  searchPage.sort = null
}
const listSortList = async () => {
  const res = await listSortApi()
  if (res.code === HttpStatusCode.Success) {
    sortList.push(...res.data)
  }
}
// 查询文章列表
const listArticle = async () => {
  close()
  const res = await listSearchArticleApi(searchPage)
  if (res.code === HttpStatusCode.Success && res.data.length > 0) {
    articleList.push(...res.data)
    searchPage.index++
    open()
  }
}
const searchArticle = () => {
  searchInit()
  activeIndex.value = 0
  articleList.length = 0
  listArticle()
}
const filterArticle = (itemId: number) => {
  articleList.length = 0
  console.log(articleList)
  activeIndex.value = itemId
  const sort = sortList.find((item) => {
    return item.id === itemId
  })
  searchInit()
  searchPage.sort = sort?.id === 0 ? null : sort?.id || null
  listArticle()
}
// 滚动事件
const libraryScroll = () => {
  if (scrollData.scrollTop + scrollData.clientHeight + 100 > scrollData.scrollHeight) {
    listArticle()
  }
}

onMounted(async () => {
  await listSortList()
  await listArticle()
})
</script>

<style lang="less" scoped>
.article-search {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  &__header {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: center;
    width: 100%;
    height: 260px;
    background-color: #c6dfdf;
    // background: url('@/assets/image/banner.jpg');
    // background-size: cover;
    // filter: blur(1px);
    &--keyword {
      position: relative;
      display: flex;
      width: 900px;
      height: 48px;
      box-shadow: 0 4px 44px #08d9d633;

      input {
        box-sizing: border-box;
        padding-left: 12px;
        border-radius: 8px;
        border: none;
        outline: rgb(159, 228, 255);
        font-size: 14px;
        color: #606297;

        &::placeholder {
          font-size: 15px;
        }
      }

      button {
        border-radius: 10px;
      }
    }
    &--label {
      width: 900px;
      margin-bottom: 10px;
      .filter {
        display: flex;
        gap: 20px;
        padding: 20px 0;
        font-size: 14px;
        list-style-type: none;
        margin: 0;

        li {
          display: flex;
          align-items: center;
          padding: 0 20px;
          height: 36px;
          user-select: none;
          transition: all 0.25s;
          color: #fff;
          cursor: pointer;
        }

        .active {
          background: #fff;
          border-radius: 10px;
          color: #08d9d6;
        }
      }
    }
  }
  &__content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: calc(100% - 260px);
    width: 100%;
    box-sizing: border-box;
    padding-bottom: 20px;
    box-shadow: inset 0 0 18px #00000026;

    &--list {
      width: 940px;
      height: 100%;
      overflow-y: scroll;
    }
    .article-item {
      margin-top: 20px;
    }
  }
}
</style>
