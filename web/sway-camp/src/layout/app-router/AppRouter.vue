<template>
  <router-view v-slot="{ Component }">
    <keep-alive>
      <component :is="Component" v-if="$route.meta.elName" :key="$route.fullPath" />
    </keep-alive>
    <!-- 绑定Key 区别路由解决同一路由不同参数不跳转问题 -->
    <component :is="Component" v-if="!$route.meta.elName" :key="$route.fullPath" />
  </router-view>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({
  name: 'AppRouter'
})
</script>
