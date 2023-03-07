<template>
  <div class="anime">
    <div class="main">
      <span class="close-btn" @click="back">
        <sway-icon name="guanbi" :size="20" color="#ffffffb7"></sway-icon>
      </span>
      <div class="main-bg animate__animated animate__fadeInLeftBig">
        <div>
          <img :src="anime.cover" />
        </div>
      </div>
      <div class="main-context animate__animated animate__lightSpeedInRight">
        <div class="context-cover">
          <img :src="anime.cover" />
        </div>
        <div class="context-anime">
          <h3>{{ anime.name }}({{ anime.originalName }})</h3>
          <ul class="context-anime-else">
            <li>
              <span>放送时间</span>
              <p>{{ (anime.broadcastTime as string).split("T")[0] }}</p>
            </li>
          </ul>
          <ul class="context-anime-tips"></ul>
          <div class="context-anime-impress paragraph-truncate">简介：{{ anime.introduction }}</div>
          <div class="context-anime-intordu paragraph-truncate"></div>
          <span class="context-anime-href el-icon-data-line">看番</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Anime } from "@/api/anime/type"

const props = defineProps<{
  anime: Anime
  modelValue: boolean
}>()

const emits = defineEmits<{
  (el: "update:modelValue", close: boolean): void
}>()

const back = () => {
  emits("update:modelValue", false)
}
</script>

<style lang="less" scoped>
.anime {
  position: relative;
  width: 100%;
  height: 100%;
  overflow-y: auto;
  overflow-x: hidden;
}
.main {
  position: sticky;
  top: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: calc((100vh - 600px) / 4);
  width: 100%;
  height: 400px;
  overflow: hidden;
  z-index: 12;

  &:hover {
    .close-btn {
      left: 0;
    }
  }
  .close-btn {
    position: absolute;
    top: 0px;
    left: -100px;
    justify-content: center;
    align-items: center;
    display: flex;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    transition: all 0.3s ease;
    z-index: 16;
    cursor: pointer;
    &:hover {
      transform: scale(1.2);
    }
  }

  &-bg {
    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 1;

    div {
      position: absolute;
      width: 110%;
      min-width: 1120px;
      height: 496px;
      top: 50%;
      left: 50%;
      margin: -260px -55%;
      z-index: 10;
      background: no-repeat 50% / cover;
      filter: blur(40px);

      &::before {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        display: block;
        width: 100%;
        height: 100%;
        background-color: #0000004d;
        z-index: 100;
      }

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        filter: blur(40px);
      }
    }
  }

  &-context {
    width: 70%;
    box-sizing: border-box;
    height: 100%;
    position: absolute;
    padding: 44px;
    z-index: 4;

    .context-cover {
      position: relative;
      box-sizing: border-box;
      float: left;
      width: 225px;
      height: 100%;
      border: 4px solid #fff;
      background: #fff;
      border-radius: 8px;
      overflow: hidden;

      img {
        width: 100%;
        height: 100%;
        border-radius: 6px;
        transition: all 1s ease;
        object-fit: cover;
      }
    }

    .context-anime {
      position: relative;
      box-sizing: border-box;
      float: left;
      width: calc(100% - 225px);
      height: 100%;
      padding: 10px 20px;
      color: #fff;

      .paragraph-truncate {
        display: -webkit-box !important;
        -webkit-box-orient: vertical;
        text-overflow: ellipsis;
        -webkit-line-clamp: 4;
        overflow: hidden;
        text-align: justify;
      }

      h3 {
        font-size: 24px;
        font-weight: 700;
        margin: 0;
      }

      &-else {
        width: 100%;
        height: 36px;
        margin: 20px 0;
        list-style-type: none;
        padding: 0;

        li {
          display: inline-flex;
          flex-direction: column;
          justify-content: space-around;
          align-items: flex-start;
          width: 90px;
          border-right: 1px solid #fff;
          height: 36px;
          font-size: 12px;
          margin-right: 16px;

          p {
            margin: 0;
          }
        }
      }

      &-tips {
        margin: 10px 0;
        list-style-type: none;
        padding: 0;

        li {
          font-size: 12px;
          display: inline-block;
          vertical-align: middle;
          height: 20px;
          padding: 0 4px;
          margin: 4px 10px 4px 0;
          line-height: 20px;
          border: 1px solid #fff;
          border-radius: 3px;
        }
      }

      &-impress {
        font-size: 14px;
        line-height: 20px;
        margin-top: 10px;
      }

      &-intordu {
        font-size: 14px;
        line-height: 20px;
        margin-top: 10px;
      }

      &-href {
        position: absolute;
        left: 20px;
        bottom: 6px;
        width: 128px;
        height: 40px;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: skyblue;
        border-radius: 8px;
        color: #fff;
        font-size: 16px;
        cursor: pointer;
        transition: all 0.3s ease;
      }
    }
  }
}
</style>
