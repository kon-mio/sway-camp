import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./stores"

import { mdPreview } from "@/common/plugin/v-md-preview.plugin"
import { mdEditor } from "@/common/plugin/v-md-editor.plugin"
import { complInit } from "@/components/global"
import { elInit } from "@/common/plugin/element-plus.plugin"
import { createPreloadCdn } from "@/common/class/PreloadCdn.class"

const app = createApp(App)

app.use(createPreloadCdn)
app.use(mdEditor)
app.use(mdPreview)
app.use(complInit)
app.use(elInit)
app.use(store)
app.use(router)
app.mount("#app")
