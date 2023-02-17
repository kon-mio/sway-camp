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
      title: "深渊制造",
      rootRouteName: "Home",
      elName: "#abyss-home"
    }
  },
  ...routeList
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

router.beforeEach(async (to, from, next) => {
  // 获取用户登录状态
  await useUserStore().refreshInfo()
  // 动态title
  if (to.meta.title) {
    document.title = to.meta.title as string
  }
  const { isLogin } = storeToRefs(useUserStore())
  if (to.meta.login) {
    isLogin.value ? next() : next({ name: "Home" })
  } else {
    next()
  }
})
router.afterEach((to) => {
  document.title = String(to.meta.title)
})

export default router
