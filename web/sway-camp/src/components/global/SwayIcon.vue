<template>
  <i class="iconfont" :class="iconFont" :style="iconStyle">
    <slot></slot>
  </i>
</template>

<script lang="ts">
import { computed, CSSProperties, defineComponent } from "vue"
export default defineComponent({
  name: "SwayIcon",
  props: {
    name: {
      type: String,
      default: ""
    },
    color: {
      type: String,
      default: ""
    },
    hcolor: {
      type: String,
      default: ""
    },
    size: {
      type: Number,
      default: 14
    }
  },
  setup(props) {
    const iconFont = computed(() => `icon-${props.name}`)

    const iconStyle = computed<CSSProperties>(() => {
      const style: CSSProperties =
        props.size === 14
          ? {
              color: props.color
            }
          : {
              color: props.color,
              fontSize: props.size + "px"
            }

      return style
    })
    return {
      iconFont,
      iconStyle
    }
  }
})
</script>
