import { App } from 'vue'
import {
  ElNotification,
  ElCarousel,
  ElCarouselItem,
  ElButton,
  ElRadioGroup,
  ElRadioButton,
  ElDatePicker,
  ElDialog,
  ElLoading,
  ElPopconfirm,
  ElRate,
  ElInput,
  ElTooltip,
  ElSelect,
  ElOption,
  ElPagination,
  ElPopover
} from 'element-plus'

const comps = [
  ElNotification,
  ElCarousel,
  ElCarouselItem,
  ElButton,
  ElRadioGroup,
  ElRadioButton,
  ElDatePicker,
  ElDialog,
  ElPopconfirm,
  ElRate,
  ElInput,
  ElTooltip,
  ElSelect,
  ElOption,
  ElPagination,
  ElPopover
]

const plugins = [ElNotification, ElLoading]
export function elInit(app: App<Element>) {
  comps.forEach((comp) => {
    app.component(comp.name, comp)
  })
  plugins.forEach((plugin) => {
    app.use(plugin)
  })
}
