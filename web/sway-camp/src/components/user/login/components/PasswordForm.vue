<template>
  <!-- 密码登录 -->
  <div class="form-account">
    <span class="text">账号</span>
    <input type="text" placeholder="请输入手机号、邮箱账号" v-model="account" />
  </div>
  <!-- 密码 -->
  <div class="form-password">
    <div class="left">
      <span class="text">密码</span>
      <input
        placeholder="请输入密码"
        minlength="6"
        maxlength="18"
        :type="passInputType"
        v-model="password"
      />
    </div>
    <!-- 显示密码 -->
    <div class="eye-btn">
      <sway-icon
        v-if="passInputType === 'password'"
        @click="passInputType = 'text'"
        :size="18"
        name="yanjing1"
        color="#61666d"
      />
      <sway-icon
        v-if="passInputType === 'text'"
        @click="passInputType = 'password'"
        :size="20"
        name="yanjing"
        color="#61666d"
      />
    </div>
    <span class="forget" @click="register"> 忘记密码 </span>
  </div>
  <!-- 按钮 -->
  <div class="form-btn">
    <div class="universal-btn register-btn" @click="register">注册</div>
    <div class="universal-btn login-btn" @click="loginSumbit">登录</div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref, toRefs } from "vue"
import { useGlobalStore } from "@/store/global.sotre"
import { loginApi } from "@/api/user/api"
import type { LoginDto } from "@/api/user/type"
import SwayNotion from "@/utils/notice"
import { useUserStore } from "@/store/user.store"
import { isEmpty } from "@/utils/data/valid"
import type { LoginType } from "../type"
export default defineComponent({
  name: "LoginForm",
  emits: {
    regist(type: LoginType) {
      // 通过返回TRUE
      return true
    }
  },
  setup(props, ctx) {
    const userStore = useUserStore()
    const globalStore = useGlobalStore()
    const passInputType = ref("password")

    const loginForm = reactive<LoginDto>({
      account: "",
      password: ""
    })
    // 登录
    const loginSumbit = async () => {
      if (isEmpty(loginForm.account) || isEmpty(loginForm.password)) {
        globalStore.openMessageMini("请输入账号密码")
        return
      }
      const res = await loginApi(loginForm)
      if (res.code === 200) {
        userStore.setUserInfo(res.data)
        userStore.login()
        SwayNotion("登录", "登录成功", "success")
      } else {
        SwayNotion("登录", res.msg, "warning")
      }
    }
    // 切换注册页面
    const register = () => {
      ctx.emit("regist", "code")
    }

    return {
      ...toRefs(loginForm),
      passInputType,
      loginSumbit,
      register
    }
  }
})
</script>

<style lang="less" scoped>
@import "../style/common.less";
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
  &:hover {
    .eye-btn {
      opacity: 1;
    }
  }

  .eye-btn {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    padding-top: 2px;
    width: 20px;
    height: 20px;
    opacity: 0;
    transition: opacity 0.25s ease-in-out;
    cursor: pointer;
  }
  .forget {
    position: relative;
    font-size: 13px;
    color: @text-4;
    cursor: pointer;
  }
}
</style>
