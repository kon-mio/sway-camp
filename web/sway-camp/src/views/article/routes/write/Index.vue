<template>
  <div class="article-write">
    <!-- 编辑器 -->
    <div class="article-write__frame">
      <div class="title">
        <b>来自深渊</b>
        <button>提交</button>
      </div>
      <div class="content">
        <v-md-editor />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import VMdEditor from "../../components/md-editor/VMdEditor.vue"
</script>

<style lang="less" scoped>
.article-write {
  position: relative;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  padding: 10px 0 10px 10px;
  &__frame {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    border-radius: 12px;
    overflow: hidden;
    .title {
      position: relative;
      display: flex;
      flex-direction: row;
      align-items: center;
      width: 100%;
      height: 50px;
      box-sizing: border-box;
      padding: 0 20px;
      background-color: white;

      button {
        position: absolute;
        right: 20px;
        width: 80px;
        height: 36px;
        color: white;
        background-color: rgb(181, 211, 255);
        outline: none;
        border: none;
        border-radius: 8px;
        cursor: pointer;

        &:hover {
          background-color: rgb(201, 223, 255);
        }
      }
    }
    .content {
      flex: 1;
      width: 100%;
    }
  }

  &__info {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 500px;
    .cover {
      display: flex;
      flex-direction: column;
      align-items: center;
      height: 270px;
      width: 320px;
      span {
        margin-top: 10px;
        font-size: 12px;
        color: #acacac;
        cursor: pointer;
      }
    }
    .info {
      display: flex;
      flex-direction: column;
      align-items: center;
      flex: 1;
      height: 100%;
      box-sizing: border-box;
      padding-top: 80px;
      .item {
        display: flex;
        flex-direction: row;
        width: 100%;
        height: 40px;
        margin: 5px 0;
        &-input {
          display: flex;
          align-items: center;
          width: 70%;
        }
        &-label {
          width: 30%;
          display: flex;
          align-items: center;
          justify-content: center;
          label {
            font-size: 16px;
            font-weight: 600;
            color: #858585;
          }
        }
      }
      .textarea {
        margin-top: 15px;
        height: 80px;
        .item-label {
          align-items: flex-start;
        }
      }
    }
  }
}

.abyss-md {
  height: calc(100vh - 70px);
  ::v-deep(pre::before) {
    content: "";
    display: block;
    background: url("");
    height: 4px;
    width: 100%;
    background-size: 40px;
    background-repeat: no-repeat;
    background-color: #282c34;
    margin-bottom: 0;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    background-position: 10px 10px;
  }
}
.abyss-cropper__preview {
  &--square {
    width: 240px;
    height: 150px;
    border-radius: 8px;
    overflow: hidden;
  }
}

::v-deep(.el-dialog) {
  border-radius: 12px;
  .el-dialog__title {
    font-weight: 700;
    font-size: 20px;
  }
  .el-dialog__body {
    padding: 10px 30px 0px 30px;
  }
}
</style>
