<template>
  <!-- 回复框 -->
  <div class="reply-box">
    <div class="box-normal" :class="{ 'box-avtive': boxActive }">
      <div class="reply-box-avatar">
        <avatar :height="40" :width="40" />
      </div>
      <!-- 发布评论 -->
      <div class="reply-box-warp">
        <textarea
          v-model="replyText"
          class="reply-box-textarea"
          :class="{ focus: isFoucs, 'send-active': sendActive }"
          placeholder="发一条友善的评论"
          @input="inputChange(replyText)"
          @focus="inputFocus"
          @blur="inputBlur"
        ></textarea>
      </div>
      <div class="reply-box-send" :class="{ 'send-btn-active': sendActive }">
        <div class="send-text">发布</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"
import { isEmpty } from "@/utils/valid"
import Avatar from "../avatar/Avatar.vue"

function replyInputModule() {
  const isFoucs = ref(false)
  const boxActive = ref(false)
  const sendActive = ref(false)

  const inputChange = (replyText: string) => {
    sendActive.value = isEmpty(replyText) ? false : true
  }
  const inputFocus = () => {
    isFoucs.value = true
    boxActive.value = true
  }
  const inputBlur = () => {
    isFoucs.value = false
    if (sendActive.value) return
    boxActive.value = false
  }
  return {
    isFoucs,
    sendActive,
    boxActive,
    inputChange,
    inputFocus,
    inputBlur
  }
}
const { isFoucs, sendActive, boxActive, inputChange, inputFocus, inputBlur } = replyInputModule()

const replyText = ref("")
</script>

<style lang="less" scoped>
.reply-box {
  display: flex;
  flex-direction: column;
  user-select: none;
  * {
    box-sizing: border-box;
  }
  .box-normal {
    display: flex;
    height: 50px;
    transition: 0.2s;
  }
  .box-avtive {
    height: 65px;
  }

  &-avatar {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80px;
    height: 50px;
  }

  &-warp {
    position: relative;
    flex: 1;
  }

  &-textarea {
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 5px 10px;
    border: 1px solid #f1f2f3;
    border-radius: 6px;
    background-color: #f1f2f3;
    font-family: inherit;
    line-height: 38px;
    color: #18191c;
    resize: none;
    outline: none;
    overflow: auto;
    &:hover {
      background-color: #ffffff;
      border-color: #c9ccd0;
    }

    &::placeholder {
      font-size: 12px;
      color: #9499a0;
    }
  }
  .focus {
    background-color: #ffffff;
    border-color: #c9ccd0;
  }
  .send-active {
    line-height: normal;
  }
  .send-btn-active {
    pointer-events: painted;
    background-color: #56d2ff;
  }

  &-send {
    //   position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    flex-basis: 70px;
    margin-left: 10px;
    border-radius: 4px;
    cursor: pointer;
    pointer-events: none;
    background-color: #80d7f6;
    .send-text {
      position: absolute;
      z-index: 1;
      font-size: 14px;
      color: white;
    }
  }
}
</style>
