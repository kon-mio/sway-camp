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
export default router
