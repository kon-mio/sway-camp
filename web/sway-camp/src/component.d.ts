import SwayIcon from "@components/global/SwayIcon.vue"
import BreakTop from ".@components/global/BreakTop.vue"
// volar全局组件提示
declare module "@vue/runtime-core" {
  export interface GlobalComponents {
    SwayIcon: typeof SwayIcon
    BreakTop: typeof BreakTop
  }
}
