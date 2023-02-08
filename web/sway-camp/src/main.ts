import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./store"

import { complInit } from "@/components/global"
import { elInit } from "@/common/plugin/ElementPlus.plugin"

const app = createApp(App)

elInit(app)
complInit(app)
app.use(store)
app.use(router)
app.mount("#app")
