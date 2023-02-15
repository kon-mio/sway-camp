<template>
  <div class="user">
    <div class="header">
      <div class="header-bg">
        <img src="http://file.takagi-san.cn/image/f12f2c8d115245cea4878ff320f53e57.jpg" />
      </div>
    </div>
    <div class="user-navigator">
      <navigator :nav-list="navList" @click="chooseItem" />
    </div>
    <div class="content">
      <app-router />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from "vue"
import { useRouter } from "vue-router"
import AppRouter from "@/layout/app-router/AppRouter.vue"
import Navigator from "./components/Navigator.vue"
import { storeToRefs } from "pinia"
import { useUserStore } from "@/stores/user.store"
import type { NavigatorItemType } from "./types/user-nav"

const $router = useRouter()
const { userInfo } = storeToRefs(useUserStore())
// 导航列表
const navList = reactive<NavigatorItemType[]>([
  { id: 1, title: "主页", name: "User", icon: "shouye", color: "skyblue", size: 24 },
  { id: 2, title: "文章", name: "UserArticle", icon: "icon2", color: "#00c091", size: 17, num: 10 },
  {
    id: 3,
    title: "收藏",
    name: "UserFav",
    icon: "tubiaozhizuomoban-",
    color: "#f3a034",
    size: 24
  },
  {
    id: 4,
    title: "个人信息",
    name: "UserInfo",
    icon: "dongtaixuanzhong",
    color: "#fb7299",
    size: 18
  },
  { id: 5, title: "账号设置", name: "UserAccount", icon: "shezhi1", color: "#23c9ed", size: 20 }
])
const chooseItem = (acIndex: number) => {
  let activeItem = navList.find((item, index) => {
    return index === acIndex
  })
  $router.push({ name: activeItem!.name, params: { id: userInfo.value?.id } })
}
</script>

<style lang="less" scoped>
.user {
  position: relative;
  width: 100%;
  height: 100%;
  .header {
    width: 100%;
    height: 200px;
    background-color: aquamarine;
    &-bg {
      width: 100%;
      height: 100%;
      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }
  }
  &-navigator {
    position: relative;
    width: 100%;
    height: 66px;
    box-sizing: border-box;
    padding: 0 30px;
    background-color: white;
  }
}
</style>
