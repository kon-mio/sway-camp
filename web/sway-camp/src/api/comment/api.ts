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

/**
 * 发布评论回复
 * @param {Type.ReplyDTO} comment 评论信息
 * @returns null
 */
export async function uploadReplyApi(reply: Type.ReplyDTO): Promise<CommonResult<null>> {
  try {
    const { data } = await request.post<null>(`/comment/reply/upload`, reply)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 分页查询评论
 * @param index 页码
 * @param size 分页大小
 * @returns 评论列表
 */
export async function listCommentApi(
  index: number,
  size: number,
  articleId: number
): Promise<CommonResult<Type.CommentPage>> {
  try {
    const { data } = await request.get<Type.CommentPage>(
      `/comment/list?index=${index}&size=${size}&articleId=${articleId}`
    )
    return data
  } catch {
    throw newError()
  }
}

/**
 * 分页查询评论回复
 * @param index 页码
 * @param size 分页大小
 * @returns 评论列表
 */
export async function listReplyApi(
  index: number,
  size: number,
  commentId: number
): Promise<CommonResult<Type.ReplyPage>> {
  try {
    const { data } = await request.get<Type.ReplyPage>(
      `/comment/reply/list?index=${index}&size=${size}&commentId=${commentId}`
    )
    return data
  } catch {
    throw newError()
  }
}
