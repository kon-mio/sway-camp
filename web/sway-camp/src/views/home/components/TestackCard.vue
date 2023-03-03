<template>
  <li class="point-tag">
    <i :style="{ background: color }"></i>
    <span>{{ name }}</span>
  </li>
</template>

<script lang="ts">
import { defineComponent } from "vue"
export default defineComponent({
  name: "TestackCard",
  components: {},
  props: {
    name: {
      type: String,
      default: ""
    },
    color: {
      type: String,
      default: ""
    }
  },
  setup(_props) {
    return {}
  }
})
</script>

<style lang="less" scoped>
.point-tag {
  display: inline-flex;
  align-items: center;
  border-radius: 4px;
  border: 1px solid #eee;
  padding: 6px 8px;
  font-size: 14px;
  margin-right: 6px;
  margin-bottom: 8px;
  user-select: none;
  i {
    width: 12px;
    height: 12px;
    border-radius: 50%;
  }
  span {
    margin-left: 8px;
    color: #717171;
  }
}
</style>
