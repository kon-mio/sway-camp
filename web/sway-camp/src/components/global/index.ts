import { App } from "vue"
import SwayIcon from "./SwayIcon.vue"

// 全局组件
const components = [SwayIcon]

/**
 * 注册全局组件
 * @param app 根vue
 * @returns
 */
export const complInit = (app: App<Element>) => {
  components.forEach((comp) => {
    app.component(comp.name, comp)
  })
}
