import { RouteRecordRaw } from "vue-router"

import animeRoute from "./anime.route"
import articleRoute from "./article.route"
import userRoute from "./user.route"

const routeList: RouteRecordRaw[] = [animeRoute, userRoute, articleRoute]

export default routeList
