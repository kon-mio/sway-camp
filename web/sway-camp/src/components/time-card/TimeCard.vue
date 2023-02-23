<template>
  <div class="time-card" title="时间">
    <div class="time-card__cover">
      <img :src="tcBg" />
    </div>
    <div class="time-card__content">
      <div v-for="(value, key, index) in nowTime" :key="key" class="time-frame">
        <div class="time-frame--info" :style="{ animationDuration: `${index * 0.4 + 0.8}s` }">
          <p>{{ value }}</p>
          <span>{{ key }}</span>
        </div>
        <b v-if="key !== 'seconds'">:</b>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted, reactive, toRefs } from "vue"
export default defineComponent({
  name: "TimeCard",
  props: {
    cardBg: {
      type: String,
      default: "http://file.takagi-san.cn/image/kon_all_57.webp"
    }
  },
  setup(props) {
    const tcBg = computed(() => {
      return props.cardBg
    })
    interface nowTimeType {
      hours: string | number
      minutes: string | number
      seconds: string | number
    }
    // 获取当前时间
    const TimeData = reactive({
      nowTime: {
        hours: new Date().getHours() < 10 ? "0" + new Date().getHours() : new Date().getHours(),
        minutes:
          new Date().getMinutes() < 10 ? "0" + new Date().getMinutes() : new Date().getMinutes(),
        seconds:
          new Date().getSeconds() < 10 ? "0" + new Date().getSeconds() : new Date().getSeconds()
      } as nowTimeType
    })
    onMounted(() => {
      setInterval(() => {
        TimeData.nowTime.hours =
          new Date().getHours() < 10 ? "0" + new Date().getHours() : new Date().getHours()
        TimeData.nowTime.minutes =
          new Date().getMinutes() < 10 ? "0" + new Date().getMinutes() : new Date().getMinutes()
        TimeData.nowTime.seconds =
          new Date().getSeconds() < 10 ? "0" + new Date().getSeconds() : new Date().getSeconds()
      }, 1000)
    })
    return {
      tcBg,
      ...toRefs(TimeData)
    }
  }
})
</script>

<style lang="less" scoped>
.time-card {
  position: relative;
  width: 100%;
  overflow: hidden;
  box-shadow: 1px 0 6px rgb(0 0 0 / 4%);
  cursor: pointer;

  &__cover {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    z-index: 1;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: all 0.5s ease;
    }
  }

  &:hover {
    .time-card__cover {
      img {
        filter: blur(4px);
      }
    }
    .time-card__content {
      &::before {
        opacity: 1;
      }
    }
  }

  &__content {
    position: relative;
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    height: 130px;
    box-sizing: border-box;
    padding: 0 10px;
    user-select: none;
    &::before {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      z-index: 2;
      opacity: 0;
      background: rgba(0, 0, 0, 0.2);
      transition: all 0.3s ease;
    }
    .time-frame {
      display: flex;
      flex-direction: row;
      b {
        font-size: 32px;
        color: #fff;
        width: 16px;
        height: 70px;
        z-index: 2;
        text-align: center;
        text-shadow: 0.8px 0 5px #333;
      }

      &--info {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: center;
        width: 60px;
        height: 80px;
        z-index: 2;
        color: #fff;
        animation-name: clockanm;
        p {
          margin: 0px;
          font-size: 40px;
          text-shadow: 0.8px 0 5px #333;
        }

        span {
          font-size: 12px;
        }
      }
    }
  }

  @keyframes clockanm {
    0% {
      transform: translateY(-100%);
    }

    100% {
      transform: translateY(0);
    }
  }
}
</style>
