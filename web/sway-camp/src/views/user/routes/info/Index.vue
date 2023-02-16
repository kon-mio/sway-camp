<template>
  <div class="info">
    <em>点击相应信息进行修改</em>
    <button class="info-btn-sub">提交修改</button>
    <!-- 账户信息 -->
    <h3>账号信息 <span>*不可修改</span></h3>
    <div v-for="(p, item) in accountInfo" :key="item" class="info-group">
      <b>{{ p.title }}</b>
      <div class="info-group-msg">{{ p.Info }}</div>
    </div>
    <!-- 用户信息 -->
    <h3>基本信息</h3>
    <div v-for="(p, item) in basicInfo" :key="item" class="info-group base-info">
      <b>{{ p.title }}</b>
      <div v-if="p.type.includes('input')" class="check-input">
        <input type="text" v-model="p.Info" style="width: 200px" placeholder="请输入昵称" />
        <p></p>
      </div>
      <div v-else-if="p.type.includes('date')" style="position: relative; bottom: 4px">
        <el-date-picker
          value-format="YYYY-MM-DD"
          v-model="p.Info"
          type="date"
          placeholder="Pick a day"
          size="large"
        />
      </div>
      <div v-else style="position: relative; bottom: 4px">
        <el-radio-group v-model="p.Info" size="large">
          <el-radio-button label="女" />
          <el-radio-button label="男" />
          <el-radio-button label="保密" />
        </el-radio-group>
      </div>
    </div>
    <!-- 联系方式 -->
    <h3>联系方法 <span>*暂不可用</span></h3>
    <div v-for="(p, item) in contactInfo" :key="item" class="info-group">
      <b>{{ p.title }}</b>
      <div v-show="false">
        <input disabled type="text" v-model="p.Info" />
        <p></p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { onMounted, reactive, toRefs } from "vue"
import { storeToRefs } from "pinia"
import { useUserStore } from "@/stores/user.store"
export default {
  name: "UserInfo",
  setup() {
    interface InfoTabItemType {
      id: number
      title: string
      type: string
      Info: string | number
    }

    const userStore = useUserStore()
    const { userInfo } = storeToRefs(userStore)
    //用户信息tab
    const InfoTabs = reactive({
      // 账号信息
      accountInfo: [] as InfoTabItemType[],
      // 用户信息
      basicInfo: [] as InfoTabItemType[],
      // 联系方式
      contactInfo: [] as InfoTabItemType[]
    })
    onMounted(() => {
      // 信息栏赋值
      const accountInfo = [
        {
          id: 1,
          title: "角色",
          type: "input",
          Info: "用户"
        },
        { id: 2, title: "账号", type: "input", Info: userInfo.value?.sid },
        { id: 3, title: "邮箱", type: "input", Info: userInfo.value?.email },
        { id: 4, title: "注册时间", type: "input", Info: userInfo.value?.username }
      ] as InfoTabItemType[]
      const basicInfo = [
        { id: 1, title: "昵称", type: "input", Info: userInfo.value?.username },
        { id: 2, title: "性别", type: "radio", Info: userInfo.value?.gender },
        { id: 3, title: "生日", type: "date", Info: userInfo.value?.username }
      ] as InfoTabItemType[]
      const contactInfo = [
        { id: 1, title: "QQ", type: "input", Info: "" },
        { id: 2, title: "手机", type: "input", Info: "" }
      ] as InfoTabItemType[]
      InfoTabs.accountInfo = accountInfo
      InfoTabs.basicInfo = basicInfo
      InfoTabs.contactInfo = contactInfo
    })
    return {
      userInfo,
      ...toRefs(InfoTabs)
    }
  }
}
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
}

.check-input input[type="text"] {
  flex: 1;
  height: 100%;
  color: @text-gray-3;
  border: none;
  padding: 0 10px;
  box-sizing: border-box;
  font-size: 1rem;
  line-height: 1.5;
  outline: 0 !important;
  background: 0 0;
}
</style>
