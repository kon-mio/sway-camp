<template>
  <div class="sway-aside">
    <div class="sway-aside__header">
      <AsideHeader />
    </div>
    <div class="sway-aside-main">
      <div class="sway-aside-main__user">
        <AsideUser :user-tab-list="userTabList" />
      </div>
      <div class="sway-aside-main__tab">
        <AsideTab>
          <AsideTabItem v-for="(index, item) in routerList" :key="item" :tab-item="index" />
        </AsideTab>
      </div>
    </div>
    <div class="sway-aside__footer">
      <AsideFooter />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from "vue"
import AsideHeader from "./components/AsideHeader.vue"
import AsideUser from "./components/AsideUser.vue"
import AsideTab from "./components/AsideTab.vue"
import AsideTabItem from "./components/AsideTabItem.vue"
import AsideFooter from "./components/AsideFooter.vue"
import { asideTabItemType, userTabItemType } from "./type"
export default defineComponent({
  name: "AppAside",
  components: { AsideHeader, AsideUser, AsideTab, AsideTabItem, AsideFooter },
  setup() {
    // 路由列表
    const routerList = reactive<asideTabItemType[]>([
      {
        id: 0,
        name: "Home",
        title: "Kingの主页",
        icon: "shouyefill",
        rootRouteName: "Home"
      },
      {
        id: 1,
        name: "Article",
        title: "Kingの宝库",
        icon: "rizhi",
        size: 36,
        rootRouteName: "Article"
      },
      {
        id: 2,
        name: "Anime",
        title: "Kingの后宫",
        icon: "dongman",
        size: 44,
        rootRouteName: "Anime"
      }
    ])
    // 用户tabList
    const userTabList = reactive<userTabItemType[]>([
      { id: 0, icon: "user-o", title: "用户", func: "userSpace" },
      { id: 1, icon: "shoucang1", title: "收藏", func: "userFav" },
      { id: 2, icon: "lingdang2", title: "消息" },
      { id: 3, icon: "sign-review", title: "写文章", func: "writeArticle" },
      { id: 4, icon: "exit", title: "退出", func: "Exit" }
    ])
    return {
      routerList,
      userTabList
    }
  }
})
</script>

<style lang="less" scoped>
.sway-aside {
  position: relative;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;

  &__header {
    width: 100%;
    height: 100px;
  }
  &-main {
    display: flex;
    flex-direction: column;
    width: 100%;
    flex: 1;
    &__user {
      width: 100%;
      height: 130px;
    }
    &__tab {
      flex: 1;
    }
  }
  &__footer {
    width: 100%;
    height: 100px;
  }
}
</style>
