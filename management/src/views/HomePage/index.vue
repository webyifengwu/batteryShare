<template>
  <div class="home">
    <a-carousel :after-change="onChange" :autoplay="true" :dots="true" :dotPostion="'left'" style="">

 
        <img src="@/assets/swiper/SharedMobilePowerSupply01.jpg"/>
     
        <img src="@/assets/swiper/SharedMobilePowerSupply02.jpg"/>
     
        <img src="@/assets/swiper/SharedMobilePowerSupply03.jpg"/>
    
        <img src="@/assets/swiper/SharedMobilePowerSupply04.jpg"/>
  
        <img src="@/assets/swiper/SharedMobilePowerSupply05.jpg"/>
    
        <img src="@/assets/swiper/SharedMobilePowerSupply06.jpg"/>
    </a-carousel>


  </div>
</template>

<script setup>
import { onMounted } from "vue"
const onChange = current => {
};
onMounted(() => {

})
</script>


<style lang="scss">
.home {
position: relative;
  .ant-carousel {
    width: 1300px;
    height: 730px;
    margin: 10px auto;
    .slick-slider {
      .slick-list {

        .slick-track {
          .slick-slide{

          }
        }
      }
    }
  }

  .ant-carousel :deep(.slick-slide) {
    // text-align: center;
    height: 160px;
    line-height: 160px;
    overflow: hidden;
  }

  .ant-carousel :deep(.slick-slide h3) {

    color: #fff;
  }
  h2{
    span{
      color: pink;

    }
  }


}
</style>
