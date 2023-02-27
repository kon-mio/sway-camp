<template>
  <div v-if="articleInfo" class="editor-header">
    <h2>{{ articleInfo.title }}</h2>
    <div class="editor-header__msg">
      <div class="info">
        <sway-icon name="biaoqian" />
        <span>{{ articleInfo.label }}</span>
      </div>
      <div class="info riqi">
        <sway-icon name="riqi_o" />
        <span>{{ articleInfo.createTime }}</span>
      </div>
      <div class="info view">
        <sway-icon name="chakan2" />
        <span>{{ articleInfo.viewCount }}次浏览</span>
      </div>
      <div class="info comment">
        <sway-icon name="pinglun" />
        <span>{{ articleInfo.viewCount }}条评论</span>
      </div>
    </div>

    <em v-if="!articleInfo.reprinted">版权声明：本文为博主原创文章，转载请说明来源</em>
    <em v-else
      >原文链接: <a :href="articleInfo.reprinted">{{ articleInfo.reprinted }}</a></em
    >
    <hr />
  </div>
</template>

<script lang="ts" setup>
import { computed } from "vue"
import type { ArticleInfo } from "@/api/article/type"

const props = withDefaults(
  defineProps<{
    articleInfo: ArticleInfo
  }>(),
  {}
)
const articleInfo = computed(() => {
  return props.articleInfo
})
</script>

<style lang="less" scoped>
.editor-header {
  width: 100%;
  box-sizing: border-box;
  padding: 0px 0 0 0px;
  margin: 0;
  background-color: white;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;

  h2 {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;
    overflow: hidden;
    text-align: justify;
    font-size: 34px;
    max-width: 90%;
    margin: 0;
  }

  em {
    display: block;
    font-size: 14px;
    color: var(--red);
    // 首行缩进
    text-indent: 4px;
  }

  hr {
    margin: 16px 0 0 0;
    border: 0;
    border-top: 1px solid rgba(0, 0, 0, 0.1);
  }

  &__msg {
    margin-top: 8px;
    height: 35px;
    display: flex;
    align-items: center;
    font-size: 0.8125rem;
    color: #a0a0a0;
    // 首行缩进
    text-indent: 2px;

    .info {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 20px;

      i {
        margin-top: 2px;
        box-sizing: border-box;
      }

      span {
        padding-right: 10px;
        // margin-left: -2px;
      }
    }

    .riqi {
      i {
        margin-bottom: 3px;
        font-size: 18px;
      }
    }

    .view {
      i {
        margin-bottom: 2px;
        font-size: 18px;
      }
    }
    .comment {
      i {
        font-size: 14px;
      }
    }
  }
}
</style>
