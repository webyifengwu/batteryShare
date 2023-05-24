<template>
    <div class="home">
        <van-nav-bar :title="title" :fixed="true">
        </van-nav-bar>
        <div class="box">

            <router-view />

        </div>

        <van-tabbar v-model="active" @change="changTabBar" :fixed="true">
            <van-tabbar-item icon="home-o">首页</van-tabbar-item>
            <van-tabbar-item icon="home-o">充电宝借/还</van-tabbar-item>
            <van-tabbar-item icon="setting-o">个人中心</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script setup>
import { Tabbar, NavBar ,TabbarItem } from 'vant';
import { useStore } from "vuex";
import { useRouter } from "vue-router"
import {  computed, onMounted} from "vue"
import { getUsers } from '@/API/url';
const router = useRouter();
const store = useStore();
const username = computed(()=>store.state.userModule.username)
let title = computed(() => store.state.pageModule.pageTitle);

let active = computed(()=>store.state.pageModule.active);

const changTabBar = (value) => {
    store.commit("setActive", value);
    if (value === 0) {
        store.commit("setPageTitle", "首页");
        router.push({ name: "page" });
    } else if (value === 1) {
        store.commit("setPageTitle", "借还");
        router.push({ name: "back" });
    } else {
        store.commit("setPageTitle", "个人中心");
        router.push({ name: "setting" });
    }
}
// 刷新用户信息
onMounted(async () => {
    let res = await  getUsers({username:username.value})
    if(res.code === 200){
        let {avatar,balance,phone} = res.data.list[0]
        store.commit("setAvatar",avatar)
        store.commit("setBalance",balance)
        store.commit("setPhone",phone)

    }
})
</script>


<style lang="scss" >
.home {

    // 展示部分

    // 导航栏
    .van-nav-bar {
        background-color: aqua;
        position: sticky;
        top: 0;
    }

    // tabbar标签栏
    .van-tabbar {
        border-top: 3px solid pink;
    }
}
</style>
