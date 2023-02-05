// 用户tab
export interface userTabItemType {
  id: number
  icon: string
  title: string
  func?: string
  size?: number
}
// 侧边路由列表
export interface asideTabItemType {
  id: number
  name: string
  title: string
  icon: string
  rootRouteName: string
  size?: number
}
