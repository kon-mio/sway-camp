<template>
  <div class="pre-pic">
    <div class="pre-pic__move" :style="imgBgStyle"></div>
  </div>
</template>

<script lang="ts">
import { computed, CSSProperties, defineComponent } from 'vue'
export default defineComponent({
  name: 'HomeImgView',
  props: {
    img: {
      type: String,
      default: 'http://file.takagi-san.cn/image/kon_all_30.webp'
    }
  },
  setup(props) {
    const imgBgStyle = computed<CSSProperties>(() => {
      return {
        width: '100%',
        height: '100%',
        backgroundImage: `url(${props.img})`
      }
    })
    return {
      imgBgStyle
    }
  }
})
</script>

<style lang="less" scoped>
.pre-pic {
  width: 100%;
  height: 100%;
  overflow: hidden;

  &__move {
    background-size: cover;
    animation: picmove 16s linear infinite alternate;
  }

  @keyframes picmove {
    0% {
      background-position: 0 0;
    }

    20%,
    60% {
      transform: scale(1.1);
    }

    40% {
      transform: scale(1);
    }

    100% {
      background-position: 100% 100%;
    }
  }
}
</style>
