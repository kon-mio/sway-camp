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
//  是否是客户端
export const isClient = typeof window !== "undefined"
//  是否是函数
export const isFunction = <T extends Function>(val: any): val is T => typeof val === "function"
