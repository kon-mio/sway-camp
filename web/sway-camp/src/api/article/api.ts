import * as Type from "./type"
import * as request from "@/common/request"
import { CommonResult } from "@/common/request/result.type"

const newError = () => new Error("错误请求")

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