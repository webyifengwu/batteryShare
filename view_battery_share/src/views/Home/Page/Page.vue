<template>
  <div id="home">
    <van-swipe :autoplay="3000" lazy-render>
      <van-swipe-item v-for="image in images" :key="image" >
        <img :src="image" style="width:100%;height:100%;" />
      </van-swipe-item>
    </van-swipe>
    
    <van-grid icon-size="50px" :gutter="10" :column-num="2" >
      <van-grid-item v-for="value in gridArr" :key="value" :to="value.to" :icon="value.icon" :text="value.text" />
    
    </van-grid>

<div class="block"></div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { onMounted, ref, watch } from "vue"
import { useStore } from "vuex"
const store = useStore()
const images = [
require("@/assets/bg/SharedMobilePowerSupply01.jpg"),
require("@/assets/bg/SharedMobilePowerSupply02.jpg"),
require("@/assets/bg/SharedMobilePowerSupply03.jpg"),
require("@/assets/bg/SharedMobilePowerSupply04.jpg"),
require("@/assets/bg/SharedMobilePowerSupply05.jpg"),
require("@/assets/bg/SharedMobilePowerSupply06.jpg"),

];
const router = useRouter()
const gridArr = [
   {
    icon: "scan",
    text: "附近投放点",
    to:"/message/location?title=附近投放点"
  }, {
    icon: "description",
    text: "借还规则",
    to:"/message/rule?title=规则"
  } ,{
    icon: "cart-o",
    text: "历史订单",
    to:"/message/history?title=历史订单"
  }
]


onMounted(() => {
 
})
</script>
<style lang="scss">
// .van-grid-item__content{
// font-size: large;
// }
:root:root{

  --van-grid-item-text-font-size:25px;
}
.block{
  width: 100%;
  height: 100px;
}
</style>
