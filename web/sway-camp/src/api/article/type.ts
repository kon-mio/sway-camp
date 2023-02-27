/**请求参数 */

/**响应/接收参数 */

// 文章信息
export interface ArticleInfo {
  id: number
  userId: number
  username: string
  sort: string
  label: string
  cover: string
  title: string
  introduction: string
  viewCount: number
  likeCount: number
  createTime: string
  updateTime: string
  content: string | null
  reprinted: string | null
}

export interface ArticleList {
  list: ArticleInfo[]
  total: number
}
