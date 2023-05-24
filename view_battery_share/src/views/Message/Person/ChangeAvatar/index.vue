<template>
    <div class="change_avatar">

        <!-- 不指定单位，默认为 px -->
        <van-uploader v-model="fileList" :after-read="afterRead" :max-count="1" preview-size="400px" />

        <div class="btn" @click="save">
            <button>
                保存
            </button>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios';
import { changeUser, uploadUrl } from '@/API/url';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
const store = useStore()
const router = useRouter()
let oldAvatar = ref("")
const avatar = ref("")
const userId = ref("")


const fileList = ref([{
    url: ""
}]);

const afterRead = async (file) => {
    const forms = new FormData();
    //这里的file表示给后台传的属性名字，这里看接口文档需要传的的属性
    forms.append("photo", file.file); // 获取上传图片信息
    let res = await axios.post(uploadUrl, forms, {
        headers: {
            "content-type": "multipart/form-data",
        }
    })
    avatar.value = res.data.data
}


//自定义输入

const save = async () => {
    if (avatar.value == oldAvatar.value) {
        router.push({ name: "setting" })
    } else {

        let res = await changeUser({
            avatar: avatar.value,
            userId: userId.value
        })
        if (res.code === 200) {
            store.commit("setAvatar", avatar.value)
            router.push({ name: "setting" })
        }
    }


}

onMounted(() => {
    oldAvatar.value = store.state.userModule.avatar
    avatar.value = store.state.userModule.avatar
    userId.value = store.state.userModule.userId
    fileList.value[0].url = uploadUrl + store.state.userModule.avatar

})

</script>


<style lang="scss">
.change_avatar {
    .van-uploader{
        margin-top: 10vh;
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
