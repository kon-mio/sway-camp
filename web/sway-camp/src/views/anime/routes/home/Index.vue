<template>
  <div class="anime-home">
    <div class="anime-banner animate__animated animate__backInRight">
      <AnimeCarousel v-if="animeList.length" :anime-list="animeList" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Anime } from "@/api/anime/type"
import { reactive, onBeforeMount } from "vue"
import AnimeCarousel from "../../components/carousel/AnimeCarousel.vue"
import { listRecommendAnimeApi } from "@/api/anime/api"
import { HttpStatusCode } from "@/common/enum"

const animeList = reactive<Anime[]>([])
onBeforeMount(async () => {
  const res = await listRecommendAnimeApi()
  if (res.code === HttpStatusCode.Success) {
    Object.assign(animeList, res.data)
  }
})
</script>

<style lang="less" scoped>
.anime-home {
  position: relative;
  width: 100%;
  height: 100%;
}
.anime-banner {
  position: relative;
  top: 20px;
  left: 2%;
  width: 98%;
  min-height: 700px;
  max-height: 95vh;
  aspect-ratio: 16/10;
  overflow: hidden;
  border-top-left-radius: 24px;
  border-bottom-left-radius: 24px;
}
</style>
