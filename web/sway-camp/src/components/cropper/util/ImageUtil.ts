/**
 * 获取base64格式图片
 * @param img 图片
 */
export const getBase64Image = (img: HTMLImageElement): string => {
  const canvas = document.createElement("canvas")
  const width = img.naturalWidth || img.width
  const height = img.naturalHeight || img.height
  canvas.width = width
  canvas.height = height
  const ctx = canvas.getContext("2d")
  ctx?.drawImage(img, 0, 0, width, height) // 绘制图像
  const ext = img.src.substring(img.src.lastIndexOf(".") + 1).toLowerCase() // 图片格式
  const base64Image = canvas.toDataURL("image/" + ext) // 包含图片展示的 data URI
  return base64Image
}

/**
 * 根据Url加载图片
 * @param url 图片地址
 * @param callback 图片加载完成回调
 */
export const loadImage = (url: string, callback: (image: HTMLImageElement) => void) => {
  const newImage = new Image()
  newImage.src = url + "?v=" + Math.random()
  newImage.crossOrigin = "*"
  newImage.onload = () => {
    callback(newImage)
    return
  }
  // 加载失败图片
  const errorImage = new Image()
  errorImage.src = "http://file.takagi-san.cn/image/kon_all_3.webp"
  errorImage.crossOrigin = "*"
  newImage.onerror = () => {
    callback(errorImage)
  }
}

// base64 to File
export function base64ToFile(data: string) {
  const fileName = "avatar"
  const dataArr = data.split(",")
  const byteString = atob(dataArr[1])
  // ts 使用buffer 浏览器不能识别 是node.js
  // const byteString = Buffer.from(dataArr[1],'base64')
  const options = {
    type: "image/webp",
    endings: "native"
  }
  const u8Arr = new Uint8Array(byteString.length)
  for (let i = 0; i < byteString.length; i++) {
    u8Arr[i] = byteString.charCodeAt(i)
  }
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-ignore
  return new File([u8Arr], fileName + ".webp", options)
}
