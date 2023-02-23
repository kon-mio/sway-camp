<template>
  <div class="info">
    <em>点击相应信息进行修改</em>
    <button class="info-btn-sub" @click="updateUserInfo">提交修改</button>
    <!-- 账户信息 -->
    <h3>账号信息 <span>*不可修改</span></h3>
    <div class="info-group">
      <b>权限</b>
      <div class="info-group-msg">{{ infoList.id === 1 ? "用户" : "管理员" }}</div>
    </div>
    <div class="info-group">
      <b>账号</b>
      <div class="info-group-msg">{{ infoList.swayId }}</div>
    </div>
    <div class="info-group">
      <b>邮箱</b>
      <div class="info-group-msg">{{ infoList.email }}</div>
    </div>
    <div class="info-group">
      <b>注册时间</b>
      <div class="info-group-msg">{{ infoList.birthday }}</div>
    </div>
    <!-- 用户信息 -->
    <h3>基本信息</h3>
    <div class="info-group base-info">
      <b>昵称</b>
      <div class="check-input">
        <input
          v-model="infoList.username"
          type="text"
          style="width: 200px"
          placeholder="请输入昵称"
        />
        <p></p>
      </div>
    </div>
    <div class="info-group base-info">
      <b>性别</b>
      <div style="position: relative; bottom: 4px">
        <el-radio-group v-model="infoList.gender" size="large">
          <el-radio-button :label="2">女</el-radio-button>
          <el-radio-button :label="1">男</el-radio-button>
          <el-radio-button :label="0">保密</el-radio-button>
        </el-radio-group>
      </div>
    </div>
    <div class="info-group base-info">
      <b>生日</b>
      <div style="position: relative; bottom: 4px">
        <el-date-picker
          v-model="infoList.birthday"
          value-format="YYYY-MM-DD"
          type="date"
          placeholder="Pick a day"
          size="large"
        />
      </div>
    </div>
    <!-- 联系方式 -->
    <h3>联系方法 <span>*暂不可用</span></h3>
    <div class="info-group">
      <b>手机号</b>
      <div v-show="false">
        <input disabled type="text" />
      </div>
    </div>
    <div class="info-group">
      <b>QQ</b>
      <div v-show="false">
        <input disabled type="text" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, onMounted } from "vue"
import { storeToRefs } from "pinia"
import { useUserStore } from "@/stores/user.store"
import { UpdateUserInfoDto, UserInfo } from "@/api/user/type"
import { updateUserInfoApi } from "@/api/user/api"
import SwayNotion from "@/utils/notice"
import { HttpStatusCode } from "@/common/enum"

const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)
//用户信息展示
const infoList = reactive<UserInfo>({
  id: 0,
  username: "",
  email: "",
  gender: 0,
  avatar: "",
  birthday: "",
  introduction: "",
  accessToken: "",
  refreshToken: ""
})
// 更新用户信息参数
const updateInfo = reactive<UpdateUserInfoDto>({
  username: "",
  gender: 0,
  birthday: ""
})
const updateUserInfo = async () => {
  updateInfo.username = infoList.username
  updateInfo.gender = infoList.gender
  updateInfo.birthday = infoList.birthday + " 00:00:00"

  const res = await updateUserInfoApi(updateInfo)
  if (res.code === HttpStatusCode.Success) {
    userStore.refreshInfo()
    SwayNotion("更新信息", "更新成功", "success")
  } else {
    SwayNotion("更新信息", "更新失败", "success")
  }
}
onMounted(() => {
  Object.assign(infoList, userInfo.value)
  infoList.birthday = infoList.birthday.split("T")[0]
})
</script>

<style lang="less" scoped>
.info {
  position: relative;

  &-btn-sub {
    position: absolute;
    right: 100px;
    top: 20px;
    width: 76px;
    height: 28px;
    border-radius: 44px;
    border: none;
    color: @bg-white-0;
    background: @bg-red-5;
    cursor: pointer;
    &:hover {
      background: @bg-red-6;
    }
  }

  h3 {
    margin-top: 60px;
    margin-bottom: 20px;
    text-indent: 10px;
    user-select: none;
    border-left: 3px solid @line-green-4;

    span {
      font-size: 12px;
      color: @text-gray-3;
    }
  }

  &-group {
    display: flex;
    height: 40px;
    margin: 10px 0;
    border-bottom: 1px solid @bg-gray-1;

    b {
      user-select: none;
      display: flex;
      align-items: center;
      width: 120px;
      min-height: 42px;
    }

    .info-group-msg {
      width: 100%;
      display: flex;
      align-items: center;
    }

    input {
      border: none;
    }
  }

  .base-info {
    margin-bottom: 20px;
  }
}

.check-input {
  position: relative;
  bottom: 2px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 50%;
  height: 40px;
  background: @bg-gray-1;
  border-radius: 5px;

  input[type="text"] {
    flex: 1;
    height: 100%;
    color: @text-gray-7;
    border: none;
    padding: 0 10px;
    box-sizing: border-box;
    font-size: 1rem;
    line-height: 1.5;
    outline: 0 !important;
    background: 0 0;
  }
}
</style>
