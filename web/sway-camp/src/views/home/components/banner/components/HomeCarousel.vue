<template>
  <div class="banner-carousel">
    <el-carousel
      ref="elCarousel"
      height="100%"
      :autoplay="autoPlay"
      :pause-on-hover="true"
      :style="carouselStyle"
      indicator-position="none"
      @change="carouselChange"
      @mouseleave="navLeave"
      @mouseenter="navEnter(null)"
    >
      <el-carousel-item
        v-for="(index, item) in list"
        :key="item"
        :class="
          currentIndex === index.id
            ? 'carousel-active'
            : lastIndex === index.id
            ? 'carousel-leave'
            : ''
        "
      >
        <img :src="index.imgUrl" />
      </el-carousel-item>
    </el-carousel>
    <div class="carousel-nav">
      <div
        v-for="(item, index) in list"
        :key="index"
        class="carousel-nav-item"
        @mouseleave="navLeave"
        @mouseenter="navEnter(index)"
      >
        <div class="item-container">
          <div
            class="item-inner"
            :class="{ 'is-progressing': currentIndex === index && startPlay }"
          >
            <div
              class="progress-bar-border"
              :style="navBorderStyle"
              :class="{ 'is-progressing': currentIndex === index && startPlay && autoPlay }"
            ></div>
            <div class="progress-bar-content">
              <img :src="item.imgUrl" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 底部控制、信息栏 -->
    <div class="carousel-indicator">
      <ul>
        <li
          v-for="index in list.length"
          :key="index"
          :class="{ 'li-active': currentIndex === index - 1 }"
          @mouseenter="navEnter(index - 1)"
          @mouseleave="navLeave"
        ></li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, CSSProperties, defineComponent, nextTick, onMounted, PropType, ref } from "vue"
import { ElCarousel } from "element-plus"
import type { carouselType } from "../type"
export default defineComponent({
  name: "HomeCarousel",
  props: {
    carouselItems: {
      type: Array as PropType<carouselType[]>,
      default: () => []
    },
    autoPlayTime: {
      type: Number,
      default: 3
    }
  },
  setup(props) {
    const autoPlay = ref(false)
    const startPlay = ref(false)
    const elCarousel = ref<InstanceType<typeof ElCarousel> | null>(null)
    // 幻灯片索引
    const currentIndex = ref(0)
    const lastIndex = ref(0)
    // 轮播图列表
    const list = computed(() => {
      return props.carouselItems
    })
    // 自动切换时间间隔
    const playTime = computed(() => {
      return props.autoPlayTime
    })
    // el增加样式
    const carouselStyle = computed<CSSProperties>(() => {
      return {
        height: "100%",
        overflow: "hidden"
      }
    })
    // border动画时间
    const borderTransTime = ref(playTime.value)
    const navBorderStyle = computed<CSSProperties>(() => {
      return {
        transition: `width ${borderTransTime.value}s linear`
      }
    })
    // 轮播图改动回调
    const carouselChange = (current: number, last: number) => {
      currentIndex.value = current
      lastIndex.value = last
    }
    // 移动触发
    const navEnter = (id: number | null = null) => {
      autoPlay.value = false
      borderTransTime.value = 0
      if (id !== null) {
        elCarousel.value?.setActiveItem(id)
      }
    }
    const navLeave = () => {
      autoPlay.value = true
      borderTransTime.value = playTime.value
    }
    onMounted(() => {
      nextTick(() => {
        autoPlay.value = true
        startPlay.value = true
      })
    })
    return {
      list,
      autoPlay,
      startPlay,
      elCarousel,
      lastIndex,
      currentIndex,
      carouselStyle,
      navBorderStyle,
      navEnter,
      navLeave,
      carouselChange
    }
  }
})
</script>

<style lang="less" scoped>
.banner-carousel {
  position: relative;
  width: 100%;
  height: 100%;

  // ::v-deep(.el-carousel__indicators--horizontal) {
  //   left: 2%;
  //   transform: translateX(0);
  // }
  ::v-deep(.el-carousel__item) {
    // 解决未知遮罩错误
    z-index: 1;
  }

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.carousel-nav {
  position: absolute;
  right: 16px;
  bottom: 12px;
  display: flex;
  flex-direction: row;
  z-index: 10;

  &-item {
    position: relative;
    margin-right: 14px;
    border-radius: 10px;
    width: 135px;
    height: 78px;
    cursor: pointer;

    img {
      position: relative;
      height: 100%;
      width: 100%;
      border-radius: 8px;
      object-fit: cover;
    }
    .item-container {
      position: relative;
      width: 100%;
      padding-top: 55.79%;
    }
    .item-inner {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      border-radius: 10px;
      overflow: hidden;
      transition: all 0.4s;
      &.is-progressing {
        top: -8px;
        left: -12px;
        width: calc(100% + 24px);
        height: calc(100% + 14px);
        box-shadow: 0 0 40px hsl(0deg 0% 100% / 60%), 0 16px 32px rgb(0 0 0 / 16%),
          0 8px 16px rgb(0 0 0 / 8%);
        .progress-bar-content {
          padding: 4px;
        }
      }
    }
    .progress-bar-border {
      width: 0px;
      height: 100%;
      &.is-progressing {
        background: hsla(0, 0%, 100%, 0.9);
        width: 100%;
      }
    }
    .progress-bar-content {
      position: absolute;
      top: 0;
      left: 0;
      height: 100%;
      width: 100%;
      box-sizing: border-box;
      padding: 3px;
      border-radius: 8px;
      background: linear-gradient(180deg, hsla(0, 0%, 100%, 0.4), hsla(0, 0%, 100%, 1) 50%);
      transition: all 0.4s;
    }
  }
}
.carousel-indicator {
  position: absolute;
  left: 20px;
  bottom: 10px;
  flex-grow: 1;
  z-index: 2;
  transition: filter 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
  ul,
  li {
    list-style-type: none;
    padding: 0;
    margin: 0;
  }
  li {
    display: inline-block;
    height: 4px;
    width: 20px;
    margin: 0 3px;
    border-radius: 2px;
    background-color: rgba(255, 255, 255, 0.3);
    cursor: pointer;
    transition: width 0.5s;
  }
  .li-active {
    width: 36px;
    background-color: #f0f0f0;
  }
}

.carousel-active {
  animation: carousel-in 0.5s ease-out;
}

.carousel-leave {
  animation: carousel-out 0.5s ease-in;
}

@keyframes carousel-in {
  0% {
    opacity: 0;
    transform: translate3d(300px, 0, 1px) scale(1.5);
  }

  100% {
    opacity: 1;
    transform: translate3d(0, 0, 0) scale(1);
  }
}

@keyframes carousel-out {
  0% {
    opacity: 1;
    transform: translate3d(0, 0, 0) scale(1);
  }

  100% {
    opacity: 0;
    transform: translate3d(-300px, 0, 1px) scale(1.5);
  }
}
</style>
