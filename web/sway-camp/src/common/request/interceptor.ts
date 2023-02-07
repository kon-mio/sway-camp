import { AxiosInstance } from 'axios'
import { ElNotification } from 'element-plus'

/**
 * axios辅助函数
 */
export default class AxiosInterceptor {
  private instance: AxiosInstance | null = null
  constructor(instance: AxiosInstance) {
    this.instance = instance
    this.response()
    this.request()
  }
  /**
   * 响应拦截器
   */
  private response() {
    if (this.instance === null) return
    this.instance.interceptors.response.use(
      (response) => {
        console.log(response)
        return response
      },
      (error) => {
        const status = error.toString()
        if (
          ['timeout ', 'Invalid URL', '401', '403', '404', 'Network Error'].some((item) =>
            status.includes(item)
          )
        ) {
          ElNotification({
            type: 'error',
            title: '请求失败',
            message: '请求失败'
          })
        }
        return Promise.reject()
      }
    )
  }
  /**
   * 请求拦截器
   */
  private request() {
    if (this.instance === null) return
    this.instance.interceptors.request.use((request) => {
      if (localStorage.getItem('abyss_token') !== null) {
        const token = 'Bearer ' + JSON.parse(localStorage.getItem('abyss_token') as string)
        if (request.headers != undefined) {
          request.headers['Authorization'] = token
        }
      }
      return request
    })
  }
}
