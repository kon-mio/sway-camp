/// <reference types="vite/client" />

declare module "*.vue" {
  import type { DefineComponent } from "vue"
  // eslint-disable-next-line @typescript-eslint/ban-types
  const component: DefineComponent<{}, {}, unknown>
  export default component
}

// v-md-editor
declare module "@kangc/v-md-editor"
declare module "@kangc/v-md-editor/lib/theme/vuepress.js"
declare module "@kangc/v-md-editor/lib/preview"
declare module "@kangc/v-md-editor/lib/plugins/line-number/index"
