<template>
  <div class="editor">
    <v-md-editor v-model="content" @change="contentChange" />
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"

const content = ref("")
const emits = defineEmits<{
  (el: "getContent", content: string): void
}>()

const contentChange = (text: string) => {
  emits("getContent", text)
}
</script>

<style lang="less" scoped>
.editor {
  position: relative;
  width: 100%;
  max-width: 100%;
  height: 100%;
  overflow: hidden;
  ::v-deep(.v-md-editor) {
    height: calc(100vh - 70px);
    .scrollbar__view {
      .v-md-textarea-editor {
        height: 100% !important;
      }
    }
  }
}
</style>
