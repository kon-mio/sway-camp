<template>
  <div class="avatar" :style="{ width: width + 'px', height: height + 'px' }">
    <img class="avatar-img avatar-face avatar-img-radius" :src="avatar" />
    <div class="avatar-pendent-dom">
      <img class="avatar-img" :src="pendent" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
export default defineComponent({
  name: "ReplyAvatar",
  props: {
    width: {
      type: Number,
      default: 0
    },
    height: {
      type: Number,
      default: 0
    },
    avatar: {
      type: String,
      default: "http://file.konmio.com/image/6dd64ae8db1143758587df1fdabe25a4.webp"
    },
    pendent: {
      type: String,
      default: "http://file.konmio.com/image/9812ffc1edd542b9ae7401664612b5d0.webp"
    }
  }
})
</script>

<style lang="less" scoped>
.avatar {
  display: block;
  position: relative;
  background-size: cover;
  border-radius: 50%;
  user-select: none;
  &-img-radius {
    border-radius: 50%;
  }
  &-img {
    border: none;
    display: block;
    -o-object-fit: cover;
    object-fit: cover;
    image-rendering: -webkit-optimize-contrast;
  }
  &-face {
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    -moz-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    -o-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    width: 100%;
    height: 100%;
  }

  &-pendent-dom {
    height: 176.48%;
    width: 176.48%;
    position: absolute;
    top: -38.33%;
    left: -38.33%;
    overflow: hidden;
    img {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
