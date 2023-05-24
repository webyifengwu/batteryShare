<template>
    <div class="change_username">
        <div class="input">
            <input type="text" v-model="phone" placeholder="输入新的电话号码" @blur="changeInput">
            <div style="color: red;text-align: left;" v-show="bool">{{ errorStr }}</div>
        </div>

        <div class="btn" @click="save">
            <button>
                保存
            </button>
        </div>
    </div>
</template>

<script setup>
import { changeUser } from '@/API/url';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore()
const router = useRouter()
let oldPhone = ref("")
const phone = ref("")
const userId = ref("")
const errorStr = ref("请输入电话号码")
// 错误输入
const bool = ref(false)
// 提交开关
const over = ref(true)

//自定义输入
const changeInput = () => {
    const phoneNumberRegex = /^1[3-9]\d{9}$/;

    if (phone.value == oldPhone.value) {
        bool.value = false
        over.value = false
        return
    }

    if (phone.value === '') {
        errorStr.value = '请输入手机号码'
        bool.value = true
        over.value = false
    } else if (!phoneNumberRegex.test(phone.value)) {
        errorStr.value = '手机号码格式错误'
        bool.value = true
        over.value = false
    }else {
        over.value = true
        bool.value = false
    }

}
const save = async () => {
    if (bool.value) return


    if (over.value) {
        let res = await changeUser({
            phone: phone.value,
            userId: userId.value
        })
        if (res.code === 200) {
            store.commit("setPhone", phone.value)
            router.push({ name: "setting" })
        }
    } else {
        router.push({ name: "setting" })
    }

}

onMounted(() => {
    oldPhone.value = store.state.userModule.phone
    phone.value = store.state.userModule.phone
    userId.value = store.state.userModule.userId

})

</script>


<style lang="scss">
.change_username {

    .input {
        width: 90%;
        margin: 50px auto;

        // background-color: #dd0b82;
        input {
            width: 100%;
            border-color: transparent transparent aqua transparent;
            border-width: 0px 0 1px 0;
            // border: 1px solid pink;
        }
    }

    .btn {

        width: 100%;
        position: fixed;
        bottom: 0;
        left: 0;
        right: 0;

        button {
            background-color: aqua;
            height: 50px;
            width: 100%;
        }
    }

}
</style>
