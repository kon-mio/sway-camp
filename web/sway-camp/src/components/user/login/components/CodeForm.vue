<template>
  <div class="code-form-mail">
    <div class="code-form-mail__left">
      <span class="text"> 账 号 </span>
      <input placeholder="请输入手机号、邮箱账号" class="mail-input" type="email" />
    </div>
    <div class="code-form-mail__right">
      <span class="line"></span>
      <span class="text">获取验证码</span>
    </div>
  </div>
  <div class="code-form-code">
    <span class="text">验证码</span>
    <input placeholder="请输入验证码" type="text" />
  </div>
  <div class="form-btn">
    <div class="universal-btn login-btn">登录 / 注册</div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
export default defineComponent({
  name: "CodeForm",
  components: {}
})
</script>

<style lang="less" scoped>
@import "../style/common.less";

.code-form-mail,
.code-form-code {
  display: flex;
  padding: 0 20px;
  align-items: center;
  height: 43px;
  border: 1px solid @line-2;
  input {
    margin-top: 3px;
    width: 170px;
    outline: none;
    border: none;
    font-size: 14px;
    color: @text-3;
  }

  .text {
    font-size: 14px;
    color: @text-3;
  }
}
.code-form-mail {
  justify-content: space-between;
  border-radius: 8px 8px 0 0;
  height: 43px;
  border-bottom: none;

  &__left {
    .text {
      margin-right: 28px;
    }
  }

  &__right {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .line {
      display: inline-block;
      width: 1px;
      height: 26px;
      background: @line-1;
      margin-right: 24px;
    }
    .text {
      user-select: none;
      color: @text-4;
      cursor: pointer;
    }
  }
}
.code-form-code {
  border-radius: 0 0 8px 8px;
  .text {
    margin-right: 22px;
  }
}
</style>
