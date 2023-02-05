const Anime = () => import('@/views/anime/Index.vue')

const commonMeta = {
  index: 2,
  rootRouteName: 'Anime'
}

export default {
  path: '/anime',
  name: 'Anime',
  component: Anime,
  meta: {
    title: '三千佳丽',
    ...commonMeta
  }
}
