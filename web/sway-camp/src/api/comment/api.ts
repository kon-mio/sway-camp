import * as Type from "./type"
import * as request from "@/common/request"
import { CommonResult } from "@/common/request/result.type"

const newError = () => new Error("错误请求")

/**
 * 发布评论
 * @param {Type.CommentDTO} comment 评论信息
 * @returns null
 */
export async function uploadCommentApi(comment: Type.CommentDTO): Promise<CommonResult<null>> {
  try {
    const { data } = await request.post<null>(`/comment/upload`, comment)
    return data
  } catch {
    throw newError()
  }
}
