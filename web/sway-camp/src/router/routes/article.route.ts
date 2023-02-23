const Article = () => import("@/views/article/Index.vue")

const commonMeta = {
  index: 1,
  rootRouteName: "Article"
}

export default {
  path: "/article",
  name: "Article",
  component: Article,
  meta: {
    title: "三千佳丽",
    ...commonMeta
  }
}
