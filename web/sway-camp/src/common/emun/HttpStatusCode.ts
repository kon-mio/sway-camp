export enum HttpStatusCode {
  Suceess = 200,
  // 资源已被移除
  MovedPrem = 301,
  // 参数列表错误（缺少，格式不匹配）
  BadRequest = 400,
  // 未授权
  Unauthorized = 401,
  // 访问受限，授权过期
  Forbidden = 403,
  // 资源，服务未找到
  NotFound = 404,
  // 不支持的数据，媒体类型
  UnsupportedType = 415,
  // 系统内部错误
  Error = 500,
  // 接口未实现
  NotImplemented = 501,
  // 系统警告消息
  Warn = 601

  //   SUCCESS = 200,
  //   // 资源已被移除
  //   MOVED_PERM = 301,
  //   // 参数列表错误（缺少，格式不匹配）
  //   BAD_REQUEST = 400,
  //   // 未授权
  //   UNAUTHORIZED = 401,
  //   // 访问受限，授权过期
  //   FORBIDDEN = 403,
  //   // 资源，服务未找到
  //   NOT_FOUND = 404,
  //   // 不支持的数据，媒体类型
  //   UNSUPPORTED_TYPE = 415,
  //   // 系统内部错误
  //   ERROR = 500,
  //   // 接口未实现
  //   NOT_IMPLEMENTED = 501,
  //   // 系统警告消息
  //   WARN = 601
}
