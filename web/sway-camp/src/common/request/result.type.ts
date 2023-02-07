export interface CommonResult<T> {
  timestamp: number
  code: number
  msg: string
  data: T
}
