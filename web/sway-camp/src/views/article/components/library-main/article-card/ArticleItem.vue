<template>
  <div class="list-item" @click="readArticle(article.id)">
    <div class="list-item__img">
      <ArticleCover ref="coverTarget" :src="article.cover" />
    </div>
    <div class="list-item__info">
      <div class="title">{{ article.title }}</div>
      <div class="intor">{{ article.introduction }}</div>
      <div class="else">
        <div class="time">
          <AsIcon name="riqi_o" />
          {{ article.createTime.split(" ")[0] }}
        </div>
        <div class="auther">
          <AsIcon name="zuozhe" />
          {{ article.username }}
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { ArticleInfo } from "@/api/article/type"
import ArticleCover from "@/components/article/article-cover/ArticleCover.vue"
import { useReadArticle } from "@/hooks/useReadArticle.hooks"
import { defineComponent, PropType } from "vue"
export default defineComponent({
  name: "ArticleItem",
  components: { ArticleCover },
  props: {
    article: {
      type: Object as PropType<ArticleInfo>,
      default: () => {}
    }
  },
  setup() {
    const { readArticle, coverTarget } = useReadArticle()
    return { readArticle, coverTarget }
  }
})
</script>

<style lang="less" scoped>
.list-item {
  position: relative;
  display: flex;
  justify-content: space-between;
  height: 150px;
  padding: 16px;
  overflow: hidden;
  box-sizing: border-box;
  border-radius: 10px;
  z-index: 3;
  background: #fff;
  cursor: pointer;

  &::before {
    content: "";
    position: absolute;
    top: 0;
    display: block;
    width: 100%;
    height: 100%;
    z-index: 1;
    opacity: 1;
    transition: all 0.8s ease;
    transform: translate(120%);
    background: rgba(0, 0, 0, 0.2);
  }

  &__img {
    position: relative;
    width: 200px;
    height: 120px;
    background: #ecf5ff;
    border-radius: 8px;
    overflow: hidden;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;

      &::after {
        content: "";
        position: absolute;
        left: 0;
        top: 0;
        display: block;
        width: 100%;
        height: 100%;
        z-index: 2;
      }
    }
  }

  &__info {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    box-sizing: border-box;
    padding: 2px 0 2px 16px;
    width: calc(100% - 160px);
    height: 120px;

    .title {
      display: -webkit-box;
      -webkit-box-orient: vertical;
      text-overflow: ellipsis;
      overflow: hidden;
      -webkit-line-clamp: 1;
      font-weight: 600;
      font-size: 18px;
      color: #717171;
    }

    .intor {
      display: -webkit-box;
      -webkit-box-orient: vertical;
      text-overflow: ellipsis;
      overflow: hidden;
      margin-top: 8px;
      -webkit-line-clamp: 2;
      font-size: 14px;
      color: #768791;
    }

    .else {
      position: relative;
      display: flex;
      flex-direction: row;
      user-select: none;
      font-size: 12px;
      margin-top: auto;

      div {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        height: 100%;
      }

      .time {
        i {
          font-size: 16px;
          margin-right: 2px;
          color: #768791;
        }

        color: #768791;
      }

      .auther {
        margin-left: 4px;

        i {
          font-size: 12px;
          margin-right: 2px;
          color: #768791;
        }

        color: #768791;
      }
    }
  }
}
</style>
