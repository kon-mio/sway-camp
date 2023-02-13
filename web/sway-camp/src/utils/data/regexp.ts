/**
 * 检查邮箱账号格式
 * @param email 邮箱账号
 * @returns {boolean} 是否是邮箱账号
 */ export const regexpEmail = (email: string) => {
  const pattern = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.(com|cn|net)$/
  return pattern.test(email)
}

/**
 * 检查手机号格式
 * @param phone 手机号
 * @returns {boolean} 是否是手机号
 */
export const regexpPhone = (phone: string) => {
  const pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
  return pattern.test(phone)
}
