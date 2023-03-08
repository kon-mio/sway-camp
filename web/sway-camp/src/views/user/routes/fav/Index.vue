<template>
  <div id="fav" class="fav">
    <div v-for="(item, index) in favList.list" :key="index" class="fav__card">
      <div class="cover">
        <fav-card :article-id="item.id" :src="item.cover" />
      </div>
      <div class="info">
        <div class="title">
          <b>{{ item.title }}</b>
        </div>
        <div class="fav-i">
          <el-tooltip content="取消收藏" placement="right">
            <sway-icon name="a-icon_buxihuanxinsui" :size="18" @click="removeFav(item.id)" />
          </el-tooltip>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, onBeforeMount } from "vue"
import { listFavArticleApi, removeFavApi } from "@/api/article/api"
import { ArticleList } from "@/api/article/type"
import { HttpStatusCode } from "@/common/enum"
import FavCard from "../../components/article/FavCard.vue"
import { useGlobalStore } from "@/stores/global.sotre"

const globalStore = useGlobalStore()

const favPage = reactive<{ index: number; size: number }>({
  index: 1,
  size: 20
})

const favList = reactive<ArticleList>({
  list: [],
  total: 0
})

const removeFav = async (id: number) => {
  const res = await removeFavApi(id)
  if (res.code === HttpStatusCode.Success) {
    const index = favList.list.findIndex((item) => item.id === id)
    if (index === -1) return
    favList.list.splice(index, 1)
    globalStore.openMessageMini("取消成功")
  } else {
    globalStore.openMessageMini("取消失败")
  }
}

const listFavArticle = async () => {
  const res = await listFavArticleApi(favPage.index, favPage.size)
  if (res.code === HttpStatusCode.Success) {
    Object.assign(favList, res.data)
  }
}
onBeforeMount(listFavArticle)
</script>

<style lang="less" scoped>
.fav {
  position: relative;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  flex: 1;
  width: 100%;
  height: fit-content;

  &__card {
    width: 300px;
    height: 260px;
    margin: 0 20px 20px 10px;

    .cover {
      width: 300px;
      height: 200px;
    }

    .info {
      position: relative;
      height: 40px;
      box-sizing: border-box;
      padding: 0 10px;
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: between;

      .title {
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
        width: 80%;
        overflow: hidden;
        cursor: pointer;

        b {
          color: rgb(82, 82, 82);
        }
      }

      .fav-i {
        position: absolute;
        right: 10px;
        i {
          font-size: 24px;
          color: rgb(124, 124, 124);
          cursor: pointer;
        }
      }
    }
  }
}
</style>
