<template>
  <!-- 密码登录 -->
  <div class="form-account">
    <span class="text">账号</span>
    <input type="text" placeholder="请输入邮箱账号" />
  </div>
  <!-- 密码 -->
  <div class="form-password">
    <div class="left">
      <span class="text">密码</span>
      <input placeholder="请输入密码" minlength="6" maxlength="18" type="text" />
    </div>
    <!-- 显示密码 -->
    <div class="eye-btn">
      <sway-icon name="yanjing1" color="--thememini-color: #00a1d6" />
      <sway-icon name="yanjing" :size="20" color="--thememini-color: #00a1d6" />
    </div>
    <span class="forget"> 忘记密码？ </span>
  </div>
  <!-- 按钮 -->
  <div class="form-btn">
    <div class="universal-btn register-btn">注册</div>
    <div class="universal-btn login-btn" @click="loginSumbit">登录</div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, toRefs } from 'vue'
import { useGlobalStore } from '@/store/global.sotre'
export default defineComponent({
  name: 'LoginForm',
  setup() {
    const globalStore = useGlobalStore()
    const loginSumbit = () => {
      globalStore.openMessageMini('请输入账号密码')
    }

    return {
      loginSumbit
    }
  }
})
</script>

<style lang="less" scoped>
@import '../style/common.less';
@input-padding:0 20px;

.form-account,
.form-password {
  display: flex;
  align-items: center;
  padding: @input-padding;
  height: 43px;
  border: 1px solid @line-2;
  input {
    width: 230px;
    outline: none;
    border: none;
    font-size: 14px;
    margin-top: 2px;
    color: @text-3;
  }
  .text {
    margin-right: 20px;
    font-size: 14px;
    color: @text-3;
  }
}
.form-account {
  border-bottom: none;
  border-radius: 8px 8px 0 0;
}
.form-password {
  position: relative;
  justify-content: space-between;
  border-radius: 0 0 8px 8px;

  .eye-btn {
    position: relative;
    cursor: pointer;
    width: 20px;
    height: 20px;
  }
  .forget {
    position: relative;
    font-size: 13px;
    color: @text-4;
    cursor: pointer;
  }
}
</style>
