import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./stores"

import { complInit } from "@/components/global"
import { elInit } from "@/common/plugin/element-plus.plugin"

const app = createApp(App)

app.use(complInit)
app.use(elInit)
app.use(store)
app.use(router)
app.mount("#app")
