import { Plugin } from "vue"
import SwayIcon from "./SwayIcon.vue"
import BreakTop from "./BreakTop.vue"

// 全局组件
const components = [SwayIcon, BreakTop]

/**
 * 注册全局组件
 * @param app 根vue
 * @returns
 */
export const complInit: Plugin = {
  install(app) {
    components.forEach((comp) => {
      app.component(comp.name, comp)
    })
  }
}
