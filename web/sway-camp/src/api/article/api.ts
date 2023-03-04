import * as Type from "./type"
import * as request from "@/common/request"
import { CommonResult } from "@/common/request/result.type"

const newError = () => new Error("错误请求")

export async function saveArticleApi(ArticleForm: FormData): Promise<CommonResult<null>> {
  try {
    const { data } = await request.post<null>(`/article/upload`, ArticleForm)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 获取文章信息
 * @param {number} id 文章ID
 * @returns {Type.ArticleInfo} 文章信息
 */
export async function getArticleApi(id: number): Promise<CommonResult<Type.ArticleInfo>> {
  try {
    const { data } = await request.get<Type.ArticleInfo>(`/article/${id}`)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 分页查询文章列表
 * @param index
 * @param size
 * @returns
 */
export async function listArticleApi(
  index: number,
  size: number
): Promise<CommonResult<Type.ArticleList>> {
  try {
    const { data } = await request.get<Type.ArticleList>(
      `/article/list?index=${index}&size=${size}`
    )
    return data
  } catch {
    throw newError()
  }
}

export async function listSearchArticleApi(
  searchDTO: Type.SearchArticleDTO
): Promise<CommonResult<Type.ArticleInfo[]>> {
  try {
    const { data } = await request.post<Type.ArticleInfo[]>(`/article/list/search`, searchDTO)
    return data
  } catch {
    throw newError()
  }
}
/**
 * 查询推荐文章列表
 * @param index
 * @param size
 * @returns
 */
export async function listRecommendApi(size: number): Promise<CommonResult<Type.ArticleInfo[]>> {
  try {
    const { data } = await request.get<Type.ArticleInfo[]>(`/article/list/recommend?size=${size}`)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 文章分类列表
 * @returns
 */
export async function listSortApi(): Promise<CommonResult<Type.ArticleSort[]>> {
  try {
    const { data } = await request.get<Type.ArticleSort[]>(`/article/sort`)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 收藏文章
 * @returns
 */
export async function saveFavApi(id: number): Promise<CommonResult<null>> {
  try {
    const { data } = await request.post<null>(`/article/fav/save?articleId=${id}`)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 取消收藏文章
 * @returns
 */
export async function removeFavApi(id: number): Promise<CommonResult<null>> {
  try {
    const { data } = await request.post<null>(`/article/fav/remove?articleId=${id}`)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 收藏文章列表
 * @returns
 */
export async function listFavArticleApi(
  index: number,
  size: number
): Promise<CommonResult<Type.ArticleList>> {
  try {
    const { data } = await request.get<Type.ArticleList>(
      `/article/fav/list?index=${index}&size=${size}`
    )
    return data
  } catch {
    throw newError()
  }
}
