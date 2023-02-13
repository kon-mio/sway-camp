import * as request from "@/common/request"
import { CommonResult } from "@/common/request/result.type"

const newError = () => new Error("错误请求")

/**
 * 密码登录
 * @param LoginForm 登录参数
 * @returns LoginVo
 */
export async function chatgptApi(): Promise<CommonResult<null>> {
  try {
    const { data } = await request.post<null>(
      "https://api.openai.com/vl/engines/davinci/jobs",
      "你好"
    )
    return data
  } catch {
    throw newError()
  }
}
