<template>
  <div class="main-right">
    <div class="main-right-about">
      <slot name="about"></slot>
    </div>
    <div class="main-right-find">
      <slot name="find"></slot>
    </div>
    <div class="main-right-testack">
      <slot name="testack"></slot>
    </div>
    <div class="main-right-friendship">
      <slot name="friendship"></slot>
    </div>
  </div>
</template>

<style lang="less" scoped>
.main-right {
  position: sticky;
  top: -20vh;
  display: flex;
  flex-direction: column;
  width: 280px;
  height: fit-content;
  &-find {
    margin-top: 12px;
  }
  &-testack {
    margin-top: 12px;
  }
  &-friendship {
    margin-top: 12px;
    color: rgb(255, 133, 154);
  }
}
</style>
