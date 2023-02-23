/**请求参数 */

// 登录参数
export interface LoginDto {
  account?: string | number
  password?: string
  code?: string
}

// 更新用户信息参数
export interface UpdateUserInfoDto {
  username: string
  gender: number
  birthday: string
}

/**响应/接收参数 */

// 用户信息
export interface UserInfo extends TokenInfo {
  id: number
  username: string
  swayId?: number
  phoneNumber?: string
  email: string
  gender: number
  avatar: string
  birthday: string
  introduction: string
}

// 刷新token信息
export interface TokenInfo {
  accessToken: string
  refreshToken: string
}
