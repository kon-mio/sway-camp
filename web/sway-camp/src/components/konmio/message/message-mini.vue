<template>
  <Transition name="fade">
    <div class="kon-message-mini" v-if="modelValue">
      {{ content }}
    </div>
  </Transition>
</template>

<script lang="ts" setup>
import { onMounted } from "vue"
import "./style/index.less"

const props = withDefaults(
  defineProps<{
    modelValue?: boolean
    content?: string
    displayTime?: number
  }>(),
  {
    modelValue: false,
    content: "",
    displayTime: 3000
  }
)

// const emits = defineEmits<{
//   (e: 'update:modelValue', isOpen: boolean): void
// }>()
</script>
