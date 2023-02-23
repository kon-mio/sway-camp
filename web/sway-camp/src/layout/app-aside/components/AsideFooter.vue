<template>
  <div>
    <a href="https://beian.miit.gov.cn/" target="_blank">@鲁ICP备2021043087号-2</a>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
export default defineComponent({
  name: "AsideFooter",
  components: {},
  setup() {
    return {}
  }
})
</script>

<style lang="less" scoped>
a {
  position: absolute;
  bottom: 0px;
  font-size: 13px;
  left: 16px;
  color: black;
}
</style>
