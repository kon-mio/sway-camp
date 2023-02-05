<template>
  <i class="iconfont" :class="IconFont" :style="{ color: color, fontSize: size + 'px' }">
    <slot></slot>
  </i>
</template>

<script lang="ts">
import { computed, defineComponent } from 'vue'
export default defineComponent({
  name: 'SwayIcon',
  components: {},
  props: {
    name: {
      type: String,
      default: ''
    },
    color: {
      type: String,
      default: ''
    },
    hcolor: {
      type: String,
      default: ''
    },
    size: {
      type: Number,
      default: 14
    }
  },
  setup(props) {
    const IconFont = computed(() => `icon-${props.name}`)
    return {
      IconFont
    }
  }
})
</script>
