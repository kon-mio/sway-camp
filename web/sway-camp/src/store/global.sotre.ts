import { defineStore } from 'pinia'

export const useGlobalStore = defineStore('global', {
  state: () => ({
    // 全局message-mini通知
    messageMini: {
      isOpen: false,
      content: ''
    },
    loginCard: false
  }),
  actions: {
    // 打开mini消息框
    openMessageMini(content: string) {
      this.messageMini.isOpen = true
      this.messageMini.content = content
      // 2s后自动关闭
      setTimeout(() => {
        this.messageMini.isOpen = false
        this.messageMini.content = ''
      }, 1600)
    },

    /**
     * 登录卡片控制
     * @param loginCardOpen true/false
     * @param isLogin 登录状态
     * @returns 
     */
    controlLoginCard(loginCardOpen: boolean, isLogin: boolean) {
      if (isLogin) return
      this.loginCard = loginCardOpen
    }
  }
})
