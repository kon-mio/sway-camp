<template>
  <div ref="recomRef" class="recom-article" :style="itemStyle" @click="readArticle(article.id)">
    <div class="recom-article__cover">
      <ArticleCover ref="coverTarget" :src="article.cover" />
    </div>
    <div class="recom-article__info">
      <div class="name" :title="article.title">{{ article.title }}</div>
      <div class="time">{{ article.viewCount }}</div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, CSSProperties, defineComponent, onMounted, PropType, ref } from "vue"
import { elVisibleObserver } from "@/utils/dom"
import ArticleCover from "@/components/article/article-cover/ArticleCover.vue"
import { useReadArticle } from "@/hooks/useReadArticle.hooks"
import { ArticleInfo } from "@/api/article/type"
export default defineComponent({
  name: "RecommendArticleCard",
  components: { ArticleCover },
  props: {
    article: {
      type: Object as PropType<ArticleInfo>,
      default: () => {}
    },
    articleIndex: {
      type: Number,
      default: 0
    }
  },
  setup(props) {
    const recomRef = ref<HTMLDivElement>()
    const translateX = ref("100%")
    const opacity = ref(0)
    const addAnime = () => {
      translateX.value = "0px"
      opacity.value = 1
    }
    const itemStyle = computed(() => {
      return {
        opacity: opacity.value,
        transform: `translateX(${translateX.value})`,
        transition: `all 0.25s ease ${0.25 * props.articleIndex}s`
      } as CSSProperties
    })
    const { readArticle, coverTarget } = useReadArticle()
    onMounted(() => {
      if (!recomRef.value) return
      elVisibleObserver(recomRef.value, addAnime)
    })
    return {
      recomRef,
      itemStyle,
      readArticle,
      coverTarget
    }
  }
})
</script>

<style lang="less" scoped>
.recom-article {
  position: relative;
  display: flex;
  width: 100%;
  padding: 6px 8px;
  box-sizing: border-box;
  cursor: pointer;

  &:hover {
    .recom-article__cover {
      img {
        transform: translate(-6px, -6px);
      }
    }
  }

  &__cover {
    width: 50px;
    height: 50px;
    margin-right: 10px;
    img {
      border-radius: 6px;
      transition: all 0.25s ease-in-out;
    }
  }

  &__info {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    position: relative;
    font-size: 12px;
    .name {
      display: -webkit-box !important;
      -webkit-box-orient: vertical;
      text-overflow: ellipsis;
      -webkit-line-clamp: 1;
      overflow: hidden;
      width: 100%;
      box-sizing: border-box;
      padding-right: 4px;
      margin-bottom: 6px;
      color: #768791;
      font-size: 11px;
    }
    .time {
      width: 100%;
      overflow: hidden;
      color: #c3ccd3;
    }
  }
}
</style>
