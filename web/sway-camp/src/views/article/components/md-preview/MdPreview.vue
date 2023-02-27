<template>
  <!-- 预览模式 -->
  <md-editor
    v-model="viewText"
    code-theme="paraiso"
    :preview-only="true"
    :show-code-row-number="true"
    :class="mdClass"
    preview-theme="default"
  />
</template>

<script lang="ts">
import { computed, defineComponent, nextTick, onMounted, ref } from "vue"
import MdEditor from "md-editor-v3"
import "md-editor-v3/lib/style.css"
export default defineComponent({
  name: "MdEditorV3",
  components: { MdEditor },
  props: {
    view: { type: Boolean, default: false },
    text: { type: String, default: "" },
    class: { type: String, default: "" }
  },
  emits: ["getCataLogue", "mdChange"],
  setup(props, ctx) {
    // 配置
    const getId = (_text: string, _level: number, index: number) => {
      return `heading-${index}`
    }
    MdEditor.config({
      markedRenderer(renderer) {
        renderer.heading = (text, level, raw, _s, index) => {
          const id = getId(text, level, index)
          return `<h${level} id="${id}">${text}</h${level}>`
        }

        return renderer
      },
      editorExtensions: {
        highlight: {
          css: {
            atom: {
              light:
                "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.5.1/styles/atom-one-light.min.css",
              dark: "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.5.1/styles/atom-one-dark.min.css"
            }
          }
        }
      }
    })
    // md
    const mdText = ref("")
    const textChange = (md: string) => {
      ctx.emit("mdChange", md)
    }
    // props
    const viewText = computed(() => {
      return props.text
    })
    const mdClass = computed(() => {
      return props.class
    })
    //获取目录
    const getCataLogue = () => {
      const anchors = document.querySelectorAll("h1,h2,h3,h4,h5,h6")
      const titles = Array.from(anchors).filter(
        (title) => !!(title as HTMLElement).innerText.trim()
      ) as HTMLElement[]
      const hTags = Array.from(
        new Set(titles.map((title) => (title as HTMLElement).tagName))
      ).sort()
      const cataLogue: any[] = titles.map((item) => ({
        title: item.innerText,
        id: item.getAttribute("id"),
        tagName: hTags.indexOf(item.tagName),
        active: false,
        ofTop: item.offsetTop + 86,
        clientTop: item.getBoundingClientRect().top
      }))
      cataLogue.shift()
      ctx.emit("getCataLogue", cataLogue)
    }
    onMounted(() => {
      nextTick(() => {
        getCataLogue()
      })
    })
    return {
      mdText,
      viewText,
      mdClass,
      getCataLogue,
      textChange
    }
  }
})
</script>

<style lang="less" scoped>
::v-deep(pre::before) {
  content: "";
  display: block;
  background: url("");
  height: 4px;
  width: 100%;
  background-size: 40px;
  background-repeat: no-repeat;
  background-color: #282c34;
  margin-bottom: 0;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  background-position: 10px 10px;
}
</style>
