/**请求参数 */

// 分类搜索文章
export interface SearchArticleDTO {
  index: number
  size: number
  sort: number | null
  keyword: string | null
}

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

export interface ArticleSort {
  id: number
  sortName: string
  sortDescription: string
  articleCount: number
  labels: ArticleLabel[]
}

export interface ArticleLabel {
  id: number
  sortId: number | null
  labelName: number
  articleCount: string
  labelDescription: string
}
