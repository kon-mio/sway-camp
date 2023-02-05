import piniaPlugin from './piniaMain'
import { createPinia } from 'pinia'

const store = createPinia().use(
  piniaPlugin({
    key: 'sway', //存储的前缀key
    paths: ['user'] //存储state中的那些数据
  })
)

export default store
