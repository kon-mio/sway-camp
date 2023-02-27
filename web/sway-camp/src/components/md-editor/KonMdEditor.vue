<template>
  <div class="kon-md-preview">
    <main class="kon-md-preview__main">
      <div v-html="markdownHtml" class="konmio-md-style"></div>
    </main>
  </div>
</template>

<script lang="ts" setup>
import MarkdownIt from "markdown-it"
import Prism from "prismjs"
import { onMounted, ref } from "vue"

const props = withDefaults(
  defineProps<{
    markdown: string
  }>(),
  {}
)

const markdownHtml = ref("")

const md: MarkdownIt = new MarkdownIt({
  html: false, // 在源码中启用 HTML 标签
  xhtmlOut: false, // 使用 '/' 来闭合单标签 （比如 <br />）。
  // 这个选项只对完全的 CommonMark 模式兼容。
  breaks: false, // 转换段落里的 '\n' 到 <br>。
  langPrefix: "language-", // 给围栏代码块的 CSS 语言前缀。对于额外的高亮代码非常有用。
  linkify: false, // 将类似 URL 的文本自动转换为链接。
  // 启用一些语言中立的替换 + 引号美化
  typographer: false,
  // 高亮函数，会返回转义的HTML。
  // 或 '' 如果源字符串未更改，则应在外部进行转义。
  // 如果结果以 <pre ... 开头，内部包装器则会跳过。
  highlight: function (str, lang) {
    if (lang) {
      try {
        const highlightCode =
          `<code class="language-${lang}">` +
          Prism.highlight(str, Prism.languages[lang], lang) +
          `</code>`
        const wrapper = `<pre class="line-numbers language-${lang}" data-ext="${lang}">${highlightCode}</pre>`
        return wrapper
      } catch (__) {}
    }
    let dlang = "html"
    const highlightCode =
      `<code class="language-${dlang}">` +
      Prism.highlight(str, Prism.languages[dlang], dlang) +
      `</code>`
    const wrapper = `<pre class="line-numbers language-${dlang}" data-ext="${dlang}">${highlightCode}</pre>`
    return wrapper
  }
})

/**
 * md -> html
 * @param markdown markdown格式文档
 */
const mdRender = (markdown: string) => {
  return md.render(markdown)
}
onMounted(() => {
  if (props.markdown) {
    markdownHtml.value = mdRender(props.markdown)
    setTimeout(() => {
      Prism.highlightAll()
    }, 0)
  }
})
</script>

<style lang="less">
@import "./style/code.less";
@import "./style/md.less";
</style>

<style lang="less" scoped>
.kon-md-preview {
  width: 100%;
  box-sizing: border-box;
  &__main {
    width: 100%;
    box-sizing: border-box;
    padding: 0 80px 50px 80px;
  }
}
</style>
