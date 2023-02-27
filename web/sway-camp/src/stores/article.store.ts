import { defineStore } from "pinia"
import type { ArticleCover } from "@/components/article-cover/type"

export const useArticleStore = defineStore("article", {
  state: () => ({
    coverInfo: null as ArticleCover | null
  }),
  actions: {
    setCoverInfo(info: ArticleCover) {
      this.coverInfo = info
    }
  }
})
