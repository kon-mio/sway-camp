import { defineStore } from "pinia"
import type { UserInfo } from "@/api/user/type"

export const useUserStore = defineStore("user", {
  state: () => ({
    isLogin: false,
    userInfo: null as UserInfo | null
  }),
  actions: {
    // 编辑用户信息
    setUserInfo(userInfo: UserInfo) {
      this.userInfo = userInfo
    },
    // 登录
    login() {
      this.isLogin = true
    },
    // 退出
    exit() {
      this.isLogin = false
      this.userInfo = null
    }
  }
})
