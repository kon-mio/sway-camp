<template>
  <v-md-preview mode="preview" :text="content" height="400px"></v-md-preview>
</template>

<script lang="ts" setup>
const props = withDefaults(
  defineProps<{
    content?: string
  }>(),
  {
    content: ""
  }
)
</script>
