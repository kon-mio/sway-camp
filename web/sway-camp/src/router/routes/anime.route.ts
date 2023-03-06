const Anime = () => import("@/views/anime/Index.vue")

const AnimeHome = () => import("@/views/anime/routes/home/Index.vue")

const commonMeta = {
  index: 2,
  rootRouteName: "Anime"
}

export default {
  path: "/anime",
  component: Anime,
  meta: {
    title: "三千佳丽",
    ...commonMeta
  },
  children: [
    {
      path: "",
      name: "Anime",
      component: AnimeHome,
      meta: {
        title: "三千佳丽",
        ...commonMeta
      }
    }
  ]
}
