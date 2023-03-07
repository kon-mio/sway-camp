<template>
  <div class="anime-imglist">
    <ul class="anime-imglist_imgs">
      <li v-for="(item, index) in list" :key="index">
        <div
          v-for="(img, idx) in item"
          :key="idx"
          class="img-card"
          :style="`height:${img.height}px`"
        >
          <LazyImg :src="img.url" />
        </div>
      </li>
    </ul>
  </div>
</template>

<script lang="ts" setup>
import { computed, onMounted, reactive } from "vue"
import LazyImg from "../lazy-img/LazyImg.vue"
import { AnimeImage } from "@/api/anime/type"

const props = defineProps<{
  animeImage: AnimeImage[]
}>()
const imageList = computed(() => {
  return props.animeImage
})
const list = reactive<{
  firstList: AnimeImage[]
  secondList: AnimeImage[]
  thirdList: AnimeImage[]
}>({
  firstList: [],
  secondList: [],
  thirdList: []
})

const splitAnimeImages = (
  imageList: AnimeImage[],
  containerWidth: number
): [AnimeImage[], AnimeImage[], AnimeImage[]] => {
  // 计算每个图片的原始高度，并按原始比例缩放宽度
  imageList.forEach((image) => {
    const originalHeight = image.height
    const originalWidth = image.width
    const ratio = Number(originalWidth) / Number(originalHeight)
    image.height = Math.floor(containerWidth / ratio).toString()
    image.width = containerWidth.toString()
  })

  const totalHeight = imageList.reduce((acc, cur) => acc + parseInt(cur.height), 0)
  const avgHeight = totalHeight / 3
  const result: [AnimeImage[], AnimeImage[], AnimeImage[]] = [[], [], []]
  let currentHeight = 0
  let currentArrayIndex = 0

  for (const image of imageList) {
    const height = parseInt(image.height)
    if (currentHeight + height <= avgHeight) {
      // Add the image to the current array
      result[currentArrayIndex].push(image)
      currentHeight += height
    } else {
      // Find the array with the lowest total height and add the image to it
      const arrayHeights = result.map((arr) =>
        arr.reduce((acc, cur) => acc + parseInt(cur.height), 0)
      )
      const lowestHeightIndex = arrayHeights.indexOf(Math.min(...arrayHeights))
      result[lowestHeightIndex].push(image)
      currentHeight = height
      currentArrayIndex = lowestHeightIndex
    }
  }

  return result
}

onMounted(() => {
  const [first, second, third] = splitAnimeImages(imageList.value, 280)
  list.firstList = first
  list.secondList = second
  list.thirdList = third
})
</script>

<style lang="less" scoped>
li,
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.anime-imglist {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  width: 100%;
  margin-bottom: 100px;
  animation-duration: 0.6s;

  &_imgs {
    display: flex;
    width: 70%;

    li {
      padding-right: 16px;
      flex: 1;
      box-sizing: border-box;

      &:first-child {
        padding-left: 16px;
      }
    }

    &-item {
      cursor: pointer;
    }

    .img-card {
      position: relative;
      width: 100%;
      background: #def;
      margin: 16px 0;
      border: 4px solid #fff;
      box-sizing: border-box;
      overflow: hidden;

      img {
        cursor: pointer;
        display: block;
        width: 100%;
        transition: all 0.25s;
        object-fit: cover;
        user-select: none;

        &:hover {
          transform: scale(1.1);
        }
      }
    }
  }
}
</style>
