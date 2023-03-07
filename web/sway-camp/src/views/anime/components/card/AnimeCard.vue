<template>
  <div class="anime-card" style="" @click="animeInfo(anime.id)">
    <div class="anime-card-img">
      <img :src="anime.cover" />
    </div>
    <div class="anime-card-info">
      <p tabindex="0" aria-describedby="el-popper-6248" class="anime-card-info-msg">
        {{ anime.name }}
      </p>
      <div class="anime-card-info-else">
        <i class="el-icon-caret-top"> </i>
        <b>{{ (anime.broadcastTime as string).split("-")[0] }} </b>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Anime } from "@/api/anime/type"

const props = withDefaults(
  defineProps<{
    anime: Anime
  }>(),
  {}
)
const emits = defineEmits<{
  (el: "click", animeId: number): void
}>()
const animeInfo = (animeId: number) => {
  emits("click", animeId)
}
</script>

<style lang="less" scoped>
.anime-card {
  position: relative;
  top: 0;
  width: 100%;
  border-radius: 14px;
  background-color: #fff;
  overflow: hidden;
  transition: all 0.2s ease;
  cursor: pointer;

  &:hover {
    transform: translateY(-10px);
    box-shadow: 6px 6px 10px 2px #cfcfcf;

    .anime-card-img {
      img {
        transform: scale(1.1);
      }
    }

    // .anime-card-info {}
  }

  &-img {
    position: relative;
    width: 100%;
    height: 0;
    padding: 30% 0;
    background: #ecf5ff;

    &::after {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      display: block;
      width: 100%;
      height: 100%;
    }

    img {
      position: absolute;
      top: 0;
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: all 0.4s ease;
    }
  }

  &-info {
    box-sizing: border-box;
    display: flex;
    justify-content: space-around;
    align-items: center;
    position: relative;
    width: 100%;
    height: 80px;
    padding: 18px 26px;

    &-msg {
      display: -webkit-box !important;
      width: 100%;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      -moz-box-orient: vertical;
      -moz-line-clamp: 2;
      text-overflow: ellipsis;
      white-space: pre-wrap;
      width: 260px;
      overflow: hidden;
      font-size: 16px;
      color: #717171;
      font-weight: 600;
    }

    &-else {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      width: 43px;
      height: 43px;

      i {
        display: block;
        font-size: 20px;
        color: #a5a4a4;
      }

      b {
        font-size: 12px;
        color: #a5a4a4;
      }
    }
  }
}
</style>
