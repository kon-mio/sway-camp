<template>
  <div class="anime-info">
    <transition name="carousel-info">
      <div v-if="visible" class="inner">
        <span>特拉法尔加·罗</span>
        <h1>{{ ctAnime.otherName }}</h1>
        <p>
          {{ ctAnime.introduction }}
        </p>
        <div class="inner-rate">
          <el-rate
            v-model="rate"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value} points"
          />
        </div>
        <div class="inner-control">
          <el-button type="warning" round
            >前往播放
            <!-- <Icon name="play" /> -->
          </el-button>
          <el-button round plain>详情</el-button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script lang="ts" setup>
import { Anime } from "@/api/anime/type"
import { computed, ref } from "vue"

const props = withDefaults(
  defineProps<{
    currentAnime: Anime
    time?: number
    infoVisible?: boolean
  }>(),
  {
    time: 500,
    infoVisible: true
  }
)

const ctAnime = computed(() => {
  return props.currentAnime
})
const visible = computed(() => {
  return props.infoVisible
})
const rate = ref(5)
</script>

<style lang="less" scoped>
:deep(.el-rate__text) {
  color: white;
  font-style: italic;
  font-size: 12px;
}

.anime-info {
  // 自动填充甚于区域
  display: flex;
  align-items: center;
  padding: 40px;
  box-sizing: border-box;
  // border-top-left-radius: 12px;

  span {
    font-size: 12px;
    color: rgb(255 255 255 /0.9);
  }

  h1 {
    padding-top: 6px;
    padding-bottom: 18px;
    color: #fff;
  }

  p {
    font-size: 14px;
    color: rgb(255 255 255 /0.7);
    max-width: 60%;
    text-align: justify;
    display: -webkit-box;
    -webkit-line-clamp: 4;
    -webkit-box-orient: vertical;
    overflow: hidden;
    word-break: break-all;
  }

  .inner {
    &-rate {
      display: flex;
      align-items: center;
      margin-top: 16px;
      margin-bottom: 30px;
      font-size: 14px;
      color: rgb(255 255 255 /0.7);
    }

    &-control {
      ::v-deep(.el-button) {
        position: relative;
        padding: 6px 22px;
        min-height: 36px;
        margin-right: 10px;

        i {
          width: 8px;
          margin-left: 4px;
        }
      }

      ::v-deep(.el-button--default) {
        background: unset;
        color: #fff;

        &:first-child {
          background: var(--primary-color);
          border-color: var(--primary-color);
        }
      }
    }
  }
}

.carousel-info-enter-active {
  animation: slide 0.3s forwards;
}

.carousel-info-leave-active {
  animation: slide 0.3s forwards reverse;
}

@keyframes slide {
  0% {
    opacity: 0;
    transform: translateY(20px);
  }

  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
