<template>
  <!-- 回复框 -->
  <div class="reply-box">
    <div class="box-normal" :class="{ 'box-avtive': boxActive }">
      <div class="reply-box-avatar">
        <avatar :height="30" :width="30" />
      </div>
      <!-- 发布评论 -->
      <div class="reply-box-warp">
        <textarea
          v-model="replyText"
          class="reply-box-textarea"
          :class="{ focus: isFoucs, 'send-active': sendActive }"
          :placeholder="placeholder"
          @input="inputChange(replyText)"
          @focus="inputFocus"
          @blur="inputBlur"
        ></textarea>
      </div>
      <div class="reply-box-send" :class="{ 'send-btn-active': sendActive }" @click="submitReply">
        <div class="send-text">发布</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, ref } from "vue"
import { isEmpty } from "@/utils/valid"
import Avatar from "../avatar/Avatar.vue"
// 动画相关
function replyInputModule() {
  const replyText = ref("")
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
  const boxInit = () => {
    replyText.value = ""
    isFoucs.value = false
    boxActive.value = false
    sendActive.value = false
  }
  return {
    isFoucs,
    replyText,
    boxActive,
    sendActive,
    boxInit,
    inputBlur,
    inputFocus,
    inputChange
  }
}
const props = withDefaults(
  defineProps<{
    placeholder?: string
  }>(),
  { placeholder: "发一条友善的评论" }
)
const emits = defineEmits<{
  (e: "submit", replyText: string): void
}>()

const { replyText, isFoucs, sendActive, boxActive, boxInit, inputBlur, inputFocus, inputChange } =
  replyInputModule()
const placeholder = computed(() => {
  return props.placeholder
})

// 点击事件应该放在外层，而不是内层文字
const submitReply = () => {
  emits("submit", replyText.value)
}

defineExpose({
  boxInit
})
</script>

<style lang="less" scoped>
.reply-box {
  display: flex;
  flex-direction: column;
  user-select: none;
  .box-normal {
    display: flex;
    height: 50px;
    transition: 0.2s;
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

    textarea {
      width: 100%;
      height: 100%;
      margin: 0;
      padding: 5px 10px;
      box-sizing: border-box;
      font-family: inherit;
      line-height: 38px;
      color: #18191c;
      border-radius: 6px;
      border: 1px solid #f1f2f3;
      background-color: #f1f2f3;
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
  }

  &-send {
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
      font-size: 14px;
      color: white;
    }
  }

  .focus {
    background-color: #ffffff;
    border-color: #c9ccd0;
  }
  .box-avtive {
    height: 65px;
  }
  .send-active {
    line-height: normal;
  }
  .send-btn-active {
    pointer-events: painted;
    background-color: #56d2ff;
  }
}
</style>
