import { defineStore } from "pinia"
import type { UserInfo } from "@/api/user/type"
import { storage } from "@/utils/storage"
import { getUserInfoApi } from "@/api/user/api"
import { HttpStatusCode } from "@/common/enum"

export const useUserStore = defineStore("user", {
  state: () => ({
    isLogin: false,
    userInfo: null as UserInfo | null
  }),
  actions: {
    // 刷新用户信息
    async refreshInfo() {
      if (!storage.get("access_token")) return
      const res = await getUserInfoApi()
      if (res.code === HttpStatusCode.Success) {
        this.login(res.data, true)
      }
    },
    // 登录
    login(userInfo: UserInfo, refresh = false) {
      this.isLogin = true
      this.userInfo = userInfo
      // 刷新信息不修改token
      if (refresh) return
      storage.set("access_token", userInfo.accessToken)
      storage.set("refresh_token", userInfo.refreshToken)
    },
    // 退出
    exit() {
      this.isLogin = false
      this.userInfo = null
      storage.remove("access_token")
      storage.remove("refresh_token")
    }
  }
})
