import { ref } from "vue"
import { useRouter } from "vue-router"
import { useArticleStore } from "@/stores/article.store"
import cover from "@/components/article-cover/ArticleCover.vue"

export const useReadArticle = () => {
  const $router = useRouter()
  const { setCoverInfo } = useArticleStore()
  const coverTarget = ref<InstanceType<typeof cover> | null>(null)
  //   const coverTarget = ref()

  const ReadArticle = (aid: number) => {
    if (!coverTarget.value) return
    console.log(aid)
    console.log(coverTarget.value)
    const coverInfo = coverTarget.value.getCover()
    if (coverInfo) {
      setCoverInfo(coverInfo)
    }
    $router.push({
      name: "Read"
    })
  }
  return {
    coverTarget,
    ReadArticle
  }
}
