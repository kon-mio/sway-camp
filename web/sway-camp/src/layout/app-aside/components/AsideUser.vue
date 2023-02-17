<template>
  <div class="user">
    <div class="unlogin" v-if="!isLogin" @click="openLoginCard">
      <span>未登录</span>
    </div>
    <div v-else class="login">
      <div class="login-user__avatar" @click="userSpace">
        <img
          src="http://file.takagi-san.cn/image/f12f2c8d115245cea4878ff320f53e57.jpg"
          class="base-img"
        />
      </div>
      <div class="login-user__name" @click="userSpace">{{ userInfo?.username }}</div>
      <div class="login-user__tab">
        <div
          class="login-user__tab--item"
          v-for="(index, item) in userTabList"
          :key="item"
          :title="index.title"
        >
          <el-popconfirm
            v-if="index.func == 'Exit'"
            title="确定退出"
            confirm-button-text="是"
            cancel-button-text="否"
            icon-color="#626AEF"
            @confirm="userTabMeth(index.func)"
          >
            <template #reference>
              <SwayIcon :name="index.icon" :size="16" />
            </template>
          </el-popconfirm>
          <SwayIcon v-else :name="index.icon" @click="userTabMeth(index.func)" :size="16" />
        </div>
      </div>
    </div>
    <login-card />
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, PropType } from "vue"
import { useRouter } from "vue-router"
import { storeToRefs } from "pinia"
import { useUserStore } from "@/stores/user.store"
import { useGlobalStore } from "@/stores/global.sotre"
import type { userTabItemType } from "../type"
import LoginCard from "@/components/login-card/LoginCard.vue"
import { isEmpty } from "@/utils/valid"

// 用户导航栏方法
function userTabMoudel() {
  const router = useRouter()
  const userStore = useUserStore()
  const { userInfo } = storeToRefs(userStore)
  const userTabMeth = (methName: string | undefined) => {
    switch (methName) {
      case "Exit":
        Exit()
        break
      case "userSpace":
        userSpace()
        break
      case "userFav":
        userFav()
        break
      case "writeArticle":
        writeArticle()
        break
      case "parrot":
        console.log("I own a parrot")
        break
      default:
        break
    }
  }
  // 退出
  const Exit = () => {
    userStore.exit()
    router.push({
      name: 'Home'
    })
  }
  // 跳转用户主页
  const userSpace = () => {
    if (isEmpty(userInfo)) {
      return
    }
    router.push({
      name: "User",
      params: {
        id: userInfo.value!.id
      }
    })
  }
  const userFav = () => {
    router.push({
      name: "SpaceFav"
    })
  }
  // 发表
  const writeArticle = () => {
    // if (userInfo.value.abyssRole === 1) {
    //   return
    // }
    router.push({
      name: "WriteArticle",
      params: {
        // userId: userInfo.value.id
      }
    })
  }
  return {
    userTabMeth,
    userSpace
  }
}
export default defineComponent({
  name: "AsideUser",
  components: { LoginCard },
  props: {
    userTabList: {
      type: Array as PropType<userTabItemType[]>,
      default: () => []
    }
  },
  setup() {
    const { userTabMeth, userSpace } = userTabMoudel()
    const userStore = useUserStore()
    const { isLogin, userInfo } = storeToRefs(userStore)
    // 全局登录卡片
    const globalStore = useGlobalStore()
    const { openLoginCard } = globalStore
    return {
      isLogin,
      userInfo,
      userTabMeth,
      userSpace,
      openLoginCard
    }
  }
})
</script>

<style lang="less" scoped>
.user {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  .unlogin {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 70px;
    height: 70px;
    border-radius: 50%;
    background-color: @bg-gray-1;
    overflow: hidden;
    cursor: pointer;
    span {
      font-weight: 700;
    }
  }
  .login {
    display: flex;
    align-items: center;
    flex-direction: column;
    width: 100%;
    height: 100%;
    &-user__avatar {
      width: 70px;
      height: 70px;
      overflow: hidden;
      border-radius: 50%;
      cursor: pointer;
      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }
    &-user__name {
      margin-top: 2px;
      height: 24px;
      font-weight: 700;
      cursor: pointer;
    }
    &-user__tab {
      display: flex;
      flex-direction: row;
      justify-content: space-evenly;
      align-items: flex-start;
      width: 50%;
      height: 36px;
      &--item {
        // 收藏图标较小
        .icon-shoucang1 {
          font-size: 18px !important;
        }

        i {
          &:hover {
            cursor: pointer;
            // color: @icon-acitve;
          }
        }
      }
    }
  }
}
</style>
