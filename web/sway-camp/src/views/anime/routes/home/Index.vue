<template>
  <div id="anime-home" class="anime-home">
    <div class="anime-banner">
      <AnimeCarousel v-if="animeList.length" :anime-list="animeList" />
    </div>
    <div class="anime-list">
      <div class="anime-list__content slide-ltin-up">
        <AnimeCard
          v-for="(item, index) in animeList"
          :key="index"
          :anime="item"
          class="slide-ltin-up"
          @click="getAnime"
        />
      </div>
    </div>
  </div>
  <div v-show="mainShow" class="anime-main">
    <home-main v-model="mainShow" :anime="anime" />
  </div>
  <break-top target="anime-home" />
  <!-- <div v-show="$route.name === 'AnimeMain'" class="anime-main">
    <router-view></router-view>
  </div> -->
</template>

<script lang="ts" setup>
import { Anime } from "@/api/anime/type"
import { reactive, onBeforeMount, onMounted, ref } from "vue"
import AnimeCarousel from "../../components/carousel/AnimeCarousel.vue"
import { getAnimeApi, listRecommendAnimeApi } from "@/api/anime/api"
import { HttpStatusCode } from "@/common/enum"
import AnimeCard from "../../components/card/AnimeCard.vue"
import HomeMain from "../../components/main/HomeMain.vue"
import { useRoute, useRouter } from "vue-router"

const $route = useRoute()
const $router = useRouter()

const mainShow = ref(false)

const anime = reactive<Anime>({
  id: 0,
  name: "",
  originalName: "",
  cover: "",
  region: "",
  introduction: "",
  broadcastTime: "",
  officialWebsite: "",
  labels: [],
  image: []
})
const animeList = reactive<Anime[]>([])

const getAnime = async (id: number) => {
  $router.replace({ query: { id: id } })
  const res = await getAnimeApi(id)
  if (res.code === HttpStatusCode.Success) {
    Object.assign(anime, res.data)
    mainShow.value = true
  }
}
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
  overflow-y: auto;
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
.anime-list {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 1000px;
  &__content {
    width: 80%;
    min-width: 1200px;
    margin-top: 30px;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    padding: 10px 0;
    grid-gap: 18px;
    overflow: hidden;
  }
}
.anime-main {
  position: absolute;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 1000;
  background-color: rgba(233, 227, 227, 0.524);
}

.slide-ltin-up {
  animation: slide-ltin-up 0.5s forwards;
}

@keyframes slide-ltin-up {
  0% {
    transform: translateY(100px);
  }

  100% {
    transform: translateY(0);
  }
}
</style>
