import { ref } from "vue"
import { useRouter } from "vue-router"
import { useArticleStore } from "@/stores/article.store"
import cover from "@/components/article/article-cover/ArticleCover.vue"

export const useReadArticle = () => {
  const $router = useRouter()
  const { setCoverInfo } = useArticleStore()
  const coverTarget = ref<InstanceType<typeof cover> | null>(null)

  const readArticle = (aid: number) => {
    if (!coverTarget.value) return
    const coverInfo = coverTarget.value.getCover()
    if (coverInfo) {
      setCoverInfo(coverInfo)
    }
    $router.push({
      name: "Read",
      params: {
        id: aid
      }
    })
  }
  return {
    coverTarget,
    readArticle
  }
}
