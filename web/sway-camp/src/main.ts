import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./stores"

import { complInit } from "@/components/global"
import { elInit } from "@/common/plugin/element-plus.plugin"

const app = createApp(App)

elInit(app)
complInit(app)
app.use(store)
app.use(router)
app.mount("#app")
