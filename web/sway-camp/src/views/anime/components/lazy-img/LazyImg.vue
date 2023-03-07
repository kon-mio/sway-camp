<template>
  <div ref="lazyImg" style="height: 100%">
    <img v-if="load" :src="src" class="scale-up-top" />
  </div>
</template>

<script lang="ts" setup>
import { onMounted, computed, ref } from "vue"
import { elVisibleObserver } from "@/utils/dom"

const props = defineProps<{
  src: string
}>()
const src = computed(() => {
  return props.src
})

const load = ref(false)
const lazyImg = ref<HTMLDivElement | null>(null)
onMounted(() => {
  if (!lazyImg.value) return
  elVisibleObserver(lazyImg.value, () => {
    load.value = true
  })
})
</script>
<style lang="less" scoped>
img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.scale-up-top {
  animation: scale-up-top 0.5s;
}

@keyframes scale-up-top {
  0% {
    transform: scale(0.5);
    transform-origin: 50% 0%;
  }

  100% {
    transform: scale(1);
    transform-origin: 50% 0%;
  }
}

// .base-img {}
</style>
