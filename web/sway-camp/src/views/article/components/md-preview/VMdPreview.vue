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
    ofTop: item.offsetTop + 86,
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
  padding: 0px;
}
</style>
