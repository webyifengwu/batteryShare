<template>
    <a-page-header style="border: 1px solid rgb(235, 237, 240)" title="返回" sub-title="充电宝管理界面"
        @back="() => router.go(-1)" />

    <a-form :model="formState" v-bind="layout" name="nest-messages" :validate-messages="validateMessages"
        @finish="onFinish">

        <a-form-item :name="['battery', 'dpId']" label="投放点" data-index="dpId" :rules="[{ required: true }]">
            <a-select v-model:value="formState.battery.dpId" style="width: 100%" placeholder="投放点"
                :options="locationArr"></a-select>
        </a-form-item>
        <a-form-item :name="['battery', 'brand']" label="品牌" data-index="brand" :rules="[{ required: true }]">
            <a-select v-model:value="formState.battery.brand" style="width: 100%" placeholder="品牌"
                :options="brandArr"></a-select>
        </a-form-item>

        <a-form-item :name="['battery', 'total']" label="定金" data-index="total"
            :rules="[{ required: true, type: 'number', min: 50, max: 300 }]">
            <a-input-number v-model:value="formState.battery.total" />/元
        </a-form-item>
        <a-form-item :name="['battery', 'price']" label="租金" data-index="price"
            :rules="[{ required: true, type: 'number', min: 0, max: 30 }]">
            <a-input-number v-model:value="formState.battery.price" />/小时
        </a-form-item>

        <!-- <a-form-item :name="['battery', 'status']" label="状态" data-index="status" >
            <a-select v-model:value="formState.battery.status" style="width: 100%" placeholder="充电宝状态" :options="statusArr">

            </a-select>
        </a-form-item> -->

        <a-form-item :name="['battery', 'img']" label="充电宝图片">
            <div class="clearfix">
                <a-upload v-model:file-list="fileList" :action="uploadUrl" name="photo" list-type="picture-card"
                    @preview="handlePreview" @change="handleChange">
                    <div v-if="fileList.length < 1">
                        <plus-outlined />
                        <div style="margin-top: 8px">上传充电宝图片</div>
                    </div>
                </a-upload>

                <a-modal :visible="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
                    <img alt="example" style="width: 100%" :src="previewImage" />
                </a-modal>
            </div>

        </a-form-item>

        <a-form-item :wrapper-col="{ ...layout.wrapperCol, offset: 8 }">
            <a-button type="primary" html-type="submit">{{ btnStr }}</a-button>
        </a-form-item>
        <a-form-item :name="['battery', 'pId']" data-index="pId">

        </a-form-item>
    </a-form>
</template>
<script setup>
import {
    addBattery, changeBattery, uploadUrl, getAllLication
} from "@/API/url"
import { message } from 'ant-design-vue';
import { useRouter, useRoute } from "vue-router"
import { reactive, ref, onMounted } from 'vue';
const router = useRouter()
const route = useRoute()
const previewVisible = ref(false);
const previewImage = ref('');
const previewTitle = ref('');
const fileList = ref([{
    uid: '-1',
    name: 'image.png',
    status: 'done',
    url: uploadUrl + '/小米.png',
}]);
const btnStr = ref("添加充电宝")


const layout = {
    labelCol: {
        span: 6,
    },
    wrapperCol: {
        span: 10,
    },
};
const validateMessages = {
    required: '${label}不能为空!',
    types: {
        number: '${label}必须是数字',
    },
    number: {
        range: '${label} must be between ${min} and ${max}',
    },
};
// 投放点列表
const locationArr = ref([
    {
        value: "l30",
        label: "云浮"
    }
])
const brandArr = ref([
    {
        value: "小米",
        label: "小米"
    },
    {
        value: "Pony",
        label: "Pony"
    },
    {
        value: "炫美科",
        label: "炫美科"
    },
    {
        value: "爱国者",
        label: "爱国者"
    },
    {
        value: "索尼",
        label: "索尼"
    },
    {
        value: "罗马仕",
        label: "罗马仕"
    }, {
        value: "飞利浦",
        label: "飞利浦"
    }
])
// 租借状态列表
const statusArr = [
    {
        value: "1",
        label: "可租借"
    }, {
        value: "0",
        label: "租出中"
    }
]
// 表单数据
const formState = reactive({
    battery: {
        brand: "小米",
        price: 0.5,
        total: 100,
        // status:"1",
        dpId: '',
        pId: null,
        img: '/小米.png',
    },
});



//图片上传
const handleChange = info => {
    const status = info.file.status;
    if (status !== 'uploading') {
    }
    if (status === 'done') {
        message.success(`${info.file.name} 上传成功`);
        formState.battery.img = `${info.file.response.data}`
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

// 提交.
const onFinish = async (values) => {
    let res = null
    if (btnStr.value === "添加充电宝") {
        res = await addBattery(values.battery)
    } else {
        res = await changeBattery(values.battery)
    }
    if (res.code === 200) {
        router.push({ name: "battery" })
    }
};



onMounted(async () => {
    let res = await getAllLication()
    if (res.code === 200) {
        let arr = res.data.list
        let index_z = 0;
        for (let index = 0; index < arr.length; index++) {
            // if (arr[index].dpAll === arr[index].dpStock)
            //     continue
            locationArr.value[index_z++] = {
                value: arr[index].dpId,
                label: arr[index].dpName
            }
        }

    }
    if (Object.keys(route.query).length) {
        btnStr.value = "修改充电宝信息"
        fileList.value[0].url = route.query.img
        for (const key in formState.battery) {
            if (key === "price" || key === "total") {
                formState.battery[key] = Number.parseFloat(route.query[key])
            } else if (key === "img") {
                formState.battery[key] = route.query[key].split(uploadUrl)[1]
            } else {
                formState.battery[key] = route.query[key]
            }
        }
    }
})


</script>