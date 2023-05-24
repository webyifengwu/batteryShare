<template>
    <div class="change_password">
        <div class="input">
            <span class="text1">原密码</span><input type="text" v-model="password1" placeholder="请填写原来的密码"
                @blur="changeInput1">
            <div style="color: red;text-align: left;text-indent: 55px; " v-show="bool1">{{ errorStr1 }}</div>
        </div>
        <div class="input">
            <span class="text2">新密码</span><input type="text" v-model="password2" placeholder="输入新的密码" @blur="changeInput2">
            <div style="color: red;text-align: left;text-indent: 55px;" v-show="bool2">{{ errorStr2 }}</div>
        </div>
        <div class="input">
            <span class="text3">确认密码</span><input type="text" v-model="password3" placeholder="确认密码" @blur="changeInput3">
            <div style="color: red;text-align: left;text-indent: 55px;" v-show="bool3">{{ errorStr3 }}</div>
        </div>
        <van-dialog id="van-dialog" />
        <div class="btn" @click="save">
            <button>
                保存
            </button>
        </div>
    </div>
</template>

<script setup>
import { showDialog } from 'vant';
import { changePassword } from '@/API/url';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore()
const router = useRouter()
const userId = ref("")
let password1 = ref("")
let password2 = ref("")
let password3 = ref("")
const errorStr1 = ref("请输入原密码")
const errorStr2 = ref("请输入新密码")
const errorStr3 = ref('请再次确认密码')
// 错误输入1
const bool1 = ref(false)
// 错误输入2
const bool2 = ref(false)
// 错误输入3
const bool3 = ref(false)
// 提交开关1
const over1 = ref(false)
// 提交开关2
const over2 = ref(false)
// 提交开关3
const over3 = ref(false)

//自定义输入1
const changeInput1 = () => {
    const passwordRegex = /^(?=.*[a-zA-Z])(?!.*[\u4E00-\u9FA5]).{6,10}$/;
    if (password1.value === '') {
        errorStr1.value = '请输入原密码'
        bool1.value = true
        over1.value = false
    } else if (!passwordRegex.test(password1.value)) {
        errorStr1.value = '请输入[6-10]位包含字母且不含中文的密码';
        bool1.value = true
        over1.value = false
    } else {
        over1.value = true
        bool1.value = false
    }

}
//自定义输入2
const changeInput2 = () => {
    const passwordRegex = /^(?=.*[a-zA-Z])(?!.*[\u4E00-\u9FA5]).{6,10}$/;
    if (password2.value === '') {
        errorStr2.value = '请输入新密码'
        bool2.value = true
        over2.value = false
    } else if (!passwordRegex.test(password2.value)) {
        errorStr2.value = '请输入[6-10]位包含字母且不含中文的密码';
        bool2.value = true
        over2.value = false
    } else {
        over2.value = true
        bool2.value = false
    }

}
//自定义输入3
const changeInput3 = () => {
    if (password2.value == "") return
    const passwordRegex = /^(?=.*[a-zA-Z])(?!.*[\u4E00-\u9FA5]).{6,10}$/;
    if (password3.value === '') {
        errorStr3.value = '请再次确认密码'
        bool3.value = true
        over3.value = false
    } else if (!passwordRegex.test(password3.value)) {
        errorStr3.value = '请输入[6-10]位包含字母且不含中文的密码';
        bool3.value = true
        over3.value = false
    } else if (password2.value != password3.value) {
        errorStr3.value = "密码输入不一致"
        bool3.value = true
        over3.value = false
    } else {
        over3.value = true
        bool3.value = false
    }

}
const save = async () => {
    if (bool1.value || bool2.value || bool3.value) return
    if (over1.value && over2.value && over3.value) {
        let res = await changePassword({
            oldPwd: password1.value,
            newPwd: password2.value
        })
        if (res.code === 200) {
            store.commit("deleteAllState")
            router.push({name:'login'})
        } else {
            showDialog({
                message: res.msg,
            }).then(() => {
                // on close
            });
        }
    }


}

onMounted(() => {
    userId.value = store.state.userModule.userId

})

</script>


<style lang="scss">
.change_password {

    .input {
        width: 100%;
        margin: 50px auto;

        // background-color: #dd0b82;
        input {
            margin-left: 30px;
            width: 60%;
            border-color: transparent transparent aqua transparent;
            border-width: 0px 0 1px 0;
        }

        .text1 {
            font-weight: 600;
        }

        .text2 {
            font-weight: 600;
        }

        .text3 {
            font-weight: 600;
            margin-right: -19px;
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
