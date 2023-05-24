<template>
  <div>
    <van-empty description="暂无租借中充电宝" v-if="pay === null">
      <van-button round type="primary" class="bottom-button" @click="toLocation">查看附近投放点</van-button>
    </van-empty>
    <div v-if="pay !== null" class="back_wrapper">

      <div class="title">订单状态通知</div>

      <div class="box" v-for="(payItem, index) in pay">
        <div class="details">
          <div class="orderId">订单编号:<span>{{ payItem.pId }}</span></div>
          <div class="orderStatus">订单状态:<span>{{ payItem.status ? "使用中" : "已归还" }}</span></div>
          <div class="createTime">开始时间：<span>{{ payItem.createTime }}</span></div>
        </div>

        <div class="look" @click="finshOrder(payItem.orderId)">
          <div> 归还</div><van-icon name="arrow" />
        </div>

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
//借
const toLocation = () => {
  router.push({ name: "location" })
  store.commit("setSubPageTitle", "附近投放点")
}
//还
const finshOrder = (value) => {
  store.commit("setSubPageTitle", "可用投放点")
  router.push({ name: 'return', query: { orderId: value } })
}
const toDetails = (value) => {
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
    status: 1
  })
  if (res.code === 200) {
    if (res.data.list.length)
      pay.value = res.data.list.map((item, index) => {
        item.key = index + 1;
        return item
      })
  }


})
</script>


<style lang="scss" >
.back_wrapper {
  background-color: #F5F5F5;

  .title {
    // text-align: center;
    text-indent: 36%;
    font-size: 25px;
  }

  .box {
    padding-top: 10px;
    text-align: left;
    margin: 10px auto;
    width: 95%;
    background-color: #FFFFFF;

    .details {
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

    }

    .look {
      padding-left: 15px;
      height: 40px;
      width: 100%;
      line-height: 40px;
      border-top: 1px solid #F5F5F5;
      display: flex;
      justify-content: space-between;

      .van-icon {
        line-height: 40px;
      }
    }


  }

}</style>
