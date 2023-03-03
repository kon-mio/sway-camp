<template>
  <div class="about-up">
    <sway-icon :name="item.icon" :size="item.size" />
    {{ item.name }}
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from "vue"
export default defineComponent({
  name: "AboutUp",
  components: {},
  props: {
    item: {
      type: Object as PropType<{
        id: number
        name: string
        link: string
        icon: string
        size: number
      }>,
      default: () => {}
    }
  },
  setup(props) {
    return {}
  }
})
</script>

<style lang="less" scoped>
.about-up {
  box-sizing: border-box;
  display: block;
  padding: 10px 12px;
  font-size: 12px;
  color: #768791;
  transition: all 0.4s ease;
  background: #eeeeee70;
  border-radius: 6px;
  margin-bottom: 12px;
  cursor: pointer;
}
</style>
