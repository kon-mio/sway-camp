const Article = () => import("@/views/article/Index.vue")

const Library = () => import("@/views/article/routes/library/Index.vue")
const Write = () => import("@/views/article/routes/write/Index.vue")
const Read = () => import("@/views/article/routes/read/Index.vue")

const commonMeta = {
  index: 1,
  rootRouteName: "Article"
}

export default {
  path: "/article",
  component: Article,
  meta: {
    title: "各务原抚子",
    ...commonMeta
  },
  children: [
    {
      path: "",
      name: "Article",
      component: Library,
      meta: {
        title: "各务原抚子",
        ...commonMeta
      }
    },
    {
      path: "write",
      name: "Write",
      component: Write,
      meta: {
        title: "各务原抚子",
        ...commonMeta
      }
    },
    {
      path: "read/:id",
      name: "Read",
      component: Read,
      props: true,
      meta: {
        title: "各务原抚子",
        ...commonMeta
      }
    }
  ]
}
