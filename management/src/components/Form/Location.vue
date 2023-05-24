<template>
    <a-page-header style="border: 1px solid rgb(235, 237, 240)" title="返回" sub-title="充电宝投放点管理界面"
        @back="() => router.go(-1)" />

    <a-form :model="formState" v-bind="layout" name="nest-messages" :validate-messages="validateMessages"
        @finish="onFinish">
        <a-form-item :name="['location', 'dpName']" label="投放点名" :rules="[{ required: true }]">
            <a-input v-model:value="formState.location.dpName" />
        </a-form-item>
        <a-form-item :name="['location', 'dpZone']" label="所属区域" :rules="[{ required: true }]">
            <a-input v-model:value="formState.location.dpZone" />
        </a-form-item>

        <a-form-item :name="['location', 'dpAll']" label="可存放充电宝块数" :rules="[{ required: true,type: 'number',max: 15, min: 1 }]">
            <a-input-number v-model:value="formState.location.dpAll" />/块
        </a-form-item>


        <a-form-item :wrapper-col="{ ...layout.wrapperCol, offset: 8 }">
            <a-button type="primary" html-type="submit">{{ btnStr }}</a-button>
        </a-form-item>

        <a-form-item :name="['location', 'dpId']">
        </a-form-item>
    </a-form>
</template>
<script setup>
import { useRouter, useRoute } from "vue-router"
import { addLocation, changeLocation } from "@/API/url"
import { reactive, onMounted, ref } from 'vue';
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
const btnStr = ref("提交")

const validateMessages = {
    required: '${label}不能为空!',
    types: {
        number: '${label}必须为数字!',
    },
    number: {
        range: '${label} 最小0 最大15',
    },
};
const formState = reactive({
    location: {
        dpName: '',
        dpZone: '',
        dpStock: 0,
        dpAll: 0,
        dpId: ''
    }
});
const onFinish = values => {
    if (btnStr.value === "提交") {
        addLocation(values.location).then((res) => {
            if(res.code === 200)
            router.push({ name: 'location' })
        })
    } else {
        changeLocation(values.location).then((res) => {
            if(res.code === 200)
            router.push({ name: 'location' })
        })
    }
};

onMounted(() => {
    if (Object.keys(route.query).length) {
        btnStr.value = "修改"
        for (const key in formState.location) {
            if (key === "dpAll") {
                formState.location[key] = Number.parseFloat(route.query[key])
            } else {
                formState.location[key] = route.query[key]
            }
        }
    }
})

</script>