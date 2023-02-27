<template>
  <div class="title-box">
    <div v-if="titleType === 0" class="title-box__title">
      <div class="title">
        <b>{{ title }}</b>
      </div>
    </div>
    <div v-else class="title-box__title">
      <div class="title type">
        <b>{{ title }}</b>
      </div>
    </div>
    <div class="title-box__content">
      <slot></slot>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
export default defineComponent({
  name: "TitleBox",
  props: {
    title: {
      type: String,
      default: ""
    },
    titleType: {
      type: Number,
      default: 0
    }
  },
  setup() {
    return {}
  }
})
</script>

<style lang="less" scoped>
.title-box {
  position: relative;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: auto;
  box-sizing: border-box;
  padding: 16px;
  border-radius: 12px;
  background-color: white;
  overflow: hidden;

  &__title {
    width: 100%;
    height: 30px;
    border-bottom: 1px solid #dedede;
    .title {
      position: relative;
      width: fit-content;
      height: 100%;
      b {
        color: #595959;
        &::before {
          content: "";
          position: absolute;
          bottom: 0px;
          width: 100%;
          height: 4px;
          border-radius: 6px;
          background-color: #ff9090;
        }
      }
    }
    .type {
      b {
        position: relative;
        left: 10px;
        top: 2px;
        font-size: 14px;
        color: #595959;
        &::before {
          content: "";
          position: absolute;
          left: -10px;
          width: 4px;
          height: 100%;
          border-radius: 6px;
          background-color: #ff9090;
        }
      }
    }
  }

  &__content {
    width: 100%;
    flex: 1;
    box-sizing: border-box;
    padding: 12px 2px 0px 2px;
  }
}
</style>
