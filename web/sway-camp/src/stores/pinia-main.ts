import type { PiniaPluginContext } from "pinia"
import { toRaw } from "vue"

const __piniaKey = "__PINIAKEY__"
type OptPinia = {
  key?: string
  paths?: string[]
}
//取值
const getStorage = (key: string) => {
  return localStorage.getItem(key) ? JSON.parse(localStorage.getItem(key) as string) : {}
}
//存储
const setStorage = (key: string, value: any): void => {
  localStorage.setItem(key, JSON.stringify(value))
}
const piniaPlugin = (options: OptPinia) => {
  return (context: PiniaPluginContext) => {
    const { store } = context
    // ?? 二选其一
    const data = getStorage(`${options?.key ?? __piniaKey}_${store.$id}`)
    store.$subscribe((args, state) => {
      let arrPaths = options.paths
      if (!arrPaths?.includes(args.storeId)) return
      setStorage(`${options?.key ?? __piniaKey}_${store.$id}`, toRaw(store.$state))
    })
    return {
      ...store.$state,
      ...data
    }
  }
}
export default piniaPlugin
