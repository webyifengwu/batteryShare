<template>
  <div class="history">
    <van-empty description="还未借充电宝现在" v-if="pay === null">
      <van-button round type="primary" class="bottom-button" @click="toLocation">查看附近的充电点</van-button>
    </van-empty>
    <div v-if="pay !== null" class="history_wrapper">
      <div class="box" v-for="(payItem, index) in pay">
        <div class="orderId">订单编号:<span>{{ payItem.orderId }}</span></div>
        <div class="orderStatus">订单状态:<span>{{ payItem.status ? "使用中" : "已归还" }}</span></div>
        <!-- <div class="createTime">花费：<span>{{payItem.total}}元</span></div>
        <div class="createTime">使用时间：<span>{{payItem.time}}小时</span></div>
        <div class="createTime">开始时间：<span>{{payItem.createTime}}</span></div>
        <div class="createTime">结束时间：<span>{{payItem.endTime}}</span></div> -->
        <div class="look" @click="toDetails(payItem)">
          <div> 查看详情</div><van-icon name="arrow" />

        </div>
      </div>
    </div>


    <div class="block"></div>

  </div>
</template>

<script setup>
import { getPays } from '@/API/url';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
const store = useStore()
const router = useRouter()
const username = computed(() => store.state.userModule.username)
const pay = ref(null)

const toLocation = () => {
  router.push({ name: "location" })
}

const toDetails = (value) => {
  console.log(value);
  store.commit("setSubPageTitle", "订单详情")
  router.push({
    name: "details", query: {
      orderId: value.orderId,
      createTime: value.createTime,
      endTime: value.endTime,
      dpName:value.dpName,
      status: value.status,
      time: value.time,
      total: value.total,
      username: value.username
    }
  })
}

onMounted(async () => {
  let res = await getPays({
    username: username.value,
    status: 0
  })
  if (res.code === 200) {
    if (res.data.list.length)
      pay.value = res.data.list
  }


})
</script>



<style lang="scss" >
.history {
  .history_wrapper {
    width: 100%;
    height: 100vh;
    background-color: #F5F5F5;

    .box {
      padding-top: 10px;
      text-align: left;
      margin: 10px auto;
      width: 95%;
      background-color: #FFFFFF;


      .orderId {
        height: 40px;
        padding-left: 15px;
        color: #888888;

        span {
          color: #00003A;
          padding-left: 30px;

        }
      }

      .orderStatus {
        padding-left: 15px;

        height: 40px;
        color: #888888;

        span {
          color: #00003A;
          padding-left: 30px;

        }

      }

      .createTime {
        padding-left: 15px;

        height: 40px;

        color: #888888;

        span {
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

        .van-icon {
          line-height: 40px;
        }
      }


    }
  }
}</style>
