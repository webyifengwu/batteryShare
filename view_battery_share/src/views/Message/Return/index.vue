<template>
    <div>
        <div class="lNav" v-if="list">您附近有<span class="lNum">{{ list.length }}</span>家可用充电点</div>
        <van-list v-model:loading="loading" :finished="finished" finished-text="没有更多了">
            <div v-for="(item, index) in list" :key="index">
                <div class="item" @click="finshOrder(item.dpId)">
                    <div class="left">
                        <div class="dpName">{{ item.dpName }}</div>
                        <div class="num">
                            <div>距离: {{ item.distance }}</div>
                            <div>剩余空位:<span class="stock">{{ item.dpAll - item.dpStock }}</span></div>
                        </div>
                    </div>
                    <div class="right">
                        <van-icon name="arrow" />
                    </div>
                </div>
            </div>
        </van-list>

    </div>
</template>

<script setup>
import { showConfirmDialog } from 'vant';
import { computed, onMounted, ref, watch } from 'vue';
import { getCost, finshPay, getLocation } from "@/API/url"
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';
const list = ref([]);
const router = useRouter()
const loading = ref(false);
const finished = ref(false);
const store = useStore()
const route = useRoute()
let orderId = ref("")
const newDpId = ref("")
// const show = ref(false);
const num = ref(0);
const dpZone = computed(() => store.state.userModule.zone)
const createRandNum = (num1, num2) => {
    return Math.floor(Math.random() * (num2 - num1)) + num1
}
const finshOrder = async (dpId) => {
    newDpId.value = dpId
    let cost = await getCost({
        orderId: orderId.value,
        time: num.value
    })
    if (cost.code === 200) {
        showConfirmDialog({
            title: '订单提交',
            message:
                `您使用的时间为${num.value}小时,花费为${cost.data}元`,
            beforeClose
        })

    }
}

const beforeClose = (action) => {
    return new Promise((resolve) => {
        setTimeout(() => {
            if (action === 'confirm') {
                finshPay({
                    orderId: orderId.value,
                    newDpId: newDpId.value,
                    time: num.value
                }).then((res) => {
                    if (res.code === 200) {
                        resolve(true);
                        router.push({ name: "back" })
                    } else {
                        resolve(false);

                        showConfirmDialog({
                            title: '余额不足',
                            message:
                                res.msg,
                        })
                            .then(() => {
                                store.commit("setSubPageTitle", "充值中心")
                                router.push({ name: "recharge" })
                                resolve(true);
                                // on confirm
                            })
                            .catch(() => {
                                // on cancel

                            });

                    }

                })

            } else {
                // 拦截取消操作
                resolve(true);
            }
        }, 1000);
    }).catch((err) => {
        throw err
    });
}


watch(list, (newList) => {
    if (newList.length > 0) {
        // 加载状态结束
        loading.value = false;
        // 数据全部加载完成
        finished.value = true;
    }
})


onMounted(async () => {
    num.value = createRandNum(1, 24)
    let res = await getLocation({
        status: 1,
        dpZone: dpZone.value
    })
    if (route.query) {
        orderId.value = route.query.orderId
    }
    if (res.code === 200) {
        list.value = res.data.map((item) => {
            if (item.distance < 1000) {
                item.distance = `${item.distance}米`
            } else {
                let km = item.distance / 1000
                item.distance = `${km.toFixed(2)}公里`
            }
            return item
        })
    }


})
</script>


<style lang="scss">
.lNum {
    color: #0cdf3a;
}

.van-list {
    background-color: #F7F7F7;

    .item {
        display: flex;
        background-color: #FFFFFF;
        width: 90%;
        height: 100px;
        // border: 1px solid;
        margin: 10px 10% 10px 5%;

        .left {
            width: 90%;

            .dpName {
                text-align: left;
                text-indent: 15px;
                font-size: 25px;
            }

            .num {
                color: #C1C1C1;

                margin-top: 30px;
                display: flex;
                justify-content: space-around;

                .stock {
                    color: #0cdf3a;
                }
            }
        }

        .right {
            display: flex;
            align-items: center;
            color: #C1C1C1;
            // padding-top: 30px;
            font-size: 40px;
        }

    }
}
</style>
