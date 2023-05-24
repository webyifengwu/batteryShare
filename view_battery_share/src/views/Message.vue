<template>
    <div>
        <div class="nav">
            <van-nav-bar  :title="title" left-text="返回" left-arrow  @click-left="onClickLeft" />
        </div>
      

        <div class="box">
            <RouterView></RouterView>
        </div>
    </div>
</template>

<script setup>
import { computed, onMounted } from 'vue';
import { useRoute, useRouter } from "vue-router"
import { useStore } from 'vuex';
const route = useRoute()
const store  = useStore()
let title = computed(()=>store.state.pageModule.subPageTitle)
const router = useRouter()
const onClickLeft =()=>{
    router.go(-1)
}

onMounted(() => {
    if(route.query.title){
        store.commit("setSubPageTitle",route.query.title)
    }
})
</script>


<style lang="scss">
.nav{
    position: sticky;
    z-index: 999;
    top: 0;

}
</style>
