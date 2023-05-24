<template>
    <div>
        <div class="lNav" v-if="list">您附近有<span class="lNum">{{list.length}}</span>家投放点</div>
        <van-list v-model:loading="loading" :finished="finished" finished-text="没有更多了">
            <div v-for="(item, index) in list" :key="index" >
                <div class="item" @click="toBatteryList(item)">
                    <div class="left">
                    <div class="dpName">{{ item.dpName }}</div>
                    <div class="num">
                        <div>距离:  {{ item.distance }}</div>
                        <div>剩余充电宝:<span class="stock">{{ item.dpStock }}</span></div>
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
import { computed, onMounted, ref, watch } from 'vue';
import { getLocation } from "@/API/url"
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
const list = ref([]);
const router =useRouter()
const loading = ref(false);
const finished = ref(false);
const store = useStore()
const dpZone= computed(()=>store.state.userModule.zone)
watch(list,(newList)=>{
if(newList.length>0){
    // 加载状态结束
      loading.value = false;
    // 数据全部加载完成
    finished.value = true;
}
})
const toBatteryList = (value)=>{
    store.commit("setSubPageTitle","充电宝列表")

    router.push({name:"battery",query:value})
}
onMounted(async () => {
    let res = await getLocation({
        status:0,
        dpZone:dpZone.value
    })
    if(res.code === 200){
        list.value = res.data.map((item)=>{
            if(item.distance <1000){
                item.distance = `${item.distance}米`
            }else{
                let km = item.distance/1000
                // let m = item.distance%1000

                item.distance = `${km.toFixed(2)}公里`
            }
            return item
        })
    }

})
</script>


<style lang="scss">
.lNum{
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

      .left{
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
      .right{
        display: flex;
        align-items: center;
        color: #C1C1C1;
        // padding-top: 30px;
        font-size: 40px;
      }

    }
}
</style>
