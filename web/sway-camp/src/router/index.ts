import { useUserStore } from "@/stores/user.store"
import { storeToRefs } from "pinia"
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router"
import routeList from "./routes/index"

const routes: RouteRecordRaw[] = [
  // 匹配不存在路由，跳转错误页面
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: () => import("@/views/error/Index.vue"),
    meta: {
      title: "404not found"
    }
  },
  // 默认路由
  {
    path: "/",
    name: "Home",
    component: () => import("@/views/home/Index.vue"),
    meta: {
      index: 0,
      title: "摇曳",
      rootRouteName: "Home",
      elName: "#sway-home"
    }
  },
  ...routeList
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

router.beforeEach(async (to, from) => {
  // 获取用户登录状态
  // ！请求报错会终止路由
  await useUserStore().refreshInfo()
  // 动态title
  if (to.meta.title) {
    document.title = to.meta.title as string
  }
  const { isLogin } = storeToRefs(useUserStore())
  if (to.meta.login) {
    return isLogin.value ? true : { name: "Home" }
  }
  return true
})
router.afterEach((to) => {
  document.title = String(to.meta.title)
})

router.onError((error, to, from) => {
  console.log(error)
  return { name: "Home" }
})

export default router
