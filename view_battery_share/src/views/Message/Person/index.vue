<template>
  <div class="setting">

    <div class="avatar" @click="changeAvatar">
      <div class="text">
        头像
      </div>
      <div class="pic">
        <img :src="avatar" width="60" height="60"><van-icon name="arrow" class="avatarA" />
      </div>
    </div>

    <div class="username" @click="changeUsername">
      <div class="text">
        用户名
      </div>
      <div class="name">
        {{ username }} <van-icon name="arrow" />
      </div>
    </div>

    <div class="phone" @click="changePhone">
      <div class="text">
        电话号码
      </div>
      <div class="phone_num">
        {{ phone }} <van-icon name="arrow" />
      </div>
    </div>

    <div class="password" @click="changePassword">
      <div class="text">
        修改密码
      </div>
      <div class="phone_num">
        <van-icon name="arrow" />
      </div>
    </div>



  </div>
</template>

<script setup>
import { uploadUrl } from '@/API/url';
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
const router = useRouter()
const store = useStore()
const avatar = computed(() => uploadUrl + store.state.userModule.avatar)
const username = computed(() => store.state.userModule.username)
const phone = computed(() => store.state.userModule.phone)

const changeAvatar = ()=>{
  store.commit("setSubPageTitle","修改头像")

router.push({name:"avatar"})
}
const changeUsername =()=>{
  store.commit("setSubPageTitle","修改用户名")

  router.push({name:"username"})
}
const changePhone = ()=>{
  store.commit("setSubPageTitle","修改手机号码")

  router.push({name:"phone"})

}
const changePassword = ()=>{
  store.commit("setSubPageTitle","修改密码")

  router.push({name:"password"})

}



</script>


<style lang="scss">
.setting {
  background-color: #F7F7F7;

  .avatar {
    padding-left: 10px;
    padding-right: 10px;
    background-color: #FFFFFF;
    border-bottom: 1px solid #F7F7F7;
    display: flex;
    justify-content: space-between;

    .text {
      line-height: 60px;
    }

    .pic {
      color: #ddcdcd;

      .van-badge__wrapper {
        vertical-align: middle;
        height: 100%;

      }

    }

  }

  .username {
    padding-left: 10px;
    padding-right: 10px;
    line-height: 60px;
    height: 60px;
    background-color: #FFFFFF;
    border-bottom: 1px solid #F7F7F7;

    display: flex;
    justify-content: space-between;

    .name {
      color: #ddcdcd;
    }

  }

  .phone {
    padding-left: 10px;
    padding-right: 10px;
    line-height: 60px;
    height: 60px;
    background-color: #FFFFFF;
    border-bottom: 1px solid #F7F7F7;
    display: flex;
    justify-content: space-between;

    .phone_num {
      color: #ddcdcd;
    }
  }

  .password {
    padding-left: 10px;
    padding-right: 10px;
    line-height: 60px;
    height: 60px;
    background-color: #FFFFFF;
    border-bottom: 1px solid #F7F7F7;
    display: flex;
    justify-content: space-between;

    .phone_num {
      color: #ddcdcd;
    }
  }

}</style>
