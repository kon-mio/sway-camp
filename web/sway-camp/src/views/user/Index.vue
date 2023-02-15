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
        <div
          class="cursor"
          :style="{
            width: compWidth + 'px',
            left: compLeft + 'px',
            transition: `all ${cursorTransTime}s ease`
          }"
        ></div>
      </div>
    </div>
    <div class="content">
      <app-router />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref, watch, onMounted, computed } from "vue"
import { useRoute, useRouter } from "vue-router"
import AppRouter from "@/layout/app-router/AppRouter.vue"
import NavigatorItem from "./components/NavigatorItem.vue"
import { storeToRefs } from "pinia"
import { useUserStore } from "@/stores/user.store"
import type { NavigatorItemType } from "./types/user-nav"
import { isEmpty } from "@/utils/data/valid"

// 注意：因为每次切换子路由时每次都会初始化'提示线'的数据，
//      导致切换路由时每次'提示线'都会从头开始，故使用定时器只在需要时提供线条动画
//      暂未知原因 ！！！

const $route = useRoute()
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

// 选中id
const activeId = ref(1)
const mouseEnterId = ref<number | null>(null)

// 提示线
const cursorWidth = ref(4)
const cursorTransTime = ref(0)

// 获取距离左侧总和
const compLeft = computed(() => {
  let count = 0
  if (mouseEnterId.value != null) {
    navList.forEach((item) => {
      if (item.id < mouseEnterId.value!) {
        count += item.width! + 20
      }
    })
  } else {
    navList.forEach((item) => {
      if (item.id < activeId.value) {
        count += item.width! + 20
      }
    })
  }
  return count
})
const compWidth = computed(() => {
  let width = 0
  if (mouseEnterId.value != null) {
    navList.forEach((item) => {
      if (item.id == mouseEnterId.value) {
        width = item.width!
      }
    })
  } else {
    navList.forEach((item) => {
      if (item.id == activeId.value) {
        width = item.width!
      }
    })
  }
  return width
})
// 使用动画
const useCursorAnime = (time: number) => {
  cursorTransTime.value = time
  setTimeout(() => {
    cursorTransTime.value = 0
  }, time * 100)
}
// 鼠标移入
const mouseEnter = (itemId: number) => {
  useCursorAnime(0.25)
  mouseEnterId.value = itemId
}
const mouseLeave = () => {
  useCursorAnime(0.25)
  mouseEnterId.value = null
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

const chooseItem = (id: number) => {
  let activeItem = navList.find((item) => {
    return item.id === id
  })
  if (isEmpty(activeItem)) {
    return
  }
  $router.push({ name: activeItem!.name, params: { id: userInfo.value?.id } })
  activeId.value = activeItem?.id!
}

const navigatorInit = () => {
  // 根据路由确定活动页面
  let activeItem = navList.find((item) => {
    return item.name === $route.name
  })
  activeId.value = activeItem?.id!
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
      position: relative;
      margin-left: 50px;
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
      background: @line-blue-4;
    }
  }
}
</style>
