<template>
  <div class="wrapper">
    <div class="balance"> 余额为：<span class="balance_num">{{ balance }}元</span>
    </div>

    <ul class="box">
      <li class="item" v-for="(item, index) in list" @click="changeNum(index)" :style="item.bg">
        <div>
          <div class="num1">{{ item.num }}元</div>
          <div class="num2" :style="item.color">￥{{ Number(item.num).toFixed(2) }}</div>
        </div>
      </li>
    </ul>

    <div class="input">
      <input type="number"  v-model="inputNum" placeholder="自定义输入金额（1-9999）元" @blur="changeInput">
      <div style="color: red;text-align: left;" v-show="bool">{{ errorStr }}</div>
    </div>

    <div class="btn" @click="recharge">
      <button>去充值￥{{ digit }} 元</button>
    </div>
    <!-- <hr> -->
  </div>
</template>

<script setup>
import { getSession } from '@/API/session';
import { changeUser } from '@/API/url';
import { showConfirmDialog } from 'vant';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
const store = useStore()
const router = useRouter()
const balance = ref(0)
const userId = computed(() => store.state.userModule.userId)
const active = ref(0)
let digit = ref(1)
const bool = ref(false)
const inputNum = ref("")
const errorStr = ref("请输入数字")
// 提交开关
const over = ref(true)
const list = ref([
  {
    num: 6,
  },
  {
    num: 18
  },
  {
    num: 68
  },
  {
    num: 233
  },
  {
    num: 648
  }, {
    num: 998
  }
])

// 选择输入
const changeNum = (value) => {
  list.value[active.value].bg = ""
  list.value[active.value].color = "color:grey"
  active.value = value;
  inputNum.value = ""
  list.value[active.value].bg = "background-color: aqua;color:white"
  list.value[active.value].color = "color:white"
  digit.value = list.value[active.value].num
  over.value =true

}
//自定义输入
const changeInput = () => {
  if (inputNum.value === "") {
    bool.value = false
    over.value = true
    digit.value = 1
  } else if (!Number(inputNum.value)&&Number(inputNum.value)!==0) {
    over.value = false
    errorStr.value = "请输入数字"
    bool.value = true
  } else if (Number(inputNum.value) < 1 || Number(inputNum.value) > 9999) {
    bool.value = true
    over.value = false
    errorStr.value = "输入金额不符合规定"
  } else {
    over.value = true
    list.value[active.value].bg = ""
    list.value[active.value].color = "color:grey"
    bool.value = false
    digit.value = Number(inputNum.value)
  }

}
// 充值前的函数
const beforeClose = (action) =>
  new Promise((resolve) => {
    setTimeout(() => {
      if (action === 'confirm') {
        changeUser({
          userId: userId.value,
          balance: balance.value + digit.value
        }).then(res => {
          if (res.code === 200) {
            resolve(true);
            store.commit("setPageTitle", "个人中心");
            store.commit("setBalance",balance.value + digit.value)
            router.go(-1)
          }
        })
      } else {
        // 拦截取消操作
        resolve(true);
      }
    }, 1000);
  });


// 充值
const recharge = () => {
  if(!over.value)return
  showConfirmDialog({
    title: '充值',
    message:
      `您的充值金额为${digit.value}元`,
    beforeClose,
  });
}
onMounted(() => {
balance.value = getSession("balance")
})
</script>


<style lang="scss">
.wrapper {
  position: relative;
  background-color: #FFFFFF;

  .balance {
    .balance_num {
      font-size: large;
      color: pink;
    }
  }

  .box {
    display: flex;
    flex-wrap: wrap;

    .item {
      box-sizing: border-box;
      border: #F7F7F7 5px solid;
      background-color: #FFFFFF;
      margin-left: 28px;
      margin-top: 15px;
      border-radius: 15%;
      width: 120px;
      height: 100px;
      text-align: center;
      padding-top: 20px;

      .num2 {
        color: grey;
      }
    }


  }

  .input {
    width: 90%;
    margin: 50px auto;

    // background-color: #dd0b82;
    input {
      width: 100%;
      border-color: transparent transparent aqua transparent;
      border-width: 0px 0 1px 0;
      // border: 1px solid pink;
    }
  }

  .btn {
    position: fixed;
    bottom: 10px;
    left: 0;
    right: 0;

    button {
      border-radius: 5%;
      color: #dd0b82;
      background-color: aqua;
      height: 50px;
      width: 400px;
    }
  }

}
</style>
