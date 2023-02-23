<template>
  <div class="main-center">
    <div class="main-center__article">
      <slot></slot>
    </div>
  </div>
</template>

<style lang="less" scoped>
.main-center {
  display: flex;
  flex: 1;
  max-width: 648px;
  margin: 0 32px;

  &__article {
    display: flex;
    flex-direction: column;
    width: 100%;
    box-sizing: border-box;
    padding-bottom: 20px;
  }
}
</style>
