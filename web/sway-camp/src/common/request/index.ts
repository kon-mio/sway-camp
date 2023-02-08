import axios from "axios"
import { globalConfig as config } from "@/config/global.config"
import AxiosInterceptor from "./Interceptor"
import { CommonResult } from "./result.type"

// 创建公共实例
const instance = axios.create({
  baseURL: config.server.ip + config.server.commonChart,
  timeout: config.server.timeout
})
new AxiosInterceptor(instance)

/**
 * 请求 - get
 * @param url 请求地址
 * @returns
 */
export function get<T>(url: string) {
  return instance.get<CommonResult<T>>(url)
}

/**
 * 请求 - post
 * @param url 请求地址
 * @param params 请求参数
 * @param config 请求配置
 * @returns
 */
export function post<T>(url: string, params = {}, config = {}) {
  return instance.post<CommonResult<T>>(url, params, config)
}
