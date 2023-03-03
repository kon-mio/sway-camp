<template>
  <div class="main-right-uper">
    <div class="main-right-uper__cover">
      <img
        src="http://file.konmio.com/image/0b3ba832bbe74a6cbd359b6976fa49a7.webp"
        class="base-img"
      />
    </div>
    <div class="main-right-uper__avatar">
      <img
        src="http://file.konmio.com/image/7b0963d1e6c94a29a76a6eeec4702b1d.webp"
        class="base-img"
      />
    </div>
  </div>
</template>

<style lang="less" scoped>
.main-right-uper {
  position: relative;
  width: 100%;
  height: 200px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 1px 0 6px #0000000a;
  background-color: #fff;
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  &__cover {
    width: 100%;
    height: 120px;
  }
  &__avatar {
    position: absolute;
    top: 86px;
    right: 20px;
    width: 70px;
    height: 70px;
    border: 3px solid rgba(255, 255, 255, 0.4);
    border-radius: 50%;
    overflow: hidden;
  }
}
</style>
