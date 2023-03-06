<template>
  <!-- 单个动漫Item -->
  <div class="tab-item" :class="{ hide: reAnime.action.animation }" :style="tabItemStyle">
    <div class="item-card" :class="{ active: reAnime.action.active }">
      <!-- 封面 -->
      <div class="item-card__cover">
        <img class="base-img" :src="reAnime.list.cover" alt="" />
        <!-- 标题 -->
        <div class="item-card__cover-info">
          <b>{{ reAnime.list.name }}</b>
          <!-- <p>{{ rcAnime.episodes }}</p> -->
          <ul class="item-card__labels">
            <li v-for="(item, index) in reAnime.list.labels" :key="index">
              {{ item }}
            </li>
          </ul>
        </div>
      </div>
      <div class="progress">
        <div class="progress-inner"></div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import type { RecommendAnime } from "@/api/anime/type"
import { computed } from "vue"

const props = withDefaults(
  defineProps<{
    anime: RecommendAnime
    time?: number
  }>(),
  { time: 500 }
)

const reAnime = computed(() => {
  return props.anime
})
const tabItemStyle = computed(() => {
  return {
    transform: `translateX(${reAnime.value.action.translateX}px)`,
    opacity: 1,
    transitionDuration: `${props.time + 100}ms, 100ms`,
    transitionProperty: `transform, opacity`
  }
})
</script>

<style lang="less" scoped>
.item-card {
  position: relative;
  width: 340px;
  padding-right: 20px;
  box-sizing: border-box;
  background-clip: content-box;
  filter: brightness(0.5);
  transition: all 0.25s;
  animation: enter 0.25s forwards;
  &.active {
    filter: brightness(1);
  }

  &:hover {
    .active;
  }

  &__cover {
    position: relative;
    width: 100%;
    // 由宽高比确定宽度
    aspect-ratio: 1.6/1;
    overflow: hidden;
    border-radius: 8px;
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    &::before {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      display: block;
      width: 100%;
      height: 100%;
      z-index: 1;
      background: none;
    }

    &-info {
      position: absolute;
      bottom: 0;
      left: 0;
      width: 100%;
      padding: 6px 14px;
      box-sizing: border-box;

      &::before {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        display: block;
        width: 100%;
        height: 100%;
        z-index: 1;
        background: linear-gradient(to top, #000, rgba(0, 0, 0, 0.521));
        filter: blur(4px);
        transform: scale(1.2);
      }

      p,
      b {
        position: relative;
        z-index: 3;
      }

      b {
        color: rgba(255, 255, 255, 0.9);
      }

      p {
        color: rgba(255, 255, 255, 0.4);
        font-size: 14px;
        margin-top: 4px;
      }
    }
  }
  &__labels {
    position: relative;
    margin: 2px 0;
    list-style-type: none;
    padding: 0;
    z-index: 10;

    li {
      font-size: 12px;
      display: inline-block;
      vertical-align: middle;
      height: 20px;
      padding: 0 4px;
      margin: 4px 10px 4px 0;
      line-height: 20px;
      border: 1px solid rgb(222, 222, 222);
      border-radius: 3px;
      color: rgb(226, 226, 226);
      z-index: 10;
    }
  }

  .progress {
    margin-top: 12px;
    width: 100%;
    height: 4px;
    border-radius: 4px;
    overflow: hidden;
    background: rgba(209, 242, 255, 0.634);

    &-inner {
      width: 40%;
      height: 100%;
      border-radius: 4px;
    }
  }

  img {
    width: 100%;
    height: 100%;
    object-fit: covor;
  }
}

.hide {
  opacity: 0 !important;
  z-index: -1;
}
</style>
