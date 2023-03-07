<template>
  <router-view v-slot="{ Component }">
    <keep-alive>
      <component :is="Component" v-if="$route.meta.elName" :key="$route.name" />
    </keep-alive>
    <component :is="Component" v-if="!$route.meta.elName" :key="$route.name" />
  </router-view>
</template>
