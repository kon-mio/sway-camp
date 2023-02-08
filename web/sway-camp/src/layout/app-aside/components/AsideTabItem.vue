<template>
  <div
    class="tab-item"
    :class="{ active: ($route.meta.rootRouteName as String) === tabItem.rootRouteName }"
    @click="$router.push({ name: tabItem.name })"
  >
    <div class="tab-item__title">
      {{ tabItem.title }}
    </div>
    <div class="tab-item__icon">
      <sway-icon :name="tabItem.icon" :size="tabItem.size ? tabItem.size : 32" color="#e6e6e6" />
    </div>
    <div class="tab-item__point">
      <div class="point"></div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from "vue"
import { asideTabItemType } from "../type"
export default defineComponent({
  name: "AsideTabItem",
  props: {
    tabItem: {
      type: Object as PropType<asideTabItemType>,
      default: () => {}
    }
  },
  setup() {
    return {}
  }
})
</script>

<style lang="less" scoped>
.tab-item {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 80px;

  &:hover,
  &.active {
    cursor: pointer;

    .tab-item__title {
      opacity: 1;
      transform: translateY(-8px);
    }

    .tab-item__point {
      opacity: 1;
      transform: translateY(10px);
    }

    .tab-item__icon {
      opacity: 0;
    }
  }

  &__icon {
    opacity: 1;
    transition: all 0.3s ease-in-out;
  }

  &__title {
    position: absolute;
    font-size: 18px;
    font-weight: 700;
    opacity: 0;
    transform: translateY(-24px);
    transition: all 0.3s ease-in-out;
  }

  &__point {
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    opacity: 0;
    transform: translateY(24px);
    transition: all 0.3s ease-in-out;

    .point {
      width: 5px;
      height: 5px;
      background-color: @text-1;
      border-radius: 50%;
    }
  }
}
</style>
