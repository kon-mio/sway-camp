<template>
  <div id="app" class="app">
    <div class="app-aside">
      <AppAside />
    </div>
    <div class="app-main">
      <AppRouter />
    </div>
    <MessageMini v-model="messageMini.isOpen" :content="messageMini.content" />
  </div>
</template>

<script lang="ts" setup>
import "@/assets/font/iconfont.js"
import "@/assets/font/iconfont.css"
import AppAside from "./layout/app-aside/AppAside.vue"
import AppRouter from "./layout/app-router/AppRouter.vue"
import { useGlobalStore } from "./stores/global.sotre"
import MessageMini from "./components/konmio/message/message-mini.vue"
import { storeToRefs } from "pinia"
// mini消息框
const { messageMini } = storeToRefs(useGlobalStore())
</script>

<style lang="less">
@import "@/assets/css/common.less";
</style>

<style lang="less" scoped>
.app {
  position: relative;
  display: flex;
  flex-direction: row;
  width: 100vw;
  height: 100vh;
  min-height: 600px;
  overflow: hidden;

  &-aside {
    width: 200px;
    min-width: 200px;
    height: 100%;
  }
  &-main {
    position: relative;
    width: calc(100vw - 200px);
    min-width: 1200px;
    height: 100%;
    overflow: hidden;
    border-top-left-radius: 12px;
    border-bottom-left-radius: 12px;
    background-color: @bg-gray-1;
  }
}
</style>
