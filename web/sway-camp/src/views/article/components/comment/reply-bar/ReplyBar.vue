<template>
  <div class="reply-bar">
    <span v-if="!isReply" class="reply-content reply-bar">{{ info.content }}</span>
    <div class="reply-info">
      <span class="reply-time">{{ info.date }}</span>
      <span class="reply-like">
        <sway-icon name="like" :size="16" />
      </span>
      <span class="reply-btn" @click="open">回复</span>
    </div>
    <!-- 操作 -->
    <div class="reply-operation-warp" :style="operationStyle">
      <div class="reply-operation">
        <sway-icon name="qita" @click="openOptionList" />
        <ul class="operation-list" :style="optionListStyle">
          <li class="operation-option">
            <span class="option-title">删除</span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Comment, Reply } from "@/api/comment/type"
import { computed, CSSProperties, ref } from "vue"

const props = withDefaults(
  defineProps<{
    baseInfo: Comment | Reply
    isReply?: boolean
  }>(),
  { isReply: false }
)
const emits = defineEmits<{
  (el: "openReply", reply: Comment | Reply): void
  (el: "openOperatio"): void
  (el: "operationLeave"): void
}>()
const info = computed(() => {
  return props.baseInfo
})
const open = () => {
  // 区分回复评论还是回复用户
  emits("openReply", info.value)
}

// 显示操作
const showOption = ref(false)
const operationStyle = computed(() => {
  return showOption.value
    ? ({
        display: `block`
      } as CSSProperties)
    : ({
        display: `none`
      } as CSSProperties)
})
const showOptionList = ref(false)

const optionListStyle = computed(() => {
  return showOptionList.value
    ? ({
        display: `block`
      } as CSSProperties)
    : ({
        display: `none`
      } as CSSProperties)
})
// 显示操作按钮
const openOperation = () => {
  showOption.value = true
}
const closeOperation = async () => {
  showOption.value = false
  showOptionList.value = false
}
const openOptionList = () => {
  showOptionList.value = true
}
defineExpose({
  openOperation,
  closeOperation
})
</script>

<style lang="less" scoped>
.reply-bar {
  position: relative;
  padding: 2px 0;
  font-size: 14px;
  line-height: 24px;
  .reply-content {
    color: #18191c;
    overflow: hidden;
    word-wrap: break-word;
    word-break: break-word;
    white-space: pre-wrap;
    line-height: 24px;
    vertical-align: baseline;
  }
}
.reply-info {
  display: flex;
  align-items: center;
  position: relative;
  margin-top: 2px;
  font-size: 13px;
  color: #9499a0;
  .reply-time {
    margin-right: 20px;
  }
  .reply-btn {
    cursor: pointer;
    &:hover {
      color: #40c2f1;
    }
  }
  .reply-like,
  .reply-dislike {
    display: flex;
    align-items: center;
    margin-right: 19px;
    cursor: pointer;
    i {
      &:hover {
        color: #40c2f1;
      }
    }
  }
}

.reply-operation-warp {
  position: absolute;
  bottom: 10px;
  right: 20px;
  display: none;
  cursor: pointer;
  .reply-operation {
    display: inline-flex;
    position: relative;
    i:hover {
      color: #00aeec;
    }
    ul {
      padding: 0;
      margin: 0;
      list-style: none;
    }
    .operation-list {
      display: flex;
      flex-direction: column;
      position: absolute;
      top: 20px;
      right: 0;
      margin-top: 10px;
      z-index: 10;
      width: 120px;
      border-radius: 4px;
      font-size: 14px;
      color: #18191c;
      background-color: #ffffff;
      box-shadow: 0 0 5px rgb(0 0 0 / 20%);
      .operation-option {
        display: flex;
        align-items: center;
        height: 36px;
        padding: 0 15px;
        cursor: pointer;

        &:hover {
          background-color: #f1f2f3;
          color: #00aeec;
        }
      }
    }
  }
}
</style>
