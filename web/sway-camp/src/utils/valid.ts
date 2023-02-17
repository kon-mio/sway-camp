//  是否是客户端
export const isClient = typeof window !== "undefined"
//  是否是函数
export const isFunction = <T extends Function>(val: any): val is T => typeof val === "function"

/**
 * 判断变量是否为空
 * @param {unknown} value 判断对象
 * @returns {boolean} null -> true
 */
export const isEmpty = (value: unknown): boolean => {
  if (value === null || value === undefined) {
    return true
  }
  // 判断字符串是否为空
  if (typeof value === "string" && value.trim() == "") {
    return true
  }
  if (typeof value === "object" && Object.keys(value).length === 0) {
    return false
  }
  return false
}

/**
 * 检查邮箱账号格式
 * @param email 邮箱账号
 * @returns {boolean} 是否是邮箱账号
 */ export const isEmail = (email: string) => {
  const pattern = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.(com|cn|net)$/
  return pattern.test(email)
}

/**
 * 检查手机号格式
 * @param phone 手机号
 * @returns {boolean} 是否是手机号
 */
export const isPhone = (phone: string) => {
  const pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
  return pattern.test(phone)
}
