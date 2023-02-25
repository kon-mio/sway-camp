<template>
  <div class="article-search">
    <div class="test" @click="ReadArticle(1)"><article-cover ref="coverTarget" /></div>
  </div>
</template>

<script lang="ts" setup>
import ArticleCover from "@/components/article-cover/ArticleCover.vue"
import { useReadArticle } from "@/hooks/useReadArticle.hooks"

const { ReadArticle, coverTarget } = useReadArticle()
</script>

<style lang="less" scoped>
.article-search {
  position: relative;
  width: 100%;
  height: 100%;
  .test {
    position: relative;
    top: 200px;
    left: 200px;
    width: 200px;
    height: 200px;
  }
}
</style>
