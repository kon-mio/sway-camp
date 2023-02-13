<template>
  <div class="code-form-mail">
    <div class="code-form-mail__left">
      <span class="text"> 账 号 </span>
      <input
        placeholder="请输入手机号、邮箱账号"
        class="mail-input"
        type="text"
        v-model="account"
        maxlength="50"
      />
    </div>
    <div class="code-form-mail__right">
      <span class="line"></span>
      <span
        class="text"
        :class="{ 'code-disable': codeTimer === 0 && !isEmpty(account) ? false : true }"
        @click="getCode"
        >{{ codeCountdown }}</span
      >
    </div>
  </div>
  <div class="code-form-code">
    <span class="text">验证码</span>
    <input
      placeholder="请输入验证码"
      type="text"
      v-model="code"
      maxlength="6"
      @input="code = code ? code!.replace(/[^\d]/g, '') : code"
    />
  </div>
  <div class="form-btn">
    <div class="universal-btn login-btn" @click="loginSubmit">登录 / 注册</div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, reactive, ref, toRefs } from "vue"
import { codeLoginApi, getCodeApi } from "@/api/user/api"
import { useInterval } from "@/hooks/useInterval.hooks"
import SwayNotion from "@/utils/notice"
import { useGlobalStore } from "@/store/global.sotre"
import { isEmpty } from "@/utils/data/valid"
import { useUserStore } from "@/store/user.store"
import type { LoginDto } from "@/api/user/type"
import { HttpStatusCode } from "@/common/enum"
import { regexpEmail, regexpPhone } from "@/utils/data/regexp"

// 验证码相关
export function CodeMoudle() {
  const globalStore = useGlobalStore()
  const { openMessageMini } = globalStore

  const loginForm = reactive<LoginDto>({
    account: "2292289473@qq.com",
    code: ""
  })

  // 获取验证码倒计时
  const codeTimer = ref(0)
  // 获取验证码提示
  const codeCountdown = computed(() => {
    return codeTimer.value === 0 ? "获取验证码" : `${codeTimer.value}后重新获取`
  })
  const { pause, resume } = useInterval(
    () => {
      if (codeTimer.value <= 0) {
        // 停止定时任务
        pause()
      } else {
        // 单次定时任务执行的回调
        codeTimer.value--
      }
    },
    1000,
    {
      // 默认不开启定时任务
      immediate: false
    }
  )
  // 获取验证码
  const getCode = async () => {
    if (codeTimer.value != 0) {
      openMessageMini(`请${codeTimer.value}s后重新获取`)
      return
    }
    if (isEmpty(loginForm.account)) {
      openMessageMini("请输入手机/邮箱账号")
      return
    }
    if (
      !regexpEmail(loginForm.account!.toString()) &&
      !regexpPhone(loginForm.account!.toString())
    ) {
      openMessageMini("请输入正确的手机/邮箱账号")
      return
    }
    const res = await getCodeApi(loginForm.account!)
    if (res.code === HttpStatusCode.Suceess) {
      SwayNotion("验证码", "发送成功", "success")
      // 开启倒计时效果
      if (codeTimer.value === 0) {
        codeTimer.value = 60
        resume()
      }
    } else {
      SwayNotion("验证码", "发送失败", "warning")
    }
  }

  return {
    loginForm,
    codeTimer,
    codeCountdown,
    getCode
  }
}
export default defineComponent({
  name: "CodeForm",
  components: {},
  setup(props, ctx) {
    // 记录获取验证码IP 10分钟最多获取三次
    const userStore = useUserStore()
    const { openMessageMini } = useGlobalStore()
    const { loginForm, codeTimer, codeCountdown, getCode } = CodeMoudle()
    // 注册
    const loginSubmit = async () => {
      if (isEmpty(loginForm.account) || isEmpty(loginForm.code)) {
        openMessageMini("请输入账号/验证码")
        return
      }
      if (
        !regexpEmail(loginForm.account!.toString()) &&
        !regexpPhone(loginForm.account!.toString())
      ) {
        openMessageMini("请输入正确的手机/邮箱账号")
        return
      }
      // 待校验账号格式
      const { code, msg, data } = await codeLoginApi(loginForm)
      if (code === HttpStatusCode.Suceess && !isEmpty(data)) {
        userStore.setUserInfo(data)
        userStore.login()
        SwayNotion("登录", "登录成功", "success")
      } else {
        SwayNotion("登录", msg, "warning")
      }
    }
    return {
      ...toRefs(loginForm),
      codeTimer,
      codeCountdown,
      getCode,
      isEmpty,
      loginSubmit
    }
  }
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

.code-disable {
  color: @text-6 !important;
  pointer-events: none;
}
</style>
