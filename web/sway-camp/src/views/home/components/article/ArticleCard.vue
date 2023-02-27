<template>
  <!-- 参考地址： https://shoka.lostyu.me/ -->
  <div class="article-card">
    <!-- 文章封面 -->
    <div class="article-card__cover">
      <div class="cover-img">
        <div class="cover-img--trans">
          <article-cover ref="coverTarget" :src="article.cover" />
        </div>
      </div>
    </div>
    <!-- 文章基本信息 -->
    <div class="article-card__info">
      <div class="info-header">
        <div class="time">
          <sway-icon name="riqi_o" />
          <span>{{ article.createTime }}</span>
        </div>
        <div class="label">
          <sway-icon name="riqi_o" />
          <span> {{ article.label }} </span>
        </div>
      </div>
      <div class="info-main">
        <div class="title">
          <h2>{{ article.title }}</h2>
        </div>
        <div class="introduction">
          <div>{{ article.introduction }}</div>
        </div>
      </div>
      <div class="info-footer">
        <span class="author">
          <sway-icon name="zuozhe" />
          <span>{{ article.username }}</span>
        </span>
        <div class="read" @click="readArticle(article.id)">阅 读</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ArticleInfo } from "@/api/article/type"
import ArticleCover from "@/components/article/article-cover/ArticleCover.vue"
import { useReadArticle } from "@/hooks/useReadArticle.hooks"

const props = defineProps<{
  article: ArticleInfo
}>()

const { readArticle, coverTarget } = useReadArticle()
</script>

<style lang="less" scoped>
@icon-color: #999;
.article-card {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr;
  width: 100%;
  height: 225px;
  background-color: white;
  border-radius: 12px;
  transition: all 0.2s ease-in-out 0s;
  box-shadow: 0 0.625rem 1.875rem -0.9375rem rgba(0, 0, 0, 0.1);
  animation: slide-in-bottom 0.5s;

  &:hover {
    box-shadow: 0.1rem 0.625rem 1.875rem 0rem rgba(0, 0, 0, 0.15);

    .cover-img--trans {
      transform: rotate(4deg) scale(1.1) !important;
    }
  }

  &__cover {
    width: 100%;
    height: 100%;

    .cover-img {
      height: 225px;
      margin-right: 1.5rem;
      clip-path: polygon(0 0, 92% 0, 100% 100%, 0 100%);
      border: none;
      cursor: pointer;
      transition: all 0.2s ease-in-out;
      overflow: hidden;
      border-top-left-radius: 12px;
      border-bottom-left-radius: 12px;
      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }

      &--trans {
        width: 100%;
        height: 100%;
        transition: all 0.2s ease-in-out 0s;
      }
    }
  }

  &__info {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 35px 1fr 50px;

    .info-header {
      display: flex;
      justify-content: flex-end;
      padding: 12px 16px 0 0;
      vertical-align: middle;

      .time {
        position: relative;
        margin-right: 10px;
      }
      .label {
        position: relative;
        margin-right: 10px;
        span {
          font-size: 10px;
        }
      }
      span {
        position: relative;
        left: 2px;
        line-height: 16px;
        font-size: 12px;
        color: #999;
      }
    }

    .info-main {
      display: flex;
      flex-direction: column;
      padding-right: 16px;

      .title {
        width: 100%;
        max-width: 100%;
        overflow: hidden;

        h2 {
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 1;
          overflow: hidden;
          font-weight: 700;
          line-height: 1.5;
          margin: 0.725rem 0;
          color: #000000;
        }
      }

      .introduction {
        display: flex;
        width: 100%;

        div {
          width: 100%;
          font-size: 0.875em;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 4;
          overflow: hidden;
          word-break: break-all;
          color: #999;
          justify-content: space-around;
        }
      }
    }

    .info-footer {
      position: relative;
      display: flex;
      flex-direction: row;
      align-items: center;

      .author {
        display: flex;
        align-items: center;
        width: 70%;
        height: 50%;

        i {
          margin-left: 6px;
          font-size: 16px;
          color: @icon-color;
        }

        span {
          margin-left: 2px;
          margin-bottom: 0.1px;
          font-size: 0.8125em;
          color: @icon-color;
        }
      }

      .read {
        position: absolute;
        bottom: 0;
        right: 0;
        font-size: 16px;
        text-align: center;
        padding: 0.6rem 1rem;
        border-radius: 12px 0;
        transition: all 0.2s ease-in-out 0s;
        background-image: linear-gradient(to right, #ed6ea0 0, #ec8c69 100%);
        color: white;
        cursor: pointer;

        &:hover {
          bottom: -6px;
          right: -6px;
        }

        &::before {
          content: "";
          position: absolute;
          display: block;
          height: calc(100% - 1rem);
          width: calc(100% - 1rem);
          border-radius: 5rem;
          left: 0.5rem;
          top: 0.8rem;
        }
      }
    }
  }
}

@keyframes slide-in-bottom {
  0% {
    -webkit-transform: translateY(100px);
    transform: translateY(100px);
    opacity: 0;
  }

  100% {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>
