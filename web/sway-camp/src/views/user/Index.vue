<template>
  <div class="user">
    <div class="header">
      <div class="header-bg">
        <img src="http://file.takagi-san.cn/image/f12f2c8d115245cea4878ff320f53e57.jpg" />
      </div>
    </div>
    <div class="navigator">
      <div class="wapper">
        <div class="nav-tab">
          <NavigatorItem
            v-for="(item, index) in navList"
            :key="index"
            :nav-item="item"
            :user-id="userInfo?.id!"
            @click="chooseItem"
            @get-width="getWidth"
            @mouse-enter="mouseEnter"
            @mouse-leave="mouseLeave"
          />
        </div>
      </div>
      <div
        class="cursor"
        :style="{
          width: cursorWidth + 'px',
          left: cursorLeft + 'px',
          transition: `all ${cursorTransTime}s ease`
        }"
      ></div>
    </div>
    <div class="content">
      <app-router />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref, watch, onMounted } from "vue"
import { useRoute } from "vue-router"
import AppRouter from "@/layout/app-router/AppRouter.vue"
import NavigatorItem from "./components/NavigatorItem.vue"
import { storeToRefs } from "pinia"
import { useUserStore } from "@/stores/user.store"
import type { NavigatorItemType } from "./types/user-nav"

// 注意：因为每次切换子路由时每次都会初始化'提示线'的数据，
//      导致切换路由时每次'提示线'都会从头开始，故使用定时器只在需要时事提供线条动画
//      暂未知原因 ！！！

const $route = useRoute()
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

// 选中id
const activeId = ref(1)
const mouseEnterId = ref<number | null>(null)

// 提示线
const cursorWidth = ref(54)
const cursorLeft = ref(30)
const cursorTransTime = ref(0)
// 获取距离左侧总和
const setLetfCount = (id: number) => {
  let leftInit = 30
  navList.forEach((item, index) => {
    if (item.id < id) {
      leftInit += item.width! + 20
    }
  })
  cursorLeft.value = leftInit
}
// 设置提示线宽度
const setCursorWidth = (id: number) => {
  navList.forEach((item) => {
    if (item.id == id) {
      cursorWidth.value = item.width!
    }
  })
}
// 鼠标移入
const mouseEnter = (itemId: number) => {
  cursorTransTime.value = 0.4
  setTimeout(() => {
    cursorTransTime.value = 0
  }, 400)
  mouseEnterId.value = itemId
  setLetfCount(mouseEnterId.value)
  setCursorWidth(mouseEnterId.value)
}
const mouseLeave = () => {
  cursorTransTime.value = 0.4
  setTimeout(() => {
    cursorTransTime.value = 0
  }, 400)
  mouseEnterId.value = null
  setLetfCount(activeId.value)
  setCursorWidth(activeId.value)
}
// 获取每个导航宽度
const getWidth = (id: number, width: number) => {
  navList.forEach((item) => {
    if (item.id == id) {
      item.width = width
      return
    }
  })
}

// watch(
//   () => $route.name,
//   (newValue) => {
//     let activeItem = navList.find((item) => {
//       return item.name === newValue
//     })
//     activeId.value = activeItem?.id!
//   }
// )
const chooseItem = (id: number) => {
  let activeItem = navList.find((item) => {
    return item.id === id
  })
  activeId.value = activeItem?.id!
  setLetfCount(activeId.value)
  setCursorWidth(activeId.value)
}

const navigatorInit = () => {
  // 根据路由确定活动页面
  let activeItem = navList.find((item) => {
    return item.name === $route.name
  })
  activeId.value = activeItem?.id!
  // 初始化提示线偏移、宽度
  setLetfCount(activeId.value)
  setCursorWidth(activeId.value)
}

onMounted(() => navigatorInit())
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
  .navigator {
    position: relative;
    width: 100%;
    height: 66px;
    box-sizing: border-box;
    padding: 0 30px;
    background-color: white;
    .wapper {
      margin: 0 auto;
      .nav-tab {
        display: flex;
        flex-direction: row;
      }
    }
    .cursor {
      position: absolute;
      left: 0;
      bottom: 0px;
      width: 0;
      height: 4px;
      border-radius: 4px;
      background: #00a1d6;
    }
  }
}
</style>
