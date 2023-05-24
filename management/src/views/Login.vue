<template>
  <div class="loginBox">
    <h2>LOGIN</h2>
    <a-form :model="formState" name="basic" :label-col="{ span: 8 }" :wrapper-col="{ span: 10 }" autocomplete="off"
      @finish="onFinish" @finishFailed="onFinishFailed">
      <a-form-item  label="用户名" name="username" :rules="[{ required: true, message: 'Please input your username!' }]">
        <a-input v-model:value="formState.username" />
      </a-form-item>

      <a-form-item label="用户密码" name="password" :rules="[{ required: true, message: 'Please input your password!' }]">
        <a-input-password  v-model:value="formState.password" />
      </a-form-item>
      <a-form-item :wrapper-col="{ offset: 9, span: 16 }">
        <a-button style="width:170px;background-color: #695545; opacity: 0.5;margin-top: 50px;" type="primary" html-type="submit">登录</a-button>
      </a-form-item>
    </a-form>

  </div>
</template>

<script setup>
import { reactive } from 'vue';
import { login } from "@/API/url"
import { setSession } from "@/API/session"
import { useRouter } from "vue-router"
const formState = reactive({
  username: '',
  password: '',
  // remember: true,
});
const router = useRouter()
const onFinish = async (values) => {
  login(values).then(res => {
    if (res.code === 200) {
      setSession("token", res.data.token)
      router.push({ name: "page" })
    }

  })
};
const onFinishFailed = errorInfo => {
};
</script>


<style lang="scss" >
#app {
  height: 100vh;
  background:url("@/assets/img/beautiful-nature.jpg") no-repeat 0/cover;
  .loginBox {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    width: 700px;
    height: 350px;
    opacity: 0.5;
    padding: 50px 50px 50px 50px;
    background-color: #121527;
    border: 1px solid #172844;
    border-radius: 5% 5% 50% 50%;
    .ant-form-item-required{
      color: white;
    }
    h2 {
      text-align: center;
      color: white;
    }
  }
}
</style>
