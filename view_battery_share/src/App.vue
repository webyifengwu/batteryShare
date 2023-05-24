<template>
  <div class="app">
    <router-view></router-view>
  </div>
</template>
<script setup>
import "vant/lib/index.css";
import { onMounted } from "vue";
import { getSession } from "@/API/session"
import { easyLogin } from "@/API/url"
import { useRouter } from "vue-router"
import { useStore } from "vuex"
const store = useStore()
const router = useRouter()
const token = {
  token: getSession("token")
}
// 页面刷新 获取新位置
onMounted(() => {
  if (token) {
    easyLogin(token).then(res => {
      if (res.code === 200) {
        router.push({ name: "page" })
        store.commit("setActive", 0);
        store.commit("setPageTitle", "首页");
        store.commit("setZone",res.data.zone)
      } else {
        router.push("/login")
      }
    })
  } else {
    router.push("/login")
  }

})
</script>
<style lang="scss">
#app{
 height: 100vh;
 .app {
  height: 100%;


  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  // background-color: pink;
}
}
</style>
