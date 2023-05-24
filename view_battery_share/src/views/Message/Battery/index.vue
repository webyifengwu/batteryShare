<template>
    <div>
        <ul class="battery_box">
            <li v-for="(item, index) in batteryList" class="item">
                <div class="left">
                    <img :src="uploadUrl + item.img" width="100" height="100" alt="">
                </div>
                <div class="right">
                    <div class="introduce">
                        <div class="brand">品牌：<span class="brand_name">{{ item.brand }}</span></div>
                        <div class="price">租金：<span class="price_num">{{ item.price }}</span>元/小时</div>
                    </div>
                    <div class="status">
                        <button :style="bgcolor(item.status)" @click="rent(item.pId)">{{ item.status ? "租用" : "已被租出" }}</button>
                    </div>
                </div>

            </li>
        </ul>
    </div>
</template>

<script setup>

import { getBattery, uploadUrl, addPay } from '@/API/url';
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';
const store = useStore()
const router = useRouter()
const userId = computed(() => store.state.userModule.userId)
const route = useRoute()
const dpIdN = ref("")
const batteryList = ref([])
const bgcolor = (value) => {
    if (value === 1) {
        return { 'background-color': '#2eee95' }
    } else {
        return { 'background-color': '#e2f7ed' }
    }
}
// 租用
const rent = async (value) => {
    let res = await addPay({
        dpId: dpIdN.value,
        userId: userId.value,
        pId: value
    })
    if(res.code === 200){
        store.commit("setPageTitle", "借还");
        router.push({name:"back"})
        store.commit("setActive", 1);

    }
}
onMounted(async () => {
    let {  dpId } = route.query
    dpIdN.value = dpId
    let res = await getBattery({ dpId:dpId })
    if (res.code === 200) {
        batteryList.value = res.data
    }
})
</script>


<style lang="scss">
.battery_box {
    background-color: #F7F7F7;

    .item {
        margin-top: 10px;
        display: flex;
        background-color: #FFFFFF;
        width: 100%;
        margin-bottom: 10px;

        .left {
            width: 100px;
            height: 100px;
        }

        .right {
            flex: 1;
            background-color: #e2f7ed;
            display: flex;
            justify-content: space-around;

            .introduce {

                // flex: 1;
                display: flex;
                flex-direction: column;
                justify-content: space-around;

                .brand {
                    text-align: left;

                    .brand_name {
                        font-weight: 800;
                    }
                }

                .price {
                    text-align: left;

                    .price_num {
                        color: aquamarine;
                    }
                }
            }

            .status {
                box-sizing: border-box;

                button {
                    margin-top: 30px;

                    width: 80px;
                    border-radius: 15%;
                    height: 40px;
                }
            }
        }

    }
}</style>
