<template>
    <div class="change_username">
        <div class="input">
            <input type="text" v-model="username" placeholder="输入新的用户名" @blur="changeInput">
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
import { changeUser, checkUsername } from '@/API/url';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore()
const router = useRouter()
let oldUsername = ref("")
const username = ref("")
const userId = ref("")
const errorStr = ref("该用户名已存在")
// 错误输入
const bool = ref(false)
// 提交开关
const over = ref(true)

//自定义输入
const changeInput = async () => {
    if (username.value == oldUsername.value) {
        bool.value = false
        over.value = false
        return
    }
    let res = await checkUsername({ username: username.value })
    if (res.code !== 200) {
        errorStr.value = res.msg
        bool.value = true
        over.value = false
    } else {
        over.value = true
        bool.value = false
    }

}
const save = async () => {
    if (bool.value) return


    if (over.value) {
        let res = await changeUser({
            username: username.value,
            userId: userId.value
        })
        if (res.code === 200) {
            store.commit("setUsername", username.value)
            router.push({ name: "setting" })
        }
    } else {
        router.push({ name: "setting" })
    }

}

onMounted(() => {
    oldUsername.value = store.state.userModule.username
    username.value = store.state.userModule.username
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
