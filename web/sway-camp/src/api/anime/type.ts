/**请求参数 */

/**响应/接收参数 */

export interface Anime {
  id: number
  name: string
  otherName: string
  region: string
  airTime: string
  introduction: string
  web: string
  cover: string
}

// 推荐动漫信息
export interface RecommendAnime {
  list: Anime
  action: {
    id: number
    translateX: number
    animation: boolean
    active: boolean
  }
}
