<template>
  <div id="user" class="user">
    <div class="header">
      <div class="header-bg">
        <img src="http://file.takagi-san.cn/image/f12f2c8d115245cea4878ff320f53e57.jpg" />
      </div>
    </div>
    <div class="base-info">
      <!-- 用户资料 -->
      <div class="wapper">
        <div class="avatar" @click="changeAvatar">
          <img
            src="http://file.takagi-san.cn/image/f12f2c8d115245cea4878ff320f53e57.jpg"
            class="base-img"
          />
          <p>更换头像</p>
        </div>
        <div class="info">
          <div class="name">
            <div style="position: relative">
              <b>你好</b>
              <sway-icon name="icon" :size="10" style="position: relative; top: -6px; left: 2px" />
            </div>
          </div>
          <div class="item">
            <b>Sid:123</b>
          </div>
          <div class="btn">
            <span>预设按钮</span>
          </div>
        </div>
      </div>
    </div>
    <div class="user-navigator">
      <navigator :nav-list="navList" @click="chooseItem" />
    </div>
    <div class="content">
      <router-view v-slot="{ Component }">
        <transition name="fade" appear>
          <keep-alive>
            <component :is="Component" />
          </keep-alive>
        </transition>
      </router-view>
    </div>
  </div>
  <kon-cropper
    v-if="cropper"
    src="https://sway-camp.oss-cn-qingdao.aliyuncs.com/image/avatar/006d0a5855f74f05bc77d029805dd0e3.webp"
    @current-image="currentImage"
    @close-cropper="cropper = false"
  />
  <break-top target="user" />
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue"
import { useRouter } from "vue-router"
import Navigator from "./components/Navigator.vue"
import { storeToRefs } from "pinia"
import { useUserStore } from "@/stores/user.store"
import type { NavigatorItemType } from "./types/user-nav"
import KonCropper from "@/components/cropper/KonCropper.vue"

const $router = useRouter()
const { userInfo } = storeToRefs(useUserStore())
const cropper = ref(false)
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
  const activeItem = navList.find((item, index) => {
    return index === acIndex
  })
  if (activeItem) {
    $router.push({ name: activeItem.name, params: { id: userInfo.value?.id } })
  }
}
// 更换头像
const changeAvatar = () => {
  cropper.value = true
}
// 裁切头像
const currentImage = (image: File) => {
  console.log(image)
}
</script>

<style lang="less" scoped>
.user {
  position: relative;
  width: 100%;
  height: 100%;
  user-select: none;
  overflow-x: hidden;
  overflow-y: auto;
  &-navigator {
    position: relative;
    width: 100%;
    height: 66px;
    box-sizing: border-box;
    background-color: white;
    z-index: 2;
  }
}

.header {
  position: sticky;
  top: 0;
  width: 100%;
  height: 20vh;
  overflow: hidden;
  filter: blur(4px);
  z-index: 1;
  &-bg {
    width: inherit;
    height: inherit;
    img {
      width: inherit;
      height: inherit;
      object-fit: cover;
    }
  }
}
.base-info {
  position: relative;
  top: 0px;
  width: 100%;
  height: 80px;
  z-index: 2;
  background: white;

  .wapper {
    display: grid;
    grid-template-columns: 120px 1fr;
    width: 100%;
    padding: 0 30px;
    box-sizing: border-box;

    .avatar {
      position: relative;
      top: -40px;
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100px;
      height: 100px;
      box-sizing: border-box;
      border: 2px solid white;
      border-radius: 50%;
      overflow: hidden;
      transition: all 0.35s;
      cursor: pointer;

      &::before {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        width: 100px;
        height: 100px;
        background: rgba(0, 0, 0, 0.4);
        transition: all 0.3s ease;
        opacity: 0;
        z-index: 2;
      }

      &:hover {
        transform: scale(1.1);

        &::before {
          opacity: 1;
        }

        p {
          opacity: 1;
        }
      }

      img {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: cover;
      }

      p {
        position: relative;
        line-height: 21px;
        font-size: 14px;
        color: rgb(242, 242, 242);
        z-index: 3;
        opacity: 0;
      }
    }
    .info {
      width: 100%;
      overflow: hidden;
      .name {
        margin-top: 16px;
      }

      .item {
        margin: 8px 0;
        font-size: 12px;
        color: rgba(0, 0, 0, 0.32);
      }
      .btn {
        position: absolute;
        top: 16px;
        right: 80px;

        span {
          padding: 10px 25px;
          border: none;
          border-radius: 16px;
          font-size: 12px;
          font-weight: 700;
          color: #666;
          text-align: center;
          text-decoration: none;
          background-color: #f5f5f5;
          transition: background-color 0.2s;
          cursor: pointer;
          &:hover {
            background-color: #e6e6e6;
          }
        }
      }
    }
  }
}
.content {
  position: relative;
  width: auto;
  height: fit-content;
  box-sizing: border-box;
  padding: 40px 60px 20px;
  min-height: calc(100% - 166px);
  background-color: white;
  z-index: 1;
}

.fade-enter-active {
  transition: all 0.5s ease;
}
.fade-enter-from {
  opacity: 0;
  transform: translateY(50px);
}
</style>
