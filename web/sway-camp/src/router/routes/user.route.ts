import { RouteRecordRaw } from "vue-router"

const User = () => import("@/views/user/Index.vue")
// children
const UserHome = () => import("@/views/user/routes/home/Index.vue")
const UserInfo = () => import("@/views/user/routes/info/Index.vue")
const UserFav = () => import("@/views/user/routes/fav/Index.vue")
const UserSetting = () => import("@/views/user/routes/setting/Index.vue")
const UserArticle = () => import("@/views/user/routes/article/Index.vue")

const commonMeta = {
  index: -1.5,
  rootRouteName: "User"
}

export default {
  path: "/user/:id",
  component: User,
  meta: {
    login: true,
    ...commonMeta
  },
  children: [
    {
      path: "",
      name: "User",
      component: UserHome,
      meta: {
        title: "用户主页",
        ...commonMeta
      }
    },
    {
      path: "info",
      name: "UserInfo",
      component: UserInfo,
      meta: {
        title: "信息",
        ...commonMeta
      }
    },
    {
      path: "fav",
      name: "UserFav",
      component: UserFav,
      meta: {
        title: "收藏",
        ...commonMeta
      }
    },
    {
      path: "article",
      name: "UserArticle",
      component: UserArticle,
      meta: {
        title: "文章",
        ...commonMeta
      }
    },
    {
      path: "setting",
      name: "UserAccount",
      component: UserSetting,
      meta: {
        title: "账号设置",
        ...commonMeta
      }
    }
  ]
} as RouteRecordRaw
