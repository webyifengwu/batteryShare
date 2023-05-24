<template>
  <div class="register">
    <div class="registerbox">
      <h2>注册</h2>


      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field v-model="username" name="username" label="用户名" placeholder="用户名"
            :rules="[{ validator: validateUserName, message: '请填写用户名' }]" />
          <van-field v-model="password" type="text" name="password" label="密码" placeholder="密码"
            :rules="[{ validator: validatePassword, message: '请填写密码' }]" />
          <van-field v-model="phone" type="number" name="phone" label="电话号码" placeholder="电话号码"
            :rules="[{ validator: validatePhone, message: '请填写电话号码' }]" />

        </van-cell-group>
        <div style="margin: 16px auto;">
          <van-button round block type="primary" native-type="submit">
            提交
          </van-button>
        </div>
        <a @click="navigateToL" class="reg">已有账号？登录!</a>

      </van-form>

    </div>

  </div>
</template>

<script setup>
import { closeToast, showLoadingToast } from 'vant';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from "vuex"
import { addUser, checkUsername } from "@/API/url"
import { setSession } from "@/API/session"
const store = useStore()
const router = useRouter()
const username = ref('');
const password = ref('');
const phone = ref('');
const onSubmit = async (values) => {
  values.avatar = "defaultImg.png"
  values.balance = 0
  values.role = 0
  addUser(values).then(res => {
    if (res.code === 200) {
      store.commit("setUsername", username.value)
      setSession("token", res.data.token)

      store.commit("setAvatar", res.data.avatar)
      store.commit("setBalance", res.data.balance)
      store.commit("setPhone", res.data.phone)
      store.commit("setUserId", res.data.userId)
   


      store.commit("setZone", res.data.zone)
      store.commit("setActive", 0)
      store.commit("setPageTitle", "首页")
      router.push({ name: "page" })
    }

  })
};
const navigateToL = () => {
  router.push("/login")
}


//过滤器

const validatePassword = (value) => {
  const passwordRegex = /^(?=.*[a-zA-Z])(?!.*[\u4E00-\u9FA5]).{6,10}$/;
  if (value === '') {
    return '请输入密码';
  } else if (!passwordRegex.test(value)) {
    return '密码输入不规范:不能包含中文字符，必须包含字母,长度为6-10位';
  }
}
const validatePhone = (value) => {
  const phoneNumberRegex = /^1[3-9]\d{9}$/;
  if (value === '') {
    return '请输入电话号码';
  } else if (!phoneNumberRegex.test(value)) {
    return '电话输入错误';
  }
}

const validateUserName = async (value) => {
  showLoadingToast('验证中...');
  let res = await checkUsername({ username: value })
  if (res.code !== 200) {
    closeToast();
    return "用户名已存在"
  } else {
    closeToast();

  }

}



onMounted(() => {
  // store.commit("setWebKind","subpage")  

})
</script>


<style lang="scss" >
#app {

  .app {
    // height: 100vh;

    .register {
      height: 100%;
      // background-color: aqua;
      background: url('@/assets/img/bg1.jpg') no-repeat 0/cover;

      .registerbox {

        position: absolute;
        top: 300px;
        left: 0;
        right: 0;
        bottom: 0;
        margin: auto;

        h2 {
          text-align: center;
        }

        .van-button {
          margin-left: 90px;
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

}</style>
