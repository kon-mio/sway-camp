import * as Type from "./type"
import * as request from "@/common/request"
import { CommonResult } from "@/common/request/result.type"

const newError = () => new Error("文章相关请求错误")

/**
 * 获取推荐动漫
 * @returns
 */
export async function listRecommendAnimeApi(): Promise<CommonResult<Type.Anime>> {
  try {
    const { data } = await request.get<Type.Anime>(`/anime/recommend/list`)
    return data
  } catch {
    throw newError()
  }
}
