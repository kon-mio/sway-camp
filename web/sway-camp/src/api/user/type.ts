/**请求参数 */

// 登录参数
export interface LoginDto {
  account?: string | number
  password?: string
  code?: string
}

/**响应/接收参数 */

// 用户信息
export interface UserInfo extends TokenInfo {
  id: number
  username: string
  sid?: number
  phoneNumber?: string
  email: string
  gender: string
  avatar: string
  introduction: string
}

// 刷新token信息
export interface TokenInfo {
  accessToken: string
  refreshToken: string
}
