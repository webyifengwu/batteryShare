<template>
  <div class="login">
    <div class="loginBox">
      <h2>登录</h2>
      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field v-model="userName" name="username" label="用户名" placeholder="用户名"
            :rules="[{ required: true, message: '请填写用户名' }]" />
          <van-field v-model="password" type="password" name="password" label="密码" placeholder="密码"
            :rules="[{ required: true, message: '请填写密码' }]" />
        </van-cell-group>
        <div >
          <van-button round block type="primary" native-type="submit">
            登录
          </van-button>
          <a @click="navigateToR" class="reg">没有账号？注册!</a>
        </div>
      </van-form>
    </div>
  </div>
</template>

<script setup>
import { showDialog } from 'vant';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from "vuex"
import { login } from "@/API/url"
import { setSession } from "@/API/session"
const store = useStore()
const router = useRouter()
const userName = ref('');
const password = ref('');
const onSubmit = async (values) => {
  login(values).then(res => {
    if (res.code === 200) {
      store.commit("setAvatar", res.data.avatar)
      store.commit("setBalance", res.data.balance)
      store.commit("setPhone", res.data.phone)
      store.commit("setUserId", res.data.userId)
      setSession("token", res.data.token)
      store.commit("setZone", res.data.zone)
      store.commit("setUsername", res.data.username)
      store.commit("setActive", 0)
      store.commit("setPageTitle", "首页")
      router.push({ name: "page" })
    } else {
      showDialog({
        message: res.msg,
      }).then(() => {
        // on close
      });
    }

  })
};
const navigateToR = () => {
  router.push("/register")
}
onMounted(() => {

})
</script>


<style lang="scss" >
#app {

  .app {
    // height: 100vh;

    .login {
      height: 100%;
      // background-color: aqua;
      background: url('@/assets/img/bg1.jpg') no-repeat 0/cover;

      .loginBox {

        position: absolute;
        top: 300px;
        left: 0;
        right: 0;
        bottom: 0;
        margin: auto;

        h2 {
          text-align: center;
          color: pink;
        }

        .van-button {
          margin: 30px auto 0;
          width: 300px;
          // letter-spacing: 20px;

        }

        .reg {
          color: pink;
          position: absolute;
          right: 50px;
        }
      }
    }
  }

}
</style>
