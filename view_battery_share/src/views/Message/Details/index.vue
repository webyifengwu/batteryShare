<template>
    <div  class="details_wrapper">
      <div class="box" >
        <div class="orderStatus">订单状态：<span>{{ Nstatus == 1 ? "使用中" : "已归还" }}</span></div>

        <div class="dpName">充电点：<span>{{NdpName}}</span></div>
        <div class="orderId">订单编号：<span>{{ NorderId }}</span></div>
        <div class="total">花费：<span>{{Ntotal}}元</span></div>
        <div class="createTime">使用时间：<span>{{Ntime}}小时</span></div>
        <div class="createTime">开始时间：<span>{{NcreateTime}}</span></div>
        <div class="createTime">结束时间：<span>{{NendTime}}</span></div>
   
      </div>
    </div>


    <div class="block"></div>

</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
const route = useRoute()
const NorderId = ref("")
const NcreateTime = ref("")
const NendTime = ref("")
const NdpName = ref("")
const Nstatus = ref("")
const Ntime = ref("")
const Ntotal = ref("")
const Nusername = ref("")


onMounted(() => {
  let {
    orderId,
    createTime,
    endTime,
    dpName,
    status,
    time,
    total,
    username
  } = route.query
  
    NorderId.value = orderId
    NcreateTime.value =createTime
    NendTime.value = endTime
    NdpName.value = dpName
    Nstatus.value = status
    Ntime.value =time
    Ntotal.value = total
    Nusername.value = username
  


})
</script>


<style lang="scss">

.details_wrapper {
  width: 100%;
  height: 100vh;
  background-color: #F5F5F5;

  .box {
padding-top:10px;
    text-align: left;
    margin: 10px auto;
    width: 95%;
    background-color: #FFFFFF;


    .orderId {
      height: 40px;
padding-left: 15px;
      color: #888888;
      span{
        color: #00003A;
        padding-left: 19px;

      }
    }

    .orderStatus {
      padding-left: 15px;
      height: 40px;
      color: #888888;
      span{
        color: #00003A;
        padding-left: 20px;

      }

    }

    .dpName {
      padding-left: 30px;
      // text-indent: 10px;
      height: 40px;
      color: #888888;
      span{
        color: #00003A;
        padding-left: 20px;

      }

    }

    .total {
      padding-left: 15px;
      text-indent: 30px;
      height: 40px;

      color: #888888;
      span{
        padding-left: 18px;

        color: #00003A;

      }
    }
    .createTime {
      padding-left: 15px;

      height: 40px;

      color: #888888;
      span{
        padding-left: 18px;

        color: #00003A;

      }
    }

    .look {
      padding-left: 15px;
      height: 40px;
      line-height: 40px;
      border-top: 1px solid #F5F5F5;
      display: flex;
      justify-content: space-between;
      .van-icon{
        line-height: 40px;
      }
    }


  }
}

</style>
