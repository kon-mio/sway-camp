<template>
  <div class="main-left">
    <div class="main-left__about">
      <slot name="website"></slot>
    </div>
    <div class="main-left__anime">
      <slot name="anime"></slot>
    </div>
    <div class="main-left__article">
      <slot name="article"></slot>
    </div>
  </div>
</template>

<style lang="less" scoped>
.main-left {
  position: sticky;
  top: 10px;
  width: 260px;
  height: 80vh;
  &__about {
    margin-bottom: 12px;
  }
  &__anime {
    margin-bottom: 12px;
  }
}
</style>
