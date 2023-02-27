import { Plugin } from "vue"
import VMdPreview from "@kangc/v-md-editor/lib/preview"
import "@kangc/v-md-editor/lib/style/preview.css"
import vuepressTheme from "@kangc/v-md-editor/lib/theme/vuepress.js"
import "@kangc/v-md-editor/lib/theme/style/vuepress.css"

import Prism from "prismjs"
import createLineNumbertPlugin from "@kangc/v-md-editor/lib/plugins/line-number/index"

VMdPreview.use(vuepressTheme, {
  Prism
}).use(createLineNumbertPlugin())

export const mdPreview: Plugin = {
  install(app) {
    app.use(VMdPreview)
  }
}
