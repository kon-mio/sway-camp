const User = () => import("@/views/user/Index.vue")

const commonMeta = {
  index: -1,
  rootRouteName: "User"
}

export default {
  path: "/User",
  name: "User",
  component: User,
  meta: {
    title: "三千佳丽",
    ...commonMeta
  }
}
