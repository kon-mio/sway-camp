<template>
  <div class="main-right-uper">
    <div class="main-right-uper__cover">
      <img
        src="https://sway-camp.oss-cn-qingdao.aliyuncs.com/image/cover/540083f441c14c8e839dd6b70a831eaa.webp"
      />
    </div>
    <div class="main-right-uper__avatar">
      <img
        src="https://sway-camp.oss-cn-qingdao.aliyuncs.com/image/cover/39179cb2593f4186a40a03f29df7c1b0.webp"
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
