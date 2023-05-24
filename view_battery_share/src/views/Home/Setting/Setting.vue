<template>
  <div id="setting">

    <div class="user">
      <div class="avatar">
        <van-image round width="10rem" height="10rem" :src="avatar" />
        <div class="username">{{ username }}</div>
        <div class="balance">
          账户余额为：<span class="balance_num">{{ balance }}</span> 元
        </div>
      </div>

    </div>
    <div class="setting_wrapper">

      <div class="setting_item" @click="toPerson">
        <div class="setting_name"><van-icon name="manager"  />个人信息</div>
        <div class="icon"><van-icon name="arrow" /></div>
      </div>
      <div class="setting_item" @click="toRecharge">
        <div class="setting_name"><van-icon name="gold-coin"  />余额充值</div>
        <div class="icon"><van-icon name="arrow" /></div>
      </div>
      <div class="setting_item" @click="toTerm">
        <div class="setting_name"><van-icon name="service"  />联系客服</div>
        <div class="icon"><van-icon name="arrow" /></div>
      </div>

      <!-- <div class="setting_item">
          <div class="setting_name"><van-icon name="share" />分享</div>
          <div class="icon"><van-icon name="arrow" /></div>
        </div> -->
      <div class="setting_item" @click="toAbout">
        <div class="setting_name"><van-icon name="info"  />关于我们</div>
        <div class="icon"><van-icon name="arrow" /></div>
      </div>

      <div class="setting_item">
        <van-action-sheet v-model:show="show" :actions="actions" @select="onSelect" />
        <div class="exit" @click="show = true">退出</div>
      </div>


    </div>

    <div class="block"></div>

  </div>
</template>

<script setup>
import { showToast } from 'vant';
import { getSession } from '@/API/session';
import { uploadUrl } from "@/API/url";
import { computed } from "@vue/reactivity";
import { onMounted, ref } from "vue";
import { useStore } from "vuex"
import { useRouter } from 'vue-router';
const router =useRouter()
const store = useStore();
let avatar = computed(() => uploadUrl + store.state.userModule.avatar)
let username = computed(() => store.state.userModule.username)
let balance = ref(0)
const show = ref(false);
const actions = [
  { name: '退出登录' },
  { name: '取消' },
];

// 个人信息
const toPerson =()=>{
  store.commit("setSubPageTitle", "个人信息")
  router.push({ name: "person" })
}
// 充值中心
const toRecharge = ()=>{
  store.commit("setSubPageTitle", "充值中心")
  router.push({ name: "recharge" })
}
// 客服
const toTerm =  ()=>{
  store.commit("setSubPageTitle", "客服")
  router.push({ name: "term" })
}
// 关于
const toAbout = ()=>{
  store.commit("setSubPageTitle", "关于我们")
  router.push({ name: "about" })
}
// 退出登录
const onSelect = (item) => {
  show.value = false;
  if(item.name === "退出登录"){
    store.commit("deleteAllState");
    showToast(item.name+"成功！");
    router.push("/login")
  }

};

onMounted(() => {
  balance.value = getSession("balance")
});
</script>


<style lang="scss">
#setting {
  .user {
    .avatar {
      height: 290px;
      background-color: aqua;

      .van-image {
        margin-top: 30px;
      }

      .username {
        font-size: 25px;
        color: white;
      }

      .balance {
        .balance_num {
          font-size: 30px;
          font-weight: 500;
          color: rgb(243, 183, 228);
        }
      }
    }
  }

  .setting_wrapper {
    background-color: #F7F7F7;

    .setting_item {
      margin-top: 15px;
      height: 55px;
      background-color: #FFFFFF;
      display: flex;
      padding-left: 15px;
      padding-right: 15px;
      justify-content: space-between;
      line-height: 55px;
      font-size: 20px;

      .setting_name {}

      .exit {
        line-height: 45px;
        height: 45px;
        width: 100%;
        text-align: center;
        border: #F7F7F7 15px solid;
      }

      .icon {
        // font-size: 15px;

      }

    }

  }

}
</style>
