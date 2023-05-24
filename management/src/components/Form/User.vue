<template>
    <a-page-header style="border: 1px solid rgb(235, 237, 240)" title="返回" sub-title="用户信息界面" @back="() => router.go(-1)" />

    <a-form :model="formState" v-bind="layout" name="nest-messages" :validate-messages="validateMessages"
        @finish="onFinish">

        <a-form-item :name="['user', 'username']" label="用户名"
            :rules="[{ required: true, validator: validateUserName, trigger: `blur` }]">
            <a-input v-model:value="formState.user.username" />
        </a-form-item>
        <a-form-item :name="['user', 'password']" label="密码"
            :rules="[{ required: true, validator: validatePassword, trigger: `blur` }]">
            <a-input-password v-model:value="formState.user.password" placeholder="输入密码" />
        </a-form-item>

        <a-form-item :name="['user', 'phone']" label="电话号码"
            :rules="[{ required: true, validator: validatePhone, trigger: `blur` }]">
            <a-input v-model:value="formState.user.phone" placeholder="输入电话号码" />
        </a-form-item>

        <a-form-item :name="['user', 'balance']" label="余额" :rules="[{ required:true,type: 'number', min: 0, max: 9999 }]">
            <a-input-number v-model:value="formState.user.balance" />/元
        </a-form-item>

        <a-form-item :name="['user', 'role']" label="角色身份" :rules="[{ required: true }]">
            <a-select v-model:value="formState.user.role" style="width: 120px" :options="roleOptions">
            </a-select>
        </a-form-item>

        <a-form-item :name="['user', 'avatar']" label="头像">

            <!-- <a-upload-dragger v-model:fileList="fileList" name="photo" :multiple="true" :action="uploadUrl"
                @change="handleChange" @drop="handleDrop" maxCount="1">
                <p class="ant-upload-drag-icon">
                    <inbox-outlined></inbox-outlined>
                </p>
                <p class="ant-upload-text">上传用户头像</p>
                <p class="ant-upload-hint">
                    将图片拖拽到这里
                </p>
            </a-upload-dragger> -->

            <div class="clearfix">
                <a-upload v-model:file-list="fileList" :action="uploadUrl" name="photo" list-type="picture-card"
                    @preview="handlePreview" @change="handleChange">
                    <div v-if="fileList.length < 1">
                        <plus-outlined />
                        <div style="margin-top: 8px">上传用户头像</div>
                    </div>
                </a-upload>

                <a-modal :visible="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
                    <img alt="example" style="width: 100%" :src="previewImage" />
                </a-modal>
            </div>

        </a-form-item>


        <a-form-item :wrapper-col="{ ...layout.wrapperCol, offset: 10 }">
            <a-button type="primary" html-type="submit" size="large">{{ btnStr }}</a-button>
        </a-form-item>

        <a-form-item :name="['user', 'userId']">
        </a-form-item>
    </a-form>
</template>
<script setup>
import axios from "axios"
import { uploadUrl } from "@/API/url"
import { addUser, changeUser } from "@/API/url"
import { message } from 'ant-design-vue';
import { useRouter, useRoute } from "vue-router"
import { reactive, onMounted, ref } from 'vue';
// 数据
const previewVisible = ref(false);
const previewImage = ref('');
const previewTitle = ref('');
const fileList = ref([{
    uid: '-1',
    name: 'image.png',
    status: 'done',
    url: uploadUrl + 'defaultImg.png',
}]);

const btnStr = ref("注册")
const router = useRouter()
const route = useRoute()
const layout = {
    labelCol: {
        span: 6,
    },
    wrapperCol: {
        span: 10,
    },
};
// 角色选项
const roleOptions = [{ value: "1", label: "管理员" }, {
    value: "0", label: "普通用户"
}]
const validateMessages = {
    required: '${label}不能为空',
    types: {
        number: ''
    },
    number: {
        range: '${label} 必须大于0并且小于9999',
    },
};
// 表单数据
const formState = reactive({
    user: {
        username: '',
        password: "",
        phone: "",
        balance: 0,
        role: "0",
        avatar: 'defaultImg.png',
        userId: null
    },
});

// 函数

//图片上传
const handleChange = info => {
    const status = info.file.status;
    if (status !== 'uploading') {
    }
    if (status === 'done') {
        message.success(`${info.file.name} 上传成功`);
        formState.user.avatar = `${info.file.response.data}`
    } else if (status === 'error') {
        message.error(`${info.file.name} 上传失败`);
    }
};

const handleCancel = () => {
    previewVisible.value = false;
    previewTitle.value = '';
};

const handlePreview = async file => {
    if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj);
    }
    previewImage.value = file.url || file.preview;
    previewVisible.value = true;
    previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
};


function getBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}




//过滤器

const validatePassword = async (_rule, value) => {
    const passwordRegex = /^(?=.*[a-zA-Z])(?!.*[\u4E00-\u9FA5]).{6,10}$/;
    if (value === '') {
        return Promise.reject('请输入密码');
    } else if (!passwordRegex.test(value)) {
        return Promise.reject('密码输入不规范:不能包含中文字符，必须包含字母,长度为6-10位');
    } else {
        return Promise.resolve();
    }
}
const validatePhone = async (_rule, value) => {
    const phoneNumberRegex = /^1[3-9]\d{9}$/;
    if (value === '') {
        return Promise.reject('请输入电话号码');
    } else if (!phoneNumberRegex.test(value)) {
        return Promise.reject('电话输入错误');
    } else {
        return Promise.resolve();
    }
}

const validateUserName = async (_rule, value) => {

    let res = {};
    if(value === ""){
        return Promise.reject("用户名不能为空")
    }else if (btnStr.value === "注册") {
        res = await axios.get("http://localhost:8888/powerbank/user/check/" + value)
    } else {
        res = await axios.get("http://localhost:8888/powerbank/user/check/" + value + '?userId=' + formState.user.userId)
    }
    if (res.data.code !== 200) {
        return Promise.reject("用户名已存在")
    } else {
        return Promise.resolve()
    }

}


// 提交
const onFinish = async (values) => {
    let res = null;
    if (btnStr.value === "注册") {
        res = await addUser(values.user)
    } else {
        res = await changeUser(values.user)
    }
    if (res.code === 200) {
        router.push({ name: "user" })
    }
};


onMounted(() => {
    if (Object.keys(route.query).length) {
        btnStr.value = "修改"
        fileList.value[0].url = route.query.avatar
        for (const key in formState.user) {
            if (key === "balance") {
                formState.user[key] = Number.parseFloat(route.query[key])
            } else if (key === "avatar") {
                formState.user[key] = route.query[key].split(uploadUrl)[1]
            } else {
                formState.user[key] = route.query[key]
            }
        }
    } else {
        btnStr.value = "注册"
    }
})
</script>