<template>
  <a-layout>
    <a-layout-sider width="258" style="height: 100vh;" >
      <Sider></Sider>
    </a-layout-sider>
    <a-layout>
      <a-layout-header>
        <span class="title">欢迎使用充电宝后台管理系统</span>
        <a-popconfirm title="确认退出?" ok-text="确定" cancel-text="取消" @confirm="confirm"
          @cancel="cancel" class="exit">
          <span >退出登录</span>
        </a-popconfirm>
      </a-layout-header>
      <a-layout-content>
        <RouterView></RouterView>
      </a-layout-content>
      <!-- <a-layout-footer>Footer</a-layout-footer> -->
    </a-layout>
  </a-layout>
</template>
<script setup>
import Sider from '@/components/sider/Sider.vue';
import {removeSession} from "@/API/session"
import {useRouter} from "vue-router"
const router = useRouter()
const confirm = ()=>{
removeSession("token")
router.push({name:"login"})
}
</script>
<style lang="scss">
.ant-layout {
  height: 100vh;

  .title {
    font-size: medium;
    color: white;
  }
  .exit{
    position: absolute;
    right: 5vh;
    color: salmon;
  }

}
</style>