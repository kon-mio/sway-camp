<template>
  <div class="sway-anime">
    <!-- <app-router /> -->
    <sway-router />
  </div>
</template>

<script lang="ts" setup>
import AppRouter from "@/layout/app-router/AppRouter.vue"
import SwayRouter from "@/layout/app-router/SwayRouter.vue"
</script>

<style lang="less" scoped>
.sway-anime {
  position: relative;
  width: 100%;
  height: 100%;
}
</style>
