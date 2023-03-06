<template>
  <div class="anime-home">
    <div class="anime-banner">
      <AnimeCarousel :anime-list="animeList" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Anime } from "@/api/anime/type"
import { reactive } from "vue"
import AnimeCarousel from "../../components/carousel/AnimeCarousel.vue"

const animeList = reactive<Anime[]>([
  {
    id: 1,
    name: "けいおん！",
    otherName: "轻音少女",
    region: "番剧",
    airTime: "2009-04-02T00:00:00.000+08:00",
    introduction:
      "新学年开始，高中一年级新生平泽唯在误将“轻音乐”当做了“轻便、简易的音乐”，而由于自己小时候玩响板得到老师表扬，于是萌发申请入部的想法。\r\n另一方面，樱丘高中“轻音部”因原来的部员全部毕业离校，此时轻音部新成员只有秋山澪和田井中律两人，无法满足部员至少四人的最低人数要求即将废部，这下该如何是好呢？\r\n此外，温柔可爱的千金小姐琴吹䌷被律强拉进入轻音部。\r\n于是，这四名高一女生机缘巧合聚在了一起，便有了吉他手平泽唯、贝司手秋山澪、鼓手田井中律以及键盘手琴吹䌷，轻音部的故事也由此展开。",
    web: "http://www.tbs.co.jp/anime/k-on/",
    cover:
      "https://sway-camp.oss-cn-qingdao.aliyuncs.com/image/cover/39179cb2593f4186a40a03f29df7c1b0.webp"
  },
  {
    id: 2,
    name: "からかい上手の高木さん OVA",
    otherName: "擅长捉弄的高木同学",
    region: "番剧",
    airTime: "2018-01-18T00:00:00.000+08:00",
    introduction:
      "“今天一定要捉弄高木同学，让她害羞！”\n某所中学，在各种方面都被邻座的女生高木同学捉弄的男生西片。\n他为了对此反击而每天奋斗，但……？\n这样的高木同学与西片带来的全力“捉弄”青春大战开始了！",
    web: "http://takagi3.me/",
    cover:
      "https://konmio.oss-cn-qingdao.aliyuncs.com/anime/cover/038d9654ac8e4bc59f710d0969ed06a5.jpg"
  },
  {
    id: 3,
    name: "メイドインアビス",
    otherName: "来自深渊",
    region: "番剧",
    airTime: "2017-07-07T00:00:00.000+08:00",
    introduction:
      "在每一个角落都被探索完毕的世界中，唯一残留的秘境——大洞穴“深渊”。不知道延续到何方，深不见底的巨大纵向洞穴，栖息着奇妙奇怪的生物们，沉眠着现在的人类无法制造的贵重遗物。“深渊”那充满了不可思议的姿态吸引了人们，并驱使人们展开冒险。如此，多次挑战大洞穴的冒险者们，陆续得到了“探窟家”的名号。\n住在建造于深渊边缘的城镇“奥斯”的孤儿莉可，梦想着有朝一日成为母亲那样伟大的探窟家，并解开深渊的谜团。这样的某一天，莉可在探索深渊的途中，捡到了有着少年样貌的机器人…？",
    web: "http://miabyss.com/",
    cover:
      "https://konmio.oss-cn-qingdao.aliyuncs.com/anime/cover/78079774aeaf4fddbf459b45daf08181.jpg"
  },
  {
    id: 4,
    name: "86-エイティシックス-",
    otherName: "86-不存在的地域-",
    region: "番剧",
    airTime: "2021-04-10T00:00:00.000+08:00",
    introduction:
      "为了应对吉亚迪所开发出的完全独立无人战斗兵器“军团”的入侵，其邻国圣格诺利亚共和国开发了无人战斗兵器毁灭之力。但是，无人战斗机只是空有名号，实际是没有被认可为”人“的人们——86——驾驶，被当作道具来使用。\n由”86“所组成的部队“先锋战队”的队长少年・辛艾，在只能等待着死亡的令人绝望的战场上为了某个目的而战斗着。在那里，共和国军队的精英・蕾娜就任了新任指挥管制官。她小时候有着被86所救助过的经历，因此想把被作为“人形猪”而遭到轻视的他们作为正常人类来对待。\n只不过是为了战斗而被作为道具使用的少年以及被寄予了未来期望的精英才女，本不应当有所交集的两人，在激烈的战斗中看到了未来——",
    web: "https://anime-86.com/",
    cover:
      "https://konmio.oss-cn-qingdao.aliyuncs.com/anime/cover/f682617ff24243538a2d0bc2528acbfc.jpg"
  },
  {
    id: 5,
    name: "風が強く吹いている",
    otherName: "强风吹拂",
    region: "番剧",
    airTime: "2018-10-02T00:00:00.000+08:00",
    introduction:
      "长跑的目标不是更快，而是更强。\n“明明这么痛苦，这么难过，为什么就是不能放弃跑步？\n因为全身细胞都在蠢蠢欲动，想要感受强风迎面吹拂的滋味。”\n宽政大学宿舍“竹青庄”的十名舍友凑成杂牌长跑队，在队长清濑灰二的魔鬼训练下，从零开始向日本历史最悠久的长跑接力赛“箱根驿传”挺进 。这十名大学生包括两名田径队逃兵、一对神经大条又聒噪的双胞胎、俊帅漫画宅男、尼古丁中毒的万年留级生、逻辑超强的毒舌精英、不爱跑步的黑人留学生、老实好青年、百发百中猜谜王。这些“选手”刚开始时连自己是田径队都不知道，且一半成员没有长跑经验。他们能否创造“箱根驿传”史上最大奇迹？不到最后一棒，没人知道答案。",
    web: "http://kazetsuyo-anime.com/",
    cover:
      "https://konmio.oss-cn-qingdao.aliyuncs.com/anime/cover/b79482d2a7a04c239ada44b8bd85cbd9.jpg"
  },
  {
    id: 6,
    name: "アオアシ",
    otherName: "青之芦苇",
    region: "国创",
    airTime: "2022-04-09T00:00:00.000+08:00",
    introduction:
      "在爱媛上学的中学三年生青井苇人，在那纤弱的身体中隐藏着强烈的足球天赋，但是因为那太过直球的性格，让他经历了很大的挫折。在这样的苇人面前，出现的是东京city esperion的青年队监督福田达也。",
    web: "https://aoashi-pr.com/",
    cover:
      "https://konmio.oss-cn-qingdao.aliyuncs.com/anime/cover/573765133f2544d6bee0d4eee2d140b2.jpg"
  },
  {
    id: 7,
    name: "测试更新 の",
    otherName: "更新修改测试",
    region: "番剧",
    airTime: "2001-02-01T00:00:00.000+08:00",
    introduction: "麻生老师yyds",
    web: "http://mio.takagi-san.cn/anime",
    cover:
      "https://konmio.oss-cn-qingdao.aliyuncs.com/anime/cover/1a3e51ca27e14f24a3735dd678549404.jpg"
  },
  {
    id: 8,
    name: "权限测试",
    otherName: "空你几哇",
    region: "番剧",
    airTime: "2022-09-14T00:00:00.000+08:00",
    introduction: "权限测试",
    web: "权限测试",
    cover:
      "https://konmio.oss-cn-qingdao.aliyuncs.com/anime/cover/665b10932bfd43efbfa87a93d7c8b5ec.png"
  }
])
</script>

<style lang="less" scoped>
.anime-home {
  position: relative;
  width: 100%;
  height: 100%;
}
.anime-banner {
  position: relative;
  top: 20px;
  left: 2%;
  width: 98%;
  min-height: 700px;
  max-height: 95vh;
  aspect-ratio: 16/10;
  overflow: hidden;
  border-top-left-radius: 24px;
  border-bottom-left-radius: 24px;
  background-color: #faebd7;
}
</style>
