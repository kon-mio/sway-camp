<template>
  <div class="main-right">
    <div class="main-right-about"></div>
    <div class="main-right-up"></div>
    <div class="main-right-testack"></div>
    <div class="main-right-friendship"></div>
  </div>
</template>

<style lang="less" scoped>
.main-right {
  position: sticky;
  top: -20vh;
  display: flex;
  flex-direction: column;
  width: 280px;
  height: fit-content;
  background-color: skyblue;
  &-up {
    margin-top: 12px;
  }
  &-testack {
    margin-top: 12px;
  }
  &-friendship {
    margin-top: 12px;
    color: rgb(255, 133, 154);
  }
  .friendship {
    position: relative;
    ul {
      position: relative;
      margin: 0;
      padding: 0;
      list-style-type: none;
      li {
        position: relative;
        left: -18px;
        width: calc(100% + 36px);
        margin: 0;
        padding: 0;
        list-style-type: none;
        box-sizing: border-box;
        display: block;
        padding: 6px 18px;
        font-size: 12px;
        color: #768791;
        transition: all 0.4s ease;
        cursor: pointer;
        &:hover {
          background-color: rgb(242, 242, 242);
        }
      }
    }
  }
}
</style>
