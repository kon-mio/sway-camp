<template>
  <div class="editor">
    <v-md-editor v-model="text" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
export default defineComponent({
  name: "MdEditor",
  setup() {
    return {
      text: ""
    }
  }
})
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
