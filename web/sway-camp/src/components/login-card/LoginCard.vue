<template>
  <Transition name="login">
    <div class="login-card" v-if="loginCard && !isLogin">
      <div class="login-card-mark" @click="closeLoginCard" />
      <div class="login-card-container">
        <div class="login-card-close" @click="closeLoginCard">
          <sway-icon name="guanbi" />
        </div>
        <div class="login-card-inner">
          <div class="login-card-inner--left">
            <img src="@/assets/img/xj_3.jpg" />
          </div>
          <div class="login-card-inner--line"></div>
          <!-- 登录表单 -->
          <div class="login-card-inner--right">
            <!-- 登录选项 -->
            <div class="login-form__tab">
              <span
                class="login-form__tab--password"
                :style="{ color: !codeLogin ? '#4fa5d9' : '' }"
                @click="changeLoginType(false)"
              >
                密码登录
              </span>
              <span class="login-form__tab--line"> </span>
              <span
                class="login-form__tab--email"
                :style="{ color: codeLogin ? '#4fa5d9' : '' }"
                @click="changeLoginType(true)"
              >
                邮箱登录
              </span>
            </div>
            <!-- 密码登录 -->
            <div class="login-form">
              <div class="login-form__password" v-show="!codeLogin">
                <password-form @regist="changeLoginType" @login-success="loginSuccess" />
              </div>
              <div class="login-form__email" v-show="codeLogin">
                <code-form @login-success="loginSuccess" />
              </div>
            </div>
            <!-- 第三方登录 -->
            <div class="other-login-wrapper">
              <div class="title">其他方式登录</div>
              <div class="sns">
                <span class="btn qq">
                  <sway-icon name="QQ" :size="28" color="#00aeec" />
                  QQ登录
                </span>
                <span class="btn weibo">
                  <sway-icon name="github-fill" :size="28" />
                  GitHub
                </span>
                <span class="btn wechat">
                  <sway-icon name="weixin" :size="28" color="#50b674" />
                  微信登录
                </span>
              </div>
            </div>
          </div>
        </div>
        <div class="login-card-agreement">
          <div class="login-card-agreement--content">
            <p>未注册过的邮箱，将自动帮你注册账号</p>
          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script lang="ts">
import { UserInfo } from "@/api/user/type"
import { useGlobalStore } from "@/stores/global.sotre"
import { useUserStore } from "@/stores/user.store"
import SwayNotion from "@/utils/notice"
import { storeToRefs } from "pinia"
import { defineComponent, ref } from "vue"
import CodeForm from "./components/CodeForm.vue"
import PasswordForm from "./components/PasswordForm.vue"
export default defineComponent({
  name: "LoginCard",
  components: { PasswordForm, CodeForm },
  setup() {
    // 登录方式 只能是密码或邮箱
    const userStore = useUserStore()
    const codeLogin = ref<boolean>(false)
    const changeLoginType = (type: boolean) => {
      codeLogin.value = type
    }
    // 全局登录状态
    const { isLogin } = storeToRefs(useUserStore())
    // 全局登录卡片
    const globalStore = useGlobalStore()
    const { loginCard } = storeToRefs(globalStore)
    const { closeLoginCard } = globalStore
    const loginSuccess = (userInfo: UserInfo) => {
      userStore.setUserInfo(userInfo)
      userStore.login()
      closeLoginCard()
      SwayNotion("登录", "登录成功", "success")
    }
    return {
      isLogin,
      codeLogin,
      loginCard,
      changeLoginType,
      closeLoginCard,
      loginSuccess
    }
  }
})
</script>

<style lang="less" scoped>
.login-card {
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  min-width: @min-width;
  height: 100%;
  min-height: @min-height;
  z-index: 2000;

  &-mark {
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: @mark-black-1;
    opacity: 0.2;
  }
  &-close {
    position: absolute;
    top: 16px;
    right: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 30px;
    height: 30px;
    cursor: pointer;
  }
  &-container {
    position: relative;
    width: 800px;
    height: 420px;
    background-color: @bg-white-0;
    border-radius: 16px;
  }
  &-inner {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    padding: 50px 40px 30px;
    &--left {
      position: relative;
      width: 200px;
      height: 100%;
      img {
        position: absolute;
        width: 100%;
        height: 100%;
        object-fit: contain;
      }
    }
    &--line {
      width: 1px;
      height: 80%;
      margin: 0 45px;
      background-color: @line-gray-3;
    }
    &--right {
      box-sizing: border-box;
      padding: 20px 0;
      flex: 1;
      height: 100%;
      .login-form {
        &__tab {
          display: flex;
          justify-content: center;
          height: 20px;
          color: @text-gray-7;

          &--password,
          &--email {
            font-size: 18px;
            cursor: pointer;
          }

          &--line {
            width: 1px;
            height: 20px;
            background-color: @line-gray-2;
            margin: 0 21px;
          }
        }
        &__password,
        &__email {
          width: 100%;
          margin-top: 20px;
        }
      }

      .other-login-wrapper {
        margin-top: 24px;
        user-select: none;
        .title {
          font-size: 13px;
          line-height: 16px;
          text-align: center;
          color: @text-black-0;
          margin-bottom: 12px;
        }
        .sns {
          display: flex;
          justify-content: center;

          .btn {
            margin-right: 30px;
            cursor: pointer;
          }

          .qq,
          .wechat,
          .weibo {
            font-size: 12px;
            display: flex;
            align-items: center;
            i {
              margin-right: 8px;
            }
          }
        }
      }
    }
  }
  &-agreement {
    position: absolute;
    bottom: 30px;
    left: 280px;
    font-size: 13px;
    color: @text-gray-4;
    margin-top: 40px;
    &--content {
      user-select: none;
      p {
        text-align: center;
        line-height: 20px;
      }
    }
  }
}
.login-enter-active,
.login-leave-active {
  transition: opacity 0.5s ease;
}

.login-enter-from,
.login-leave-to {
  opacity: 0;
}

input {
  &::placeholder {
    font-size: 13px;
  }
}
</style>
