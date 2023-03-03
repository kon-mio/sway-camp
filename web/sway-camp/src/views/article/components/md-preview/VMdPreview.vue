<template>
  <v-md-preview ref="preview" mode="preview" :text="markdownContent" height="400px"></v-md-preview>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted } from "vue"
import type { Catalogue } from "../../type"

const props = withDefaults(
  defineProps<{
    content?: string
  }>(),
  {
    content: ""
  }
)
const emits = defineEmits<{
  (el: "getCatalogues", catalogues: Catalogue[]): void
}>()

const preview = ref()
const markdownContent = computed(() => {
  return props.content
})

// 目录
const catalogueList = ref<Catalogue[]>([])
const getCatalogues = () => {
  const anchors: HTMLElement[] = preview.value.$el.querySelectorAll("h1,h2,h3,h4,h5,h6")
  const titles = Array.from(anchors).filter((item) => !!item.innerText.trim())
  // H标签
  const hTags = Array.from(new Set(titles.map((title) => title.tagName))).sort()
  catalogueList.value = titles.map<Catalogue>((item, index) => ({
    id: index,
    title: item.innerText,
    indent: hTags.indexOf(item.tagName),
    tagName: `[data-v-md-line="${item.getAttribute("data-v-md-line")}"]`,
    active: false,
    ofTop: item.offsetTop - 10,
    clientTop: item.getBoundingClientRect().top
  }))
  emits("getCatalogues", catalogueList.value)
}
onMounted(() => {
  if (!preview.value) return
  getCatalogues()
})
</script>

<style lang="less" scoped>
::v-deep(.vuepress-markdown-body) {
  padding: 12px 0px 0px 0px;
  * {
    box-sizing: border-box;
    font-family: Consolas;
  }
  p {
    line-height: 1.8;
    font-size: 14px;
  }
  a {
    color: skyblue;
  }

  li,
  p,
  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    code {
      padding: 2px 6px;
      font-size: 14px;
      line-height: 14px;
      height: 14px;
      border: 1px solid #eee;
      border-radius: 6px;
      word-break: break-word;
      margin: 0 4px;
    }
  }
  code {
    overflow-x: auto;
    background-color: #fff5f5;
    color: #ff502c;
  }
  pre > code {
    border: none;
    background: none;
    font-size: 14px;
    // line-height: 24px;
    overflow: unset;
    color: #ffffffe6;
  }
  ul li,
  ol li {
    list-style: inherit;
    font-size: 14px;
    margin-top: 6px;
    line-height: 22px;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    position: relative;
    margin-top: 1em;
    margin-bottom: 16px;
    font-weight: 700;
    line-height: 1.4;
  }
  h2 {
    padding-bottom: 0.3em;
    font-size: 1.75em;
    line-height: 1.225;
    border-bottom: 1px solid #eee;
  }
}
</style>
