import { RouteRecordRaw } from "vue-router"

const User = () => import("@/views/user/Index.vue")
// children
const UserHome = () => import("@/views/user/routes/user-home/Index.vue")
const UserInfo = () => import("@/views/user/routes/user-info/Index.vue")
const UserFav = () => import("@/views/user/routes/user-fav/Index.vue")

const commonMeta = {
  index: -1.5,
  rootRouteName: "User"
}

export default {
  path: "/user/:id",
  component: User,
  meta: {
    title: "三千佳丽",
    ...commonMeta
  },
  children: [
    {
      path: "",
      name: "User",
      component: UserHome,
      meta: {
        title: "三千佳丽",
        ...commonMeta
      }
    },
    {
      path: "info",
      name: "UserInfo",
      component: UserInfo,
      meta: {
        title: "三千佳丽",
        ...commonMeta
      }
    },
    {
      path: "fav",
      name: "UserFav",
      component: UserFav,
      meta: {
        title: "三千佳丽",
        ...commonMeta
      }
    }
  ]
} as RouteRecordRaw
