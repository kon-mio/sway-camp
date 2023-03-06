/**请求参数 */

/**响应/接收参数 */

// 动漫信息
export interface Anime {
  id: number
  name: string
  originalName: string
  cover: string
  region: string
  introduction: string
  broadcastTime: string
  officialWebsite: string
  labels: string[]
  image: AnimeImage[]
}
// 动漫图片
export interface AnimeImage {
  animeId: number
  width: string
  height: string
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
