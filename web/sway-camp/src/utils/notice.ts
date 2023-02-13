import { ElNotification } from "element-plus"
import type { EpPropMergeType } from "element-plus/es/utils"

const SwayNotion = (
  title: string,
  msg: string,
  type:
    | EpPropMergeType<StringConstructor, "" | "success" | "warning" | "info" | "error", unknown>
    | undefined
) => {
  ElNotification.closeAll()
  ElNotification({
    title: title,
    message: msg,
    type: type
  })
}

export default SwayNotion



