<template>
  <div class="article-write">
    <!-- 编辑器 -->
    <div class="article-write__frame">
      <div class="title">
        <b>来自深渊</b>
        <button @click="addInfo">完善信息</button>
      </div>
      <div class="content">
        <v-md-editor ref="mdEditor" @get-content="getContent" />
      </div>
    </div>
  </div>
  <sway-dialog v-model="dialog" title="文章信息">
    <div class="article-info">
      <div class="info-cover">
        <cropper-preview v-if="cropper" ref="cropperTarget" :options="cropperOptions">
          <div class="cropper__preview--square"></div>
        </cropper-preview>
      </div>
      <div class="info-form">
        <div class="item">
          <div class="title">
            <div class="item-label">
              <label>文章标题 :</label>
            </div>
            <div class="item-input">
              <el-input v-model="article.title" size="large" placeholder="请输入文章标题" />
            </div>
          </div>

          <div class="sort">
            <div class="item-label">
              <label>文章类型 :</label>
            </div>
            <div class="item-input">
              <el-select
                v-model="article.sortId"
                placeholder="文章标签"
                size="large"
                @change="article.labelId = ''"
              >
                <el-option
                  v-for="(item, index) in sorts"
                  :key="index"
                  :label="item.sortName"
                  :value="item.id"
                />
              </el-select>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="status">
            <!-- 公开 -->
            <div class="status">
              <div class="item-label">
                <label>是否公开 :</label>
              </div>
              <div class="item-input">
                <CheckBox v-model="article.viewStatus" />
              </div>
            </div>
            <!-- 开启评论 -->
            <div class="status">
              <div class="item-label">
                <label>开启评论 :</label>
              </div>
              <div class="item-input">
                <CheckBox v-model="article.commentStatus" />
              </div>
            </div>
            <!-- 转载 -->
            <div class="status" style="width: 80px">
              <div class="item-label">
                <label>转载 :</label>
              </div>
              <div class="item-input">
                <CheckBox v-model="article.reprintedStatus" />
              </div>
            </div>
          </div>
          <div class="label" style="margin-left: 64px">
            <div class="item-label">
              <label>文章标签 :</label>
            </div>
            <div class="item-input">
              <el-select v-model="article.labelId" placeholder="文章标签" size="large">
                <el-option
                  v-for="(item, index) in sortLabels"
                  :key="index"
                  :label="item.labelName"
                  :value="item.id"
                />
              </el-select>
            </div>
          </div>
        </div>
        <!-- 转载链接 -->
        <div v-if="article.reprintedStatus" class="item">
          <div class="item-label">
            <label>转载链接 :</label>
          </div>
          <div class="item-input">
            <el-input v-model="article.reprinted" size="large" placeholder="请输入转载链接" />
          </div>
        </div>
        <!-- 文章简介 -->
        <div class="item textarea">
          <div class="item-label">
            <label>文章简介 :</label>
          </div>
          <div class="item-input">
            <el-input
              v-model="article.introduction"
              maxlength="50"
              placeholder="文章简介"
              show-word-limit
              type="textarea"
              :autosize="{ minRows: 3, maxRows: 6 }"
              resize="none"
            />
          </div>
        </div>
      </div>
      <div class="option">
        <el-button type="primary" @click="saveArticle"> 确定 </el-button>
      </div>
    </div>
  </sway-dialog>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted, computed } from "vue"
import SwayDialog from "@/components/dialog/SwayDialog.vue"
import VMdEditor from "../../components/md-editor/VMdEditor.vue"
import CropperPreview from "@/components/cropper-preview/CropperPreview.vue"
import { ArticleDTO, ArticleLabel, ArticleSort } from "@/api/article/type"
import { listSortApi, saveArticleApi } from "@/api/article/api"
import { HttpStatusCode } from "@/common/enum"
import CheckBox from "@/components/form/check-box/CheckBox.vue"
import { useGlobalStore } from "@/stores/global.sotre"

const globalStore = useGlobalStore()

const dialog = ref(false)
const cropper = ref(false)
const cropperTarget = ref<InstanceType<typeof CropperPreview> | null>(null)
const mdEditor = ref<InstanceType<typeof VMdEditor> | null>(null)
const cropperOptions = {
  // 固定裁剪框宽高比
  aspectRatio: 4 / 3,
  preview: ".cropper__preview--square"
}
const article = reactive<ArticleDTO>({
  sortId: "",
  labelId: "",
  title: "",
  content: "",
  introduction: "",
  reprinted: "",
  commentStatus: true,
  viewStatus: true,
  reprintedStatus: false,
  cover: null
})
const sorts = reactive<ArticleSort[]>([])
const sortLabels = computed<ArticleLabel[] | null>(() => {
  if (sorts.length === 0) {
    return null
  }
  return (
    sorts.find((item) => {
      return item.id === article.sortId
    })?.labels || null
  )
})
const listSortList = async () => {
  const res = await listSortApi()
  if (res.code === HttpStatusCode.Success) {
    sorts.push(...res.data)
  }
}
const getContent = (content: string) => {
  article.content = content
}
const saveArticle = async () => {
  const cover = cropperTarget.value?.currentImage()
  if (!cover) {
    globalStore.openMessageMini("请选择文章封面")
    return
  }
  article.cover = cover
  if (!article.title) {
    globalStore.openMessageMini("请输入文章标题")
    return
  }
  if (!article.sortId || !article.labelId) {
    globalStore.openMessageMini("请选择文章类型/标签")
    return
  }
  if (article.reprintedStatus) {
    if (!article.reprinted) {
      globalStore.openMessageMini("请选择输入转载链接")
      return
    }
  } else {
    article.reprinted = ""
  }
  const ArticleForm: FormData = new FormData()
  Object.keys(article).forEach((item) => {
    console.log(item, article[item as keyof ArticleDTO])
    if (typeof article[item as keyof ArticleDTO] !== "object") {
      ArticleForm.append(item, article[item as keyof ArticleDTO]?.toString() || "")
    } else {
      ArticleForm.append(item, article[item as keyof ArticleDTO] as File)
    }
  })

  const res = await saveArticleApi(ArticleForm)
  if (res.code === HttpStatusCode.Success) {
    globalStore.openMessageMini("上传成功")
    dialog.value = false
    mdEditor.value?.init()
    Object.assign(article, {
      sortId: "",
      labelId: "",
      title: "",
      content: "",
      introduction: "",
      reprinted: "",
      commentStatus: true,
      viewStatus: true,
      reprintedStatus: false,
      cover: null
    })
  } else {
    globalStore.openMessageMini("上传失败")
  }
}
const addInfo = () => {
  dialog.value = true
  cropper.value = true
}

onMounted(() => {
  listSortList()
})
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
.article-info {
  width: 100%;
  height: 100%;
  padding: 40px;
  box-sizing: border-box;
  .info-cover {
    width: 100%;
    height: 300px;
    .cropper__preview {
      &--square {
        width: 240px;
        height: 150px;
        border-radius: 8px;
        overflow: hidden;
      }
    }
  }
  .info-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    padding-left: 40px;
    box-sizing: border-box;
    .item {
      display: flex;
      flex-direction: row;
      width: 100%;
      height: 40px;
      margin: 5px 0;
      &-input {
        display: flex;
        align-items: center;
        width: calc(90% - 116px);
      }
      &-label {
        width: 120px;
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

    .title,
    .sort,
    .label {
      display: flex;
      flex-direction: row;
      width: 50%;
      height: 100%;
    }

    .status {
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      width: auto;
    }

    .textarea {
      margin-top: 15px;
      height: 80px;
      .item-label {
        align-items: flex-start;
      }
    }
  }
  .option {
    position: absolute;
    bottom: 30px;
    right: 100px;
    button {
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
