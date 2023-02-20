import { refreshTokenApi } from "@/api/user/api"
import SwayNotion from "@/utils/notice"
import { storage } from "@/utils/storage"
import { AxiosInstance } from "axios"
import { HttpStatusCode } from "../enum"

/**
 * axios辅助函数
 */
export default class AxiosInterceptor {
  // 请求实例
  private instance: AxiosInstance | null = null
  // 构造器
  constructor(instance: AxiosInstance) {
    this.instance = instance
    this.response()
    this.request()
  }

  /**
   * 请求拦截器
   */
  private request() {
    if (this.instance === null) return
    this.instance.interceptors.request.use((config) => {
      console.log(config.url)
      // 存在请求token
      if (storage.get("access_token")) {
        // 刷新token
        if (config.url === "/user/refresh") {
          if (!storage.get("refresh_token")) return config
          const token = "Bearer " + storage.get("refresh_token")
          config.headers["Authorization"] = token
        } else {
          const token = "Bearer " + storage.get("access_token")
          config.headers["Authorization"] = token
        }
      }
      // 请求时没有请求token但有刷新token时，先获取请求token
      if (!storage.get("access_token") && storage.get("refresh_token")) {
        return new Promise((resolve, _reject) => {
          refreshTokenApi().then((res) => {
            if (res.code === 200) {
              storage.set("access_token", res.data.accessToken)
              storage.set("refresh_token", res.data.refreshToken)
              config.headers["Authorization"] = res.data.accessToken
              resolve(config)
            } else {
              storage.remove("refresh_token")
              location.reload()
              SwayNotion("登录", "请重新登录", "warning")
            }
          })
        })
      }
      return config
    })
  }
  /**
   * 响应拦截器
   */
  private response() {
    if (this.instance === null) return
    this.instance.interceptors.response.use(
      (response) => {
        // 请求token过期
        if (
          response.data.code === HttpStatusCode.Forbidden ||
          response.data.code === HttpStatusCode.Unauthorized
        ) {
          if (storage.get("refresh_token")) {
            return new Promise((resolve, reject) => {
              refreshTokenApi().then((res) => {
                if (res.code === 200) {
                  storage.set("access_token", res.data.accessToken)
                  storage.set("refresh_token", res.data.refreshToken)
                  response.config.headers["Authorization"] = "Bearer " + res.data.accessToken
                  resolve(this.instance!(response.config))
                } else {
                  storage.remove("access_token")
                  storage.remove("refresh_token")
                  location.reload()
                  SwayNotion("登录", "请重新登录", "warning")
                }
              })
            })
          }
          else{
            return response
          }
        } else if (response.data.code === HttpStatusCode.Unauthorized) {
          storage.remove("access_token")
          storage.remove("refresh_token")
          SwayNotion("登录", "请重新登录", "warning")
        }
        return response
      },
      (error) => {
        return Promise.reject(error)
      }
    )
  }
}
