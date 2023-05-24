<template>
  <router-view/>
</template>
<script setup>
import {useRouter} from "vue-router"
import {onMounted} from "vue"
import {getSession} from "@/API/session"
import {easyLogin} from "@/API/url"
const router = useRouter()
const token ={
  token: getSession("token")
}

//函数


// 开始
onMounted(()=>{
  if(token){
    easyLogin(token).then(res=>{
      if(res.code === 200){
        router.push({name:"page"})
      }else{
        router.push("/login")
      }
    })
  }else{
    router.push("/login")
  }



})
</script>

<style lang="scss">

</style>
