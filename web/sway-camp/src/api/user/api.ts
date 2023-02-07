import * as Type from './type'
import * as request from '@/common/request'
import { CommonResult } from '@/common/request/result.type'

const newError = () => new Error('错误请求')

/**
 * 密码登录
 * @param LoginForm 登录参数
 * @returns LoginVo
 */
export async function loginApi(LoginForm: Type.LoginDto): Promise<CommonResult<Type.UserInfo>> {
  try {
    const { data } = await request.post<Type.UserInfo>('/user/login', LoginForm)
    return data
  } catch {
    throw newError()
  }
}
