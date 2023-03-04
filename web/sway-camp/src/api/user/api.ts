import * as Type from "./type"
import * as request from "@/common/request"
import { CommonResult } from "@/common/request/result.type"

const newError = () => new Error("错误请求")

/**
 * 密码登录
 * @param {Type.LoginDto} LoginForm 登录参数
 * @returns {Type.UserInfo} 用户信息
 */
export async function loginApi(LoginForm: Type.LoginDto): Promise<CommonResult<Type.UserInfo>> {
  try {
    const { data } = await request.post<Type.UserInfo>("/user/login", LoginForm)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 验证码登录
 * @param {Type.LoginDto} LoginForm 登录参数
 * @returns {Type.UserInfo} 用户信息
 */
export async function codeLoginApi(LoginForm: Type.LoginDto): Promise<CommonResult<Type.UserInfo>> {
  try {
    const { data } = await request.post<Type.UserInfo>("/user/register", LoginForm)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 获取验证码
 * @param {string | number} account 邮箱/手机号
 * @returns {void} void
 */
export async function getCodeApi(account: string | number): Promise<CommonResult<null>> {
  try {
    const { data } = await request.post<null>("/user/code", { account: account })
    return data
  } catch {
    throw newError()
  }
}

/**
 * 获取用户信息
 * @returns {Type.UserInfo} 用户信息
 */
export async function getUserInfoApi(): Promise<CommonResult<Type.UserInfo>> {
  try {
    const { data } = await request.get<Type.UserInfo>("/user/info")
    return data
  } catch {
    throw newError()
  }
}

/**
 * 更新用户头像
 * @param {FormData} FormData 头像文件表单
 * @returns {null} null
 */
export async function updateAvatarApi(FormData: FormData): Promise<CommonResult<null>> {
  try {
    const { data } = await request.post<null>("/user/avatar/update", FormData)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 更新用户信息
 * @param {Type.UpdateUserInfoDto} info 用户信息
 * @returns {null} null
 */
export async function updateUserInfoApi(info: Type.UpdateUserInfoDto): Promise<CommonResult<null>> {
  try {
    const { data } = await request.post<null>("/user/info/update", info)
    return data
  } catch {
    throw newError()
  }
}

/**
 * 刷新token
 * @returns {Type.TokenInfo} 新token
 */
export async function refreshTokenApi(): Promise<CommonResult<Type.TokenInfo>> {
  try {
    const { data } = await request.get<Type.TokenInfo>("/user/refresh")
    return data
  } catch {
    throw newError()
  }
}
